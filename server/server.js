// Production Backend Gateway & Realtime Engine for #1289918
// App: مزامنة درايف احترافي مع نسخ احتياطي تلقائي
// Persona: Autonomous Cloud Infrastructure & API Scaler

const express = require('express');
const http = require('http');
const cors = require('cors');
const helmet = require('helmet');
const rateLimit = require('express-rate-limit');
const { Server } = require('socket.io');

const app = express();
const server = http.createServer(app);
const PORT = process.env.PORT || 3000;

// 1. Security & Hardening Middleware
app.use(helmet({
  contentSecurityPolicy: false,
  crossOriginResourcePolicy: { policy: "cross-origin" }
}));
app.use(cors({ origin: '*' }));
app.use(express.json({ limit: '2mb' }));

// 2. Anti-Flood & DDoS Rate Limiting
const limiter = rateLimit({
  windowMs: 15 * 60 * 1000, // 15 minutes
  max: 300, // max 300 requests per IP per window
  standardHeaders: true,
  legacyHeaders: false,
  message: {
    success: false,
    error_code: 'RATE_LIMIT_EXCEEDED',
    message_ar: 'تم تجاوز الحد المسموح به من الطلبات مؤقتاً، يرجى المحاولة بعد قليل.'
  }
});
app.use('/api/', limiter);

// 3. Hardware UUID & Client Authentication Guard Middleware
const hardwareUuidGuard = (req, res, next) => {
  const clientUuid = req.headers['x-device-uuid'] || req.headers['x-hardware-uuid'];
  const bannedUuids = (process.env.BANNED_DEVICE_UUIDS || '').split(',').filter(Boolean);

  if (clientUuid && bannedUuids.includes(clientUuid)) {
    return res.status(403).json({
      success: false,
      error_code: 'DEVICE_BANNED',
      message_ar: 'هذا الجهاز محظور من استخدام النظام نظراً لمخالفة سياسات الاستخدام العادل.'
    });
  }
  req.deviceUuid = clientUuid || 'ANONYMOUS_CLIENT';
  next();
};
app.use(hardwareUuidGuard);

// 4. Socket.io Real-time WebSocket Gateway
const io = new Server(server, {
  cors: { origin: '*' },
  transports: ['websocket', 'polling']
});

io.on('connection', (socket) => {
  const clientId = socket.id;
  console.log(`[WebSocket] Client connected: ${clientId}`);

  socket.on('join_tracking_room', (data) => {
    const roomId = data.order_id || data.booking_id || 'default_room';
    socket.join(roomId);
    socket.emit('room_joined', { room: roomId, status: 'CONNECTED', timestamp: Date.now() });
    console.log(`[WebSocket] Client ${clientId} joined room: ${roomId}`);
  });

  socket.on('dispatch_location_update', (coords) => {
    const roomId = coords.order_id || coords.booking_id;
    if (roomId) {
      io.to(roomId).emit('technician_moved', {
        order_id: roomId,
        lat: coords.lat,
        lng: coords.lng,
        eta_minutes: coords.eta_minutes || 12,
        updated_at: Date.now()
      });
    }
  });

  socket.on('disconnect', () => {
    console.log(`[WebSocket] Client disconnected: ${clientId}`);
  });
});

// 5. REST Endpoints for Mobile App (DriveBackupSyncJob)
app.get('/health', (req, res) => {
  res.json({
    status: 'HEALTHY',
    project_id: '1289918',
    app_name: 'مزامنة درايف احترافي مع نسخ احتياطي تلقائي',
    uptime_seconds: process.uptime(),
    active_socket_clients: io.engine.clientsCount,
    timestamp: new Date().toISOString()
  });
});

// Catalog & Packages Endpoint
app.get('/api/v1/services', (req, res) => {
  res.json({
    success: true,
    currency: 'SAR',
    data: [
      { id: 'srv_01', title_ar: 'الباقة الأساسية السريعة', price_sar: 85, duration_min: 45, icon: 'bolt' },
      { id: 'srv_02', title_ar: 'باقة الحماية المتكاملة والنانو', price_sar: 175, duration_min: 75, icon: 'verified' },
      { id: 'srv_03', title_ar: 'الباقة الميدانية الشاملة VIP', price_sar: 290, duration_min: 150, icon: 'star' }
    ]
  });
});

// Booking / Order Submission Endpoint
app.post('/api/v1/orders', (req, res) => {
  const { service_id, vehicle_category, location, client_phone } = req.body;
  if (!service_id || !location) {
    return res.status(400).json({
      success: false,
      error_code: 'VALIDATION_FAILED',
      message_ar: 'البيانات المدخلة غير مكتملة، يرجى تحديد الخدمة والموقع بدقة.'
    });
  }

  const newOrderId = `ORD-${Date.now().toString().slice(-6)}`;
  const basePrice = service_id === 'srv_03' ? 290 : (service_id === 'srv_02' ? 175 : 85);
  const vatAmount = +(basePrice * 0.15).toFixed(2);
  const totalAmount = +(basePrice + vatAmount).toFixed(2);

  res.status(201).json({
    success: true,
    order: {
      order_id: newOrderId,
      service_id,
      vehicle_category: vehicle_category || 'SEDAN',
      location,
      pricing: {
        base_price_sar: basePrice,
        vat_15_sar: vatAmount,
        total_sar: totalAmount
      },
      zatca_qr_payload: `ZATCA-QR-SIMULATED-SHA256-${newOrderId}`,
      status: 'CONFIRMED',
      created_at: new Date().toISOString()
    }
  });
});

// 6. Global Error Handling
app.use((err, req, res, next) => {
  console.error('[Unhandled Error]', err.stack);
  res.status(500).json({
    success: false,
    error_code: 'INTERNAL_SERVER_ERROR',
    message_ar: 'حدث خطأ تقني داخلي، تم تسجيله للمراجعة الفورية.'
  });
});

// 7. Start Server
server.listen(PORT, '0.0.0.0', () => {
  console.log(`🚀 Production Server for #$1289918 running on http://0.0.0.0:${PORT}`);
  console.log(`📡 WebSocket Realtime Gateway active on ws://0.0.0.0:${PORT}`);
});
