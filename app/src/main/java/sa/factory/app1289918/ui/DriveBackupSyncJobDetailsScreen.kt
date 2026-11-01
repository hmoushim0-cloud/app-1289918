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
import sa.factory.app1289918.model.DriveBackupSyncTask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriveBackupSyncJobDetailsScreen(
    item: DriveBackupSyncTask?,
    onBack: () -> Unit,
    onProceedCheckout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تفاصيل وتخصيص الخدمة", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
            // Bespoke Vector Graphic Diagnostic / Route (>= 8 Paths)
            BespokeVectorIllustration(
                archetype = "catalog_grid",
                screenIdx = 2,
                modifier = Modifier.fillMaxWidth().height(160.dp)
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(item?.title ?: "تفاصيل الطلب", fontWeight = FontWeight.Black, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(item?.description ?: "المواصفات الفنية المعتمدة للطلب وفق خطة العمل", fontSize = 13.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("السعر التقديري: ${ (item?.priceMinorUnits ?: 5000L) / 100 } SAR", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("محددات التنفيذ والجدول الزمني:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("• آلية التنفيذ: مهندسون معتمدون وربط سحابي مشفر", fontSize = 12.sp, color = Color.Gray)
                    Text("• موعد التسليم: فوري وفق مراحل الإنجاز المعتمدة", fontSize = 12.sp, color = Color.Gray)
                    Text("• التوافق: احتساب ضريبة القيمة المضافة 15%", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onProceedCheckout,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text("تأكيد والمتابعة للدفع والتتبع", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}
