#!/usr/bin/env bash
# ==============================================================================
# 🚀 1-CLICK PRODUCTION VPS DEPLOYMENT & AUTO-SCALER SCRIPT
# Project: #1289918 — مزامنة درايف احترافي مع نسخ احتياطي تلقائي
# Supported OS: Ubuntu 22.04 LTS / Ubuntu 24.04 LTS (Hetzner, Contabo, Linode, AWS)
# ==============================================================================
set -euo pipefail

APP_NAME="api-1289918"
APP_PORT=3000
APP_DIR="/var/www/app-1289918/server"

echo "👑 [1/6] Updating system packages and security hardening..."
sudo apt-get update -y && sudo apt-get upgrade -y
sudo apt-get install -y curl ufw nginx git unattended-upgrades

echo "🛡️ [2/6] Configuring UFW Firewall..."
sudo ufw allow 22/tcp comment 'SSH'
sudo ufw allow 80/tcp comment 'HTTP Nginx'
sudo ufw allow 443/tcp comment 'HTTPS SSL'
sudo ufw default deny incoming
sudo ufw default allow outgoing
echo "y" | sudo ufw enable

echo "📦 [3/6] Installing Node.js 20 LTS & PM2 Production Process Manager..."
if ! command -v node >/dev/null 2>&1; then
    curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
    sudo apt-get install -y nodejs
fi
sudo npm install -g pm2 nodemon

echo "⚙️ [4/6] Setting up project dependencies..."
cd "$APP_DIR"
npm install --production

echo "🔄 [5/6] Launching PM2 Cluster Auto-Scaler across all CPU cores..."
pm2 delete "$APP_NAME" 2>/dev/null || true
pm2 start server.js -i max --name "$APP_NAME"
pm2 save
pm2 startup systemd -u "$USER" --hp "$HOME" || true

echo "🌐 [6/6] Configuring Nginx Reverse Proxy with WebSocket Upgrades..."
NGINX_CONF="/etc/nginx/sites-available/$APP_NAME"
sudo tee "$NGINX_CONF" > /dev/null <<EOF
server {
    listen 80;
    server_name _;

    location / {
        proxy_pass http://127.0.0.1:$APP_PORT;
        proxy_http_version 1.1;
        proxy_set_header Upgrade \$http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
        proxy_cache_bypass \$http_upgrade;
    }
}
EOF

sudo ln -sf "$NGINX_CONF" "/etc/nginx/sites-enabled/$APP_NAME"
sudo nginx -t && sudo systemctl reload nginx

echo ""
echo "=============================================================================="
echo "✅ DEPLOYMENT COMPLETE! Server is live and auto-scaled on port $APP_PORT"
echo "📊 Run 'pm2 status' to view CPU/RAM usage across clusters."
echo "📜 Run 'pm2 logs $APP_NAME' to tail real-time application logs."
echo "=============================================================================="
