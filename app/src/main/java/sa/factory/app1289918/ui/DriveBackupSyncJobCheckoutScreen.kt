package sa.factory.app1289918.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriveBackupSyncJobCheckoutScreen(
    onBack: () -> Unit,
    onComplete: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("الفوترة والتتبع اللحظي", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("<", fontWeight = FontWeight.Black, fontSize = 20.sp)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Bespoke Vector Graphic Gauge / Receipt (>= 8 Paths)
            BespokeVectorIllustration(
                archetype = "catalog_grid",
                screenIdx = 3,
                modifier = Modifier.fillMaxWidth().height(160.dp)
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("حالة الطلب: قيد المعالجة المباشرة", fontWeight = FontWeight.Black, fontSize = 16.sp)
                    Text("بصمة التحقق: مسجلة في السجل المجتمعي المشفر", fontSize = 12.sp)
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("الفاتورة الضريبية المعتمدة:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("المبلغ الأساسي: 150.00 SAR", fontSize = 12.sp, color = Color.Gray)
                    Text("ضريبة القيمة المضافة (15%): 22.50 SAR", fontSize = 12.sp, color = Color.Gray)
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    Text("الإجمالي المستحق: 172.50 SAR", fontWeight = FontWeight.Black, fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onComplete,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text("تأكيد السداد والرجوع للرئيسية", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}
