package sa.factory.app1289918.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun DriveBackupSyncJobHomeScreen(
    viewModel: DriveBackupSyncJobViewModel,
    onSelectItem: (DriveBackupSyncTask) -> Unit,
    onQuickCheckout: () -> Unit
) {
    val catalog by viewModel.catalog.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("مزامنة درايف احترافي مع نسخ احتياطي تلقائي", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("أتمتة نسخ Google Drive احتياطياً عل", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                // Bespoke Vector Graphic Header (>= 8 Paths branched on archetype)
                BespokeVectorIllustration(
                    archetype = "catalog_grid",
                    screenIdx = 1,
                    modifier = Modifier.fillMaxWidth().height(160.dp)
                )
            }

            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("الكل", "الأكثر طلباً", "المجموعات الحصرية", "عروض خاصة").forEachIndexed { idx, tab ->
                        FilterChip(
                            selected = (idx == 0),
                            onClick = { },
                            label = { Text(tab, fontSize = 12.sp) }
                        )
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("عروض الكتالوج المعتمد والشحن الفوري", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text("تغليف فاخر مع شحن مجاني لكافة مدن المملكة للطلبات المعتمدة", fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(onClick = onQuickCheckout, shape = RoundedCornerShape(10.dp)) {
                            Text("إتمام الشراء السريع الآن", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        

            item {
                Text("قائمة الخدمات المعتمدة للتخصيص:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            items(catalog) { item ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(item.description, color = Color.Gray, fontSize = 12.sp)
                        }
                        Button(
                            onClick = { onSelectItem(item) },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("${item.priceMinorUnits / 100} SAR")
                        }
                    }
                }
            }
        }
    }
}
