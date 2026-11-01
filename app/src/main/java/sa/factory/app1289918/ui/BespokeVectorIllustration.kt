package sa.factory.app1289918.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun BespokeVectorIllustration(
    archetype: String,
    screenIdx: Int,
    modifier: Modifier = Modifier.fillMaxWidth().height(160.dp),
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    secondaryColor: Color = MaterialTheme.colorScheme.secondary
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f

        // Branch on BOTH archetype AND screenIdx (8 Archetypes x 3 Screens = 24 Unique Drawings)
        when (archetype) {
            "booking_timeline" -> {
                when (screenIdx) {
                    1 -> {
                        // Calendar Grid Matrix & Clock Chrono Vector (8+ Paths)
                        val p1 = Path().apply { addRect(Rect(cx - 70f, cy - 50f, cx + 70f, cy + 50f)) }
                        drawPath(p1, color = primaryColor.copy(alpha = 0.2f), style = Fill)
                        drawPath(p1, color = primaryColor, style = Stroke(width = 3f))

                        val p2 = Path().apply { addRect(Rect(cx - 70f, cy - 50f, cx + 70f, cy - 20f)) }
                        drawPath(p2, color = primaryColor, style = Fill)

                        val p3 = Path().apply {
                            moveTo(cx - 45f, cy - 60f); lineTo(cx - 45f, cy - 40f)
                            moveTo(cx + 45f, cy - 60f); lineTo(cx + 45f, cy - 40f)
                        }
                        drawPath(p3, color = secondaryColor, style = Stroke(width = 5f, cap = StrokeCap.Round))

                        val p4 = Path().apply {
                            moveTo(cx - 70f, cy); lineTo(cx + 70f, cy)
                            moveTo(cx - 70f, cy + 25f); lineTo(cx + 70f, cy + 25f)
                        }
                        drawPath(p4, color = primaryColor.copy(alpha = 0.4f), style = Stroke(width = 2f))

                        val p5 = Path().apply {
                            moveTo(cx - 20f, cy - 20f); lineTo(cx - 20f, cy + 50f)
                            moveTo(cx + 25f, cy - 20f); lineTo(cx + 25f, cy + 50f)
                        }
                        drawPath(p5, color = primaryColor.copy(alpha = 0.4f), style = Stroke(width = 2f))

                        val p6 = Path().apply { addOval(Rect(cx - 5f, cy + 8f, cx + 15f, cy + 28f)) }
                        drawPath(p6, color = Color(0xFF10B981), style = Fill)

                        val p7 = Path().apply { addOval(Rect(cx + 80f, cy - 20f, cx + 130f, cy + 30f)) }
                        drawPath(p7, color = secondaryColor, style = Stroke(width = 4f))

                        val p8 = Path().apply {
                            moveTo(cx + 105f, cy + 5f); lineTo(cx + 105f, cy - 10f)
                            moveTo(cx + 105f, cy + 5f); lineTo(cx + 120f, cy + 5f)
                        }
                        drawPath(p8, color = Color.White, style = Stroke(width = 3f, cap = StrokeCap.Round))
                    }
                    2 -> {
                        // Chrono Progress Stream & Milestone Nodes (8+ Paths)
                        val p1 = Path().apply {
                            moveTo(cx - 140f, cy)
                            cubicTo(cx - 70f, cy - 45f, cx + 70f, cy + 45f, cx + 140f, cy)
                        }
                        drawPath(p1, color = primaryColor, style = Stroke(width = 6f, cap = StrokeCap.Round))

                        val p2 = Path().apply { addOval(Rect(cx - 150f, cy - 10f, cx - 130f, cy + 10f)) }
                        drawPath(p2, color = primaryColor, style = Fill)

                        val p3 = Path().apply { addOval(Rect(cx - 10f, cy - 10f, cx + 10f, cy + 10f)) }
                        drawPath(p3, color = secondaryColor, style = Fill)

                        val p4 = Path().apply { addOval(Rect(cx + 130f, cy - 10f, cx + 150f, cy + 10f)) }
                        drawPath(p4, color = Color(0xFF10B981), style = Fill)

                        val p5 = Path().apply {
                            moveTo(cx - 140f, cy - 25f); lineTo(cx - 140f, cy - 15f)
                            moveTo(cx, cy - 25f); lineTo(cx, cy - 15f)
                            moveTo(cx + 140f, cy - 25f); lineTo(cx + 140f, cy - 15f)
                        }
                        drawPath(p5, color = Color.White, style = Stroke(width = 3f))

                        val p6 = Path().apply {
                            moveTo(cx + 134f, cy - 2f); lineTo(cx + 139f, cy + 3f); lineTo(cx + 146f, cy - 4f)
                        }
                        drawPath(p6, color = Color.White, style = Stroke(width = 3f, cap = StrokeCap.Round))

                        val p7 = Path().apply {
                            moveTo(cx - 80f, cy - 35f); lineTo(cx - 60f, cy - 35f)
                        }
                        drawPath(p7, color = secondaryColor, style = Stroke(width = 3f))

                        val p8 = Path().apply {
                            moveTo(cx + 60f, cy + 35f); lineTo(cx + 80f, cy + 35f)
                        }
                        drawPath(p8, color = secondaryColor, style = Stroke(width = 3f))
                    }
                    else -> {
                        // Certified Booking Seal & Ribbon Stamp (8+ Paths)
                        val p1 = Path().apply { addOval(Rect(cx - 55f, cy - 55f, cx + 55f, cy + 55f)) }
                        drawPath(p1, color = primaryColor.copy(alpha = 0.2f), style = Fill)
                        drawPath(p1, color = primaryColor, style = Stroke(width = 5f))

                        val p2 = Path().apply { addOval(Rect(cx - 45f, cy - 45f, cx + 45f, cy + 45f)) }
                        drawPath(p2, color = secondaryColor, style = Stroke(width = 2f))

                        val p3 = Path().apply {
                            moveTo(cx - 20f, cy); lineTo(cx - 5f, cy + 15f); lineTo(cx + 25f, cy - 15f)
                        }
                        drawPath(p3, color = Color(0xFF10B981), style = Stroke(width = 6f, cap = StrokeCap.Round))

                        val p4 = Path().apply {
                            moveTo(cx - 30f, cy + 45f); lineTo(cx - 40f, cy + 70f); lineTo(cx - 15f, cy + 55f)
                        }
                        drawPath(p4, color = primaryColor, style = Fill)

                        val p5 = Path().apply {
                            moveTo(cx + 30f, cy + 45f); lineTo(cx + 40f, cy + 70f); lineTo(cx + 15f, cy + 55f)
                        }
                        drawPath(p5, color = primaryColor, style = Fill)

                        val p6 = Path().apply {
                            moveTo(cx, cy - 55f); lineTo(cx, cy - 45f)
                            moveTo(cx, cy + 45f); lineTo(cx, cy + 55f)
                        }
                        drawPath(p6, color = secondaryColor, style = Stroke(width = 3f))

                        val p7 = Path().apply {
                            moveTo(cx - 55f, cy); lineTo(cx - 45f, cy)
                            moveTo(cx + 45f, cy); lineTo(cx + 55f, cy)
                        }
                        drawPath(p7, color = secondaryColor, style = Stroke(width = 3f))

                        val p8 = Path().apply { addOval(Rect(cx - 4f, cy - 4f, cx + 4f, cy + 4f)) }
                        drawPath(p8, color = Color.White, style = Fill)
                    }
                }
            }
            "map_radar" -> {
                when (screenIdx) {
                    1 -> {
                        // Concentric Radar Scanner & Crosshairs (8+ Paths)
                        val p1 = Path().apply { addOval(Rect(cx - 65f, cy - 65f, cx + 65f, cy + 65f)) }
                        drawPath(p1, color = primaryColor.copy(alpha = 0.3f), style = Stroke(width = 3f))

                        val p2 = Path().apply { addOval(Rect(cx - 45f, cy - 45f, cx + 45f, cy + 45f)) }
                        drawPath(p2, color = primaryColor.copy(alpha = 0.5f), style = Stroke(width = 3f))

                        val p3 = Path().apply { addOval(Rect(cx - 25f, cy - 25f, cx + 25f, cy + 25f)) }
                        drawPath(p3, color = secondaryColor, style = Stroke(width = 3f))

                        val p4 = Path().apply {
                            moveTo(cx, cy - 70f); lineTo(cx, cy + 70f)
                            moveTo(cx - 70f, cy); lineTo(cx + 70f, cy)
                        }
                        drawPath(p4, color = primaryColor.copy(alpha = 0.4f), style = Stroke(width = 2f))

                        val p5 = Path().apply {
                            moveTo(cx, cy); lineTo(cx + 45f, cy - 45f)
                        }
                        drawPath(p5, color = secondaryColor, style = Stroke(width = 4f, cap = StrokeCap.Round))

                        val p6 = Path().apply { addOval(Rect(cx + 30f, cy - 35f, cx + 38f, cy - 27f)) }
                        drawPath(p6, color = Color(0xFF10B981), style = Fill)

                        val p7 = Path().apply { addOval(Rect(cx - 35f, cy + 20f, cx - 27f, cy + 28f)) }
                        drawPath(p7, color = secondaryColor, style = Fill)

                        val p8 = Path().apply { addOval(Rect(cx - 4f, cy - 4f, cx + 4f, cy + 4f)) }
                        drawPath(p8, color = Color.White, style = Fill)
                    }
                    2 -> {
                        // Multi-Hop Route Topology (8+ Paths)
                        val p1 = Path().apply {
                            moveTo(cx - 130f, cy + 20f)
                            cubicTo(cx - 60f, cy - 50f, cx + 40f, cy + 50f, cx + 130f, cy - 30f)
                        }
                        drawPath(p1, color = primaryColor, style = Stroke(width = 6f, cap = StrokeCap.Round))

                        val p2 = Path().apply { addOval(Rect(cx - 140f, cy + 10f, cx - 120f, cy + 30f)) }
                        drawPath(p2, color = primaryColor, style = Fill)

                        val p3 = Path().apply { addOval(Rect(cx - 45f, cy - 25f, cx - 25f, cy - 5f)) }
                        drawPath(p3, color = secondaryColor, style = Fill)

                        val p4 = Path().apply { addOval(Rect(cx + 40f, cy + 15f, cx + 60f, cy + 35f)) }
                        drawPath(p4, color = secondaryColor, style = Fill)

                        val p5 = Path().apply { addOval(Rect(cx + 120f, cy - 40f, cx + 140f, cy - 20f)) }
                        drawPath(p5, color = Color(0xFF10B981), style = Fill)

                        val p6 = Path().apply {
                            moveTo(cx - 130f, cy + 20f); lineTo(cx - 130f, cy + 45f)
                            moveTo(cx + 130f, cy - 30f); lineTo(cx + 130f, cy - 5f)
                        }
                        drawPath(p6, color = Color.Gray, style = Stroke(width = 2f))

                        val p7 = Path().apply {
                            moveTo(cx + 124f, cy - 32f); lineTo(cx + 129f, cy - 27f); lineTo(cx + 137f, cy - 35f)
                        }
                        drawPath(p7, color = Color.White, style = Stroke(width = 3f, cap = StrokeCap.Round))

                        val p8 = Path().apply {
                            moveTo(cx - 5f, cy - 50f); lineTo(cx + 5f, cy - 50f)
                        }
                        drawPath(p8, color = secondaryColor, style = Stroke(width = 3f))
                    }
                    else -> {
                        // Radial Speedometer Gauge (8+ Paths)
                        val p1 = Path().apply { arcTo(Rect(cx - 70f, cy - 55f, cx + 70f, cy + 55f), 150f, 240f, false) }
                        drawPath(p1, color = Color(0xFF334155), style = Stroke(width = 12f, cap = StrokeCap.Round))

                        val p2 = Path().apply { arcTo(Rect(cx - 70f, cy - 55f, cx + 70f, cy + 55f), 150f, 160f, false) }
                        drawPath(p2, color = primaryColor, style = Stroke(width = 12f, cap = StrokeCap.Round))

                        val p3 = Path().apply { arcTo(Rect(cx - 70f, cy - 55f, cx + 70f, cy + 55f), 310f, 60f, false) }
                        drawPath(p3, color = secondaryColor, style = Stroke(width = 12f, cap = StrokeCap.Round))

                        val p4 = Path().apply { addOval(Rect(cx - 14f, cy - 14f, cx + 14f, cy + 14f)) }
                        drawPath(p4, color = secondaryColor, style = Fill)

                        val p5 = Path().apply { moveTo(cx, cy); lineTo(cx + 40f, cy - 30f) }
                        drawPath(p5, color = Color.White, style = Stroke(width = 5f, cap = StrokeCap.Round))

                        val p6 = Path().apply { moveTo(cx - 80f, cy + 25f); lineTo(cx - 68f, cy + 18f) }
                        drawPath(p6, color = Color.Gray, style = Stroke(width = 3f))

                        val p7 = Path().apply { moveTo(cx + 80f, cy + 25f); lineTo(cx + 68f, cy + 18f) }
                        drawPath(p7, color = Color.Gray, style = Stroke(width = 3f))

                        val p8 = Path().apply { moveTo(cx, cy - 65f); lineTo(cx, cy - 55f) }
                        drawPath(p8, color = Color.Gray, style = Stroke(width = 3f))
                    }
                }
            }
            "analytics_bento" -> {
                when (screenIdx) {
                    1 -> {
                        // Sine Performance Graph & Vertices (8+ Paths)
                        val p1 = Path().apply {
                            moveTo(cx - 140f, cy + 40f)
                            cubicTo(cx - 80f, cy - 40f, cx - 20f, cy + 30f, cx + 40f, cy - 35f)
                            cubicTo(cx + 80f, cy - 60f, cx + 110f, cy - 20f, cx + 140f, cy - 45f)
                        }
                        drawPath(p1, color = secondaryColor, style = Stroke(width = 5f, cap = StrokeCap.Round))

                        val p2 = Path().apply {
                            moveTo(cx - 140f, cy + 40f)
                            cubicTo(cx - 80f, cy - 40f, cx - 20f, cy + 30f, cx + 40f, cy - 35f)
                            cubicTo(cx + 80f, cy - 60f, cx + 110f, cy - 20f, cx + 140f, cy - 45f)
                            lineTo(cx + 140f, cy + 55f); lineTo(cx - 140f, cy + 55f); close()
                        }
                        drawPath(p2, color = primaryColor.copy(alpha = 0.2f), style = Fill)

                        val p3 = Path().apply { moveTo(cx - 145f, cy + 55f); lineTo(cx + 145f, cy + 55f) }
                        drawPath(p3, color = Color.Gray, style = Stroke(width = 2f))

                        val p4 = Path().apply { addOval(Rect(cx - 84f, cy - 44f, cx - 76f, cy - 36f)) }
                        drawPath(p4, color = secondaryColor, style = Fill)

                        val p5 = Path().apply { addOval(Rect(cx + 36f, cy - 39f, cx + 44f, cy - 31f)) }
                        drawPath(p5, color = secondaryColor, style = Fill)

                        val p6 = Path().apply { addOval(Rect(cx + 136f, cy - 49f, cx + 144f, cy - 41f)) }
                        drawPath(p6, color = Color(0xFF10B981), style = Fill)

                        val p7 = Path().apply { moveTo(cx - 140f, cy - 50f); lineTo(cx - 140f, cy + 55f) }
                        drawPath(p7, color = Color.Gray, style = Stroke(width = 2f))

                        val p8 = Path().apply {
                            moveTo(cx - 145f, cy - 20f); lineTo(cx - 140f, cy - 20f)
                            moveTo(cx - 145f, cy + 15f); lineTo(cx - 140f, cy + 15f)
                        }
                        drawPath(p8, color = Color.Gray, style = Stroke(width = 2f))
                    }
                    2 -> {
                        // Bento Donut Matrix (4 Arcs & Hub) (8+ Paths)
                        val p1 = Path().apply { arcTo(Rect(cx - 55f, cy - 55f, cx + 55f, cy + 55f), 0f, 90f, false) }
                        drawPath(p1, color = primaryColor, style = Stroke(width = 24f))

                        val p2 = Path().apply { arcTo(Rect(cx - 55f, cy - 55f, cx + 55f, cy + 55f), 95f, 85f, false) }
                        drawPath(p2, color = secondaryColor, style = Stroke(width = 24f))

                        val p3 = Path().apply { arcTo(Rect(cx - 55f, cy - 55f, cx + 55f, cy + 55f), 185f, 85f, false) }
                        drawPath(p3, color = Color(0xFF6366F1), style = Stroke(width = 24f))

                        val p4 = Path().apply { arcTo(Rect(cx - 55f, cy - 55f, cx + 55f, cy + 55f), 275f, 80f, false) }
                        drawPath(p4, color = Color(0xFF10B981), style = Stroke(width = 24f))

                        val p5 = Path().apply { addOval(Rect(cx - 20f, cy - 20f, cx + 20f, cy + 20f)) }
                        drawPath(p5, color = Color(0xFF0F172A), style = Fill)

                        val p6 = Path().apply {
                            moveTo(cx, cy - 10f); lineTo(cx, cy + 10f)
                            moveTo(cx - 10f, cy); lineTo(cx + 10f, cy)
                        }
                        drawPath(p6, color = Color.White, style = Stroke(width = 2f))

                        val p7 = Path().apply {
                            moveTo(cx + 65f, cy - 35f); lineTo(cx + 90f, cy - 35f)
                        }
                        drawPath(p7, color = Color(0xFF10B981), style = Stroke(width = 3f))

                        val p8 = Path().apply {
                            moveTo(cx - 65f, cy + 35f); lineTo(cx - 90f, cy + 35f)
                        }
                        drawPath(p8, color = secondaryColor, style = Stroke(width = 3f))
                    }
                    else -> {
                        // Security Audit Shield Vector (8+ Paths)
                        val p1 = Path().apply {
                            moveTo(cx, cy - 65f)
                            lineTo(cx + 50f, cy - 40f)
                            lineTo(cx + 50f, cy + 10f)
                            cubicTo(cx + 50f, cy + 45f, cx, cy + 65f, cx, cy + 65f)
                            cubicTo(cx, cy + 65f, cx - 50f, cy + 45f, cx - 50f, cy + 10f)
                            lineTo(cx - 50f, cy - 40f)
                            close()
                        }
                        drawPath(p1, color = primaryColor.copy(alpha = 0.25f), style = Fill)
                        drawPath(p1, color = primaryColor, style = Stroke(width = 5f))

                        val p2 = Path().apply {
                            moveTo(cx, cy - 50f)
                            lineTo(cx + 38f, cy - 30f)
                            lineTo(cx + 38f, cy + 8f)
                            cubicTo(cx + 38f, cy + 35f, cx, cy + 50f, cx, cy + 50f)
                            cubicTo(cx, cy + 50f, cx - 38f, cy + 35f, cx - 38f, cy + 8f)
                            lineTo(cx - 38f, cy - 30f)
                            close()
                        }
                        drawPath(p2, color = secondaryColor.copy(alpha = 0.3f), style = Stroke(width = 2f))

                        val p3 = Path().apply {
                            moveTo(cx - 18f, cy + 2f); lineTo(cx - 4f, cy + 16f); lineTo(cx + 20f, cy - 12f)
                        }
                        drawPath(p3, color = Color.White, style = Stroke(width = 6f, cap = StrokeCap.Round))

                        val p4 = Path().apply { moveTo(cx, cy - 65f); lineTo(cx, cy + 65f) }
                        drawPath(p4, color = primaryColor.copy(alpha = 0.4f), style = Stroke(width = 2f))

                        val p5 = Path().apply { addOval(Rect(cx - 6f, cy - 25f, cx + 6f, cy - 13f)) }
                        drawPath(p5, color = secondaryColor, style = Stroke(width = 3f))

                        val p6 = Path().apply {
                            moveTo(cx - 30f, cy - 35f); lineTo(cx - 15f, cy - 35f)
                        }
                        drawPath(p6, color = Color.White, style = Stroke(width = 2f))

                        val p7 = Path().apply {
                            moveTo(cx + 15f, cy - 35f); lineTo(cx + 30f, cy - 35f)
                        }
                        drawPath(p7, color = Color.White, style = Stroke(width = 2f))

                        val p8 = Path().apply { addOval(Rect(cx - 3f, cy + 35f, cx + 3f, cy + 41f)) }
                        drawPath(p8, color = Color(0xFF10B981), style = Fill)
                    }
                }
            }
            "catalog_grid" -> {
                when (screenIdx) {
                    1 -> {
                        // Product Bottle & Luxury Flacon (8+ Paths)
                        val p1 = Path().apply { addRect(Rect(cx - 45f, cy - 30f, cx + 45f, cy + 50f)) }
                        drawPath(p1, color = primaryColor.copy(alpha = 0.25f), style = Fill)
                        drawPath(p1, color = primaryColor, style = Stroke(width = 4f))

                        val p2 = Path().apply { addRect(Rect(cx - 20f, cy - 55f, cx + 20f, cy - 30f)) }
                        drawPath(p2, color = secondaryColor, style = Fill)

                        val p3 = Path().apply {
                            moveTo(cx - 10f, cy - 65f); lineTo(cx + 10f, cy - 65f)
                            moveTo(cx, cy - 65f); lineTo(cx, cy - 55f)
                        }
                        drawPath(p3, color = secondaryColor, style = Stroke(width = 3f))

                        val p4 = Path().apply { addRect(Rect(cx - 30f, cy - 15f, cx + 30f, cy + 25f)) }
                        drawPath(p4, color = Color(0xFF1E293B), style = Fill)
                        drawPath(p4, color = secondaryColor, style = Stroke(width = 2f))

                        val p5 = Path().apply {
                            moveTo(cx - 20f, cy); lineTo(cx + 20f, cy)
                            moveTo(cx - 15f, cy + 10f); lineTo(cx + 15f, cy + 10f)
                        }
                        drawPath(p5, color = Color.White, style = Stroke(width = 2f))

                        val p6 = Path().apply {
                            moveTo(cx - 40f, cy - 25f); lineTo(cx - 40f, cy + 45f)
                        }
                        drawPath(p6, color = Color.White.copy(alpha = 0.4f), style = Stroke(width = 3f))

                        val p7 = Path().apply {
                            moveTo(cx + 55f, cy - 10f); lineTo(cx + 80f, cy - 10f)
                        }
                        drawPath(p7, color = secondaryColor, style = Stroke(width = 3f))

                        val p8 = Path().apply { addOval(Rect(cx + 80f, cy - 14f, cx + 88f, cy - 6f)) }
                        drawPath(p8, color = Color(0xFF10B981), style = Fill)
                    }
                    2 -> {
                        // Hierarchy Pyramid Tiers (8+ Paths)
                        val p1 = Path().apply {
                            moveTo(cx, cy - 60f); lineTo(cx + 30f, cy - 20f); lineTo(cx - 30f, cy - 20f); close()
                        }
                        drawPath(p1, color = secondaryColor, style = Fill)

                        val p2 = Path().apply {
                            moveTo(cx - 34f, cy - 15f); lineTo(cx + 34f, cy - 15f)
                            lineTo(cx + 60f, cy + 15f); lineTo(cx - 60f, cy + 15f); close()
                        }
                        drawPath(p2, color = primaryColor, style = Fill)

                        val p3 = Path().apply {
                            moveTo(cx - 64f, cy + 20f); lineTo(cx + 64f, cy + 20f)
                            lineTo(cx + 90f, cy + 55f); lineTo(cx - 90f, cy + 55f); close()
                        }
                        drawPath(p3, color = Color(0xFF1E293B), style = Fill)
                        drawPath(p3, color = primaryColor, style = Stroke(width = 2f))

                        val p4 = Path().apply {
                            moveTo(cx, cy - 60f); lineTo(cx - 90f, cy + 55f)
                            moveTo(cx, cy - 60f); lineTo(cx + 90f, cy + 55f)
                        }
                        drawPath(p4, color = secondaryColor, style = Stroke(width = 3f))

                        val p5 = Path().apply {
                            moveTo(cx - 90f, cy + 55f); lineTo(cx + 90f, cy + 55f)
                        }
                        drawPath(p5, color = secondaryColor, style = Stroke(width = 3f))

                        val p6 = Path().apply {
                            moveTo(cx + 40f, cy - 35f); lineTo(cx + 80f, cy - 35f)
                        }
                        drawPath(p6, color = Color.Gray, style = Stroke(width = 2f))

                        val p7 = Path().apply {
                            moveTo(cx + 70f, cy); lineTo(cx + 105f, cy)
                        }
                        drawPath(p7, color = Color.Gray, style = Stroke(width = 2f))

                        val p8 = Path().apply { addOval(Rect(cx - 4f, cy - 4f, cx + 4f, cy + 4f)) }
                        drawPath(p8, color = Color.White, style = Fill)
                    }
                    else -> {
                        // Gift Parcel & Ribbon Bow (8+ Paths)
                        val p1 = Path().apply { addRect(Rect(cx - 50f, cy - 30f, cx + 50f, cy + 50f)) }
                        drawPath(p1, color = primaryColor, style = Fill)
                        drawPath(p1, color = primaryColor, style = Stroke(width = 4f))

                        val p2 = Path().apply { addRect(Rect(cx - 55f, cy - 42f, cx + 55f, cy - 30f)) }
                        drawPath(p2, color = secondaryColor, style = Fill)

                        val p3 = Path().apply { addRect(Rect(cx - 10f, cy - 42f, cx + 10f, cy + 50f)) }
                        drawPath(p3, color = Color.White.copy(alpha = 0.9f), style = Fill)

                        val p4 = Path().apply { addRect(Rect(cx - 50f, cy - 2f, cx + 50f, cy + 12f)) }
                        drawPath(p4, color = Color.White.copy(alpha = 0.9f), style = Fill)

                        val p5 = Path().apply {
                            moveTo(cx, cy - 42f)
                            cubicTo(cx - 25f, cy - 65f, cx - 35f, cy - 50f, cx, cy - 42f)
                        }
                        drawPath(p5, color = secondaryColor, style = Stroke(width = 4f))

                        val p6 = Path().apply {
                            moveTo(cx, cy - 42f)
                            cubicTo(cx + 25f, cy - 65f, cx + 35f, cy - 50f, cx, cy - 42f)
                        }
                        drawPath(p6, color = secondaryColor, style = Stroke(width = 4f))

                        val p7 = Path().apply { addOval(Rect(cx - 6f, cy - 48f, cx + 6f, cy - 36f)) }
                        drawPath(p7, color = Color.White, style = Fill)

                        val p8 = Path().apply {
                            moveTo(cx - 30f, cy + 40f); lineTo(cx - 18f, cy + 28f)
                        }
                        drawPath(p8, color = Color.White.copy(alpha = 0.6f), style = Stroke(width = 3f))
                    }
                }
            }
            "service_stepper" -> {
                when (screenIdx) {
                    1 -> {
                        // Linear Pipeline Stepper & Progress Nodes (8+ Paths)
                        val p1 = Path().apply { moveTo(cx - 130f, cy); lineTo(cx + 130f, cy) }
                        drawPath(p1, color = primaryColor, style = Stroke(width = 6f, cap = StrokeCap.Round))

                        val p2 = Path().apply { moveTo(cx - 130f, cy); lineTo(cx, cy) }
                        drawPath(p2, color = secondaryColor, style = Stroke(width = 6f, cap = StrokeCap.Round))

                        val p3 = Path().apply { addOval(Rect(cx - 142f, cy - 12f, cx - 118f, cy + 12f)) }
                        drawPath(p3, color = secondaryColor, style = Fill)

                        val p4 = Path().apply {
                            moveTo(cx - 134f, cy - 2f); lineTo(cx - 130f, cy + 2f); lineTo(cx - 124f, cy - 4f)
                        }
                        drawPath(p4, color = Color.Black, style = Stroke(width = 3f, cap = StrokeCap.Round))

                        val p5 = Path().apply { addOval(Rect(cx - 12f, cy - 12f, cx + 12f, cy + 12f)) }
                        drawPath(p5, color = primaryColor, style = Fill)
                        drawPath(p5, color = secondaryColor, style = Stroke(width = 3f))

                        val p6 = Path().apply {
                            moveTo(cx - 5f, cy); lineTo(cx + 5f, cy)
                        }
                        drawPath(p6, color = Color.White, style = Stroke(width = 3f))

                        val p7 = Path().apply { addOval(Rect(cx + 118f, cy - 12f, cx + 142f, cy + 12f)) }
                        drawPath(p7, color = Color(0xFF1E293B), style = Fill)
                        drawPath(p7, color = Color.Gray, style = Stroke(width = 3f))

                        val p8 = Path().apply {
                            moveTo(cx - 130f, cy - 25f); lineTo(cx - 130f, cy - 18f)
                            moveTo(cx, cy - 25f); lineTo(cx, cy - 18f)
                            moveTo(cx + 130f, cy - 25f); lineTo(cx + 130f, cy - 18f)
                        }
                        drawPath(p8, color = secondaryColor, style = Stroke(width = 2f))
                    }
                    2 -> {
                        // Blueprint Drafting Compass (8+ Paths)
                        val p1 = Path().apply {
                            moveTo(cx, cy - 50f); lineTo(cx - 45f, cy + 45f)
                            moveTo(cx, cy - 50f); lineTo(cx + 45f, cy + 45f)
                        }
                        drawPath(p1, color = secondaryColor, style = Stroke(width = 5f, cap = StrokeCap.Round))

                        val p2 = Path().apply {
                            moveTo(cx - 30f, cy + 15f); lineTo(cx + 30f, cy + 15f)
                        }
                        drawPath(p2, color = primaryColor, style = Stroke(width = 4f))

                        val p3 = Path().apply { addOval(Rect(cx - 12f, cy - 62f, cx + 12f, cy - 38f)) }
                        drawPath(p3, color = primaryColor, style = Fill)

                        val p4 = Path().apply {
                            moveTo(cx - 45f, cy + 45f); lineTo(cx - 52f, cy + 58f)
                            moveTo(cx + 45f, cy + 45f); lineTo(cx + 52f, cy + 58f)
                        }
                        drawPath(p4, color = Color.White, style = Stroke(width = 3f))

                        val p5 = Path().apply {
                            arcTo(Rect(cx - 65f, cy - 20f, cx + 65f, cy + 60f), 30f, 120f, false)
                        }
                        drawPath(p5, color = primaryColor, style = Stroke(width = 2f))

                        val p6 = Path().apply { addOval(Rect(cx - 4f, cy + 11f, cx + 4f, cy + 19f)) }
                        drawPath(p6, color = secondaryColor, style = Fill)

                        val p7 = Path().apply {
                            moveTo(cx - 20f, cy + 60f); lineTo(cx + 20f, cy + 60f)
                        }
                        drawPath(p7, color = Color.Gray, style = Stroke(width = 2f))

                        val p8 = Path().apply { moveTo(cx, cy - 70f); lineTo(cx, cy - 62f) }
                        drawPath(p8, color = secondaryColor, style = Stroke(width = 3f))
                    }
                    else -> {
                        // Engineering Stamp Seal (8+ Paths)
                        val p1 = Path().apply { addOval(Rect(cx - 55f, cy - 55f, cx + 55f, cy + 55f)) }
                        drawPath(p1, color = primaryColor, style = Fill)
                        drawPath(p1, color = secondaryColor, style = Stroke(width = 5f))

                        val p2 = Path().apply { addOval(Rect(cx - 44f, cy - 44f, cx + 44f, cy + 44f)) }
                        drawPath(p2, color = Color.White, style = Stroke(width = 2f))

                        val p3 = Path().apply {
                            moveTo(cx, cy - 35f); lineTo(cx + 12f, cy - 10f); lineTo(cx + 38f, cy - 8f)
                            lineTo(cx + 18f, cy + 10f); lineTo(cx + 24f, cy + 35f); lineTo(cx, cy + 20f)
                            lineTo(cx - 24f, cy + 35f); lineTo(cx - 18f, cy + 10f); lineTo(cx - 38f, cy - 8f)
                            lineTo(cx - 12f, cy - 10f); close()
                        }
                        drawPath(p3, color = secondaryColor, style = Fill)

                        val p4 = Path().apply { addOval(Rect(cx - 14f, cy - 14f, cx + 14f, cy + 14f)) }
                        drawPath(p4, color = Color(0xFF0F172A), style = Fill)

                        val p5 = Path().apply {
                            moveTo(cx - 7f, cy); lineTo(cx - 2f, cy + 5f); lineTo(cx + 7f, cy - 4f)
                        }
                        drawPath(p5, color = Color(0xFF10B981), style = Stroke(width = 3f, cap = StrokeCap.Round))

                        val p6 = Path().apply {
                            moveTo(cx - 30f, cy + 45f); lineTo(cx - 45f, cy + 70f); lineTo(cx - 15f, cy + 60f)
                        }
                        drawPath(p6, color = primaryColor, style = Fill)

                        val p7 = Path().apply {
                            moveTo(cx + 30f, cy + 45f); lineTo(cx + 45f, cy + 70f); lineTo(cx + 15f, cy + 60f)
                        }
                        drawPath(p7, color = primaryColor, style = Fill)

                        val p8 = Path().apply { moveTo(cx, cy - 65f); lineTo(cx, cy - 55f) }
                        drawPath(p8, color = secondaryColor, style = Stroke(width = 3f))
                    }
                }
            }
            "medical_portal" -> {
                when (screenIdx) {
                    1 -> {
                        // ECG Heartbeat Pulse Waveform (8+ Paths)
                        val p1 = Path().apply {
                            moveTo(cx - 140f, cy)
                            lineTo(cx - 80f, cy)
                            lineTo(cx - 65f, cy - 25f)
                            lineTo(cx - 50f, cy + 25f)
                            lineTo(cx - 30f, cy - 55f)
                            lineTo(cx - 10f, cy + 65f)
                            lineTo(cx + 10f, cy - 15f)
                            lineTo(cx + 30f, cy + 10f)
                            lineTo(cx + 50f, cy)
                            lineTo(cx + 140f, cy)
                        }
                        drawPath(p1, color = secondaryColor, style = Stroke(width = 5f, cap = StrokeCap.Round))

                        val p2 = Path().apply {
                            moveTo(cx - 30f, cy - 55f); lineTo(cx - 10f, cy + 65f)
                        }
                        drawPath(p2, color = Color(0xFF10B981), style = Stroke(width = 6f, cap = StrokeCap.Round))

                        val p3 = Path().apply { addOval(Rect(cx - 35f, cy - 60f, cx - 25f, cy - 50f)) }
                        drawPath(p3, color = secondaryColor, style = Fill)

                        val p4 = Path().apply { addOval(Rect(cx - 15f, cy + 60f, cx - 5f, cy + 70f)) }
                        drawPath(p4, color = Color(0xFF10B981), style = Fill)

                        val p5 = Path().apply {
                            moveTo(cx - 140f, cy - 40f); lineTo(cx + 140f, cy - 40f)
                            moveTo(cx - 140f, cy + 40f); lineTo(cx + 140f, cy + 40f)
                        }
                        drawPath(p5, color = primaryColor.copy(alpha = 0.2f), style = Stroke(width = 1.5f))

                        val p6 = Path().apply {
                            moveTo(cx - 80f, cy - 55f); lineTo(cx - 80f, cy + 55f)
                            moveTo(cx + 80f, cy - 55f); lineTo(cx + 80f, cy + 55f)
                        }
                        drawPath(p6, color = primaryColor.copy(alpha = 0.2f), style = Stroke(width = 1.5f))

                        val p7 = Path().apply {
                            moveTo(cx, cy - 70f)
                            cubicTo(cx - 25f, cy - 90f, cx - 50f, cy - 65f, cx, cy - 35f)
                            cubicTo(cx + 50f, cy - 65f, cx + 25f, cy - 90f, cx, cy - 70f)
                        }
                        drawPath(p7, color = primaryColor.copy(alpha = 0.8f), style = Fill)

                        val p8 = Path().apply { addOval(Rect(cx + 135f, cy - 4f, cx + 143f, cy + 4f)) }
                        drawPath(p8, color = Color.White, style = Fill)
                    }
                    2 -> {
                        // Diagnostic Target Matrix & Crosshairs (8+ Paths)
                        val p1 = Path().apply { addOval(Rect(cx - 55f, cy - 55f, cx + 55f, cy + 55f)) }
                        drawPath(p1, color = primaryColor, style = Stroke(width = 4f))

                        val p2 = Path().apply { addOval(Rect(cx - 35f, cy - 35f, cx + 35f, cy + 35f)) }
                        drawPath(p2, color = secondaryColor, style = Stroke(width = 3f))

                        val p3 = Path().apply { addOval(Rect(cx - 15f, cy - 15f, cx + 15f, cy + 15f)) }
                        drawPath(p3, color = Color(0xFF10B981), style = Fill)

                        val p4 = Path().apply {
                            moveTo(cx, cy - 70f); lineTo(cx, cy - 55f)
                            moveTo(cx, cy + 55f); lineTo(cx, cy + 70f)
                            moveTo(cx - 70f, cy); lineTo(cx - 55f, cy)
                            moveTo(cx + 55f, cy); lineTo(cx + 70f, cy)
                        }
                        drawPath(p4, color = secondaryColor, style = Stroke(width = 4f))

                        val p5 = Path().apply {
                            moveTo(cx - 10f, cy - 35f); lineTo(cx + 10f, cy - 35f)
                            moveTo(cx - 10f, cy + 35f); lineTo(cx + 10f, cy + 35f)
                        }
                        drawPath(p5, color = primaryColor, style = Stroke(width = 2f))

                        val p6 = Path().apply {
                            moveTo(cx - 35f, cy - 10f); lineTo(cx - 35f, cy + 10f)
                            moveTo(cx + 35f, cy - 10f); lineTo(cx + 35f, cy + 10f)
                        }
                        drawPath(p6, color = primaryColor, style = Stroke(width = 2f))

                        val p7 = Path().apply {
                            moveTo(cx + 25f, cy - 25f); lineTo(cx + 75f, cy - 50f)
                        }
                        drawPath(p7, color = secondaryColor, style = Stroke(width = 2f))

                        val p8 = Path().apply { addOval(Rect(cx + 72f, cy - 53f, cx + 78f, cy - 47f)) }
                        drawPath(p8, color = Color.White, style = Fill)
                    }
                    else -> {
                        // Caduceus Medical Staff Vector (8+ Paths)
                        val p1 = Path().apply { moveTo(cx, cy - 65f); lineTo(cx, cy + 65f) }
                        drawPath(p1, color = primaryColor, style = Stroke(width = 8f, cap = StrokeCap.Round))

                        val p2 = Path().apply { addOval(Rect(cx - 14f, cy - 75f, cx + 14f, cy - 47f)) }
                        drawPath(p2, color = secondaryColor, style = Fill)

                        val p3 = Path().apply {
                            moveTo(cx - 35f, cy - 35f)
                            cubicTo(cx + 35f, cy - 25f, cx + 35f, cy - 5f, cx, cy + 5f)
                        }
                        drawPath(p3, color = secondaryColor, style = Stroke(width = 4f))

                        val p4 = Path().apply {
                            moveTo(cx + 35f, cy - 35f)
                            cubicTo(cx - 35f, cy - 25f, cx - 35f, cy - 5f, cx, cy + 5f)
                        }
                        drawPath(p4, color = secondaryColor, style = Stroke(width = 4f))

                        val p5 = Path().apply {
                            moveTo(cx, cy + 5f)
                            cubicTo(cx - 35f, cy + 15f, cx - 35f, cy + 35f, cx, cy + 45f)
                        }
                        drawPath(p5, color = primaryColor, style = Stroke(width = 4f))

                        val p6 = Path().apply {
                            moveTo(cx, cy + 5f)
                            cubicTo(cx + 35f, cy + 15f, cx + 35f, cy + 35f, cx, cy + 45f)
                        }
                        drawPath(p6, color = primaryColor, style = Stroke(width = 4f))

                        val p7 = Path().apply {
                            moveTo(cx - 45f, cy - 45f); lineTo(cx + 45f, cy - 45f)
                        }
                        drawPath(p7, color = Color(0xFF10B981), style = Stroke(width = 4f, cap = StrokeCap.Round))

                        val p8 = Path().apply { addOval(Rect(cx - 3f, cy + 68f, cx + 3f, cy + 74f)) }
                        drawPath(p8, color = secondaryColor, style = Fill)
                    }
                }
            }
            "cupertino_storefront" -> {
                when (screenIdx) {
                    1 -> {
                        // Cupertino SF Symbol Shopping Bag (8+ Paths)
                        val p1 = Path().apply {
                            moveTo(cx - 35f, cy - 25f)
                            cubicTo(cx - 35f, cy - 65f, cx + 35f, cy - 65f, cx + 35f, cy - 25f)
                        }
                        drawPath(p1, color = secondaryColor, style = Stroke(width = 7f, cap = StrokeCap.Round))

                        val p2 = Path().apply {
                            moveTo(cx - 50f, cy - 25f); lineTo(cx + 50f, cy - 25f)
                            lineTo(cx + 40f, cy + 55f); lineTo(cx - 40f, cy + 55f); close()
                        }
                        drawPath(p2, color = primaryColor, style = Fill)
                        drawPath(p2, color = primaryColor, style = Stroke(width = 4f))

                        val p3 = Path().apply {
                            moveTo(cx - 35f, cy - 5f)
                            cubicTo(cx - 35f, cy + 15f, cx + 35f, cy + 15f, cx + 35f, cy - 5f)
                        }
                        drawPath(p3, color = secondaryColor, style = Stroke(width = 4f, cap = StrokeCap.Round))

                        val p4 = Path().apply {
                            moveTo(cx - 15f, cy + 25f); lineTo(cx + 15f, cy + 25f)
                            moveTo(cx, cy + 10f); lineTo(cx, cy + 40f)
                        }
                        drawPath(p4, color = Color.White, style = Stroke(width = 4f, cap = StrokeCap.Round))

                        val p5 = Path().apply { addOval(Rect(cx - 43f, cy + 52f, cx - 37f, cy + 58f)) }
                        drawPath(p5, color = secondaryColor, style = Fill)

                        val p6 = Path().apply { addOval(Rect(cx + 37f, cy + 52f, cx + 43f, cy + 58f)) }
                        drawPath(p6, color = secondaryColor, style = Fill)

                        val p7 = Path().apply {
                            moveTo(cx - 65f, cy - 20f); lineTo(cx - 50f, cy - 20f)
                            moveTo(cx + 50f, cy - 20f); lineTo(cx + 65f, cy - 20f)
                        }
                        drawPath(p7, color = Color.Gray, style = Stroke(width = 3f))

                        val p8 = Path().apply { addOval(Rect(cx - 4f, cy - 50f, cx + 4f, cy - 42f)) }
                        drawPath(p8, color = secondaryColor, style = Fill)
                    }
                    2 -> {
                        // Cupertino Segmented Control Matrix (8+ Paths)
                        val p1 = Path().apply { addRoundRect(androidx.compose.ui.geometry.RoundRect(cx - 85f, cy - 25f, cx + 85f, cy + 25f, 25f, 25f)) }
                        drawPath(p1, color = Color(0xFF1E293B), style = Fill)
                        drawPath(p1, color = primaryColor, style = Stroke(width = 3f))

                        val p2 = Path().apply { addRoundRect(androidx.compose.ui.geometry.RoundRect(cx - 80f, cy - 20f, cx - 25f, cy + 20f, 20f, 20f)) }
                        drawPath(p2, color = secondaryColor, style = Fill)

                        val p3 = Path().apply {
                            moveTo(cx - 52f, cy - 8f); lineTo(cx - 52f, cy + 8f)
                            moveTo(cx - 60f, cy); lineTo(cx - 44f, cy)
                        }
                        drawPath(p3, color = Color.Black, style = Stroke(width = 3f, cap = StrokeCap.Round))

                        val p4 = Path().apply {
                            moveTo(cx, cy - 12f); lineTo(cx, cy + 12f)
                            moveTo(cx + 55f, cy - 12f); lineTo(cx + 55f, cy + 12f)
                        }
                        drawPath(p4, color = Color.Gray, style = Stroke(width = 3f))

                        val p5 = Path().apply { addOval(Rect(cx - 25f, cy + 40f, cx + 25f, cy + 90f)) }
                        drawPath(p5, color = primaryColor.copy(alpha = 0.2f), style = Fill)
                        drawPath(p5, color = primaryColor, style = Stroke(width = 4f))

                        val p6 = Path().apply {
                            moveTo(cx - 10f, cy + 65f); lineTo(cx - 2f, cy + 73f); lineTo(cx + 12f, cy + 57f)
                        }
                        drawPath(p6, color = Color(0xFF10B981), style = Stroke(width = 4f, cap = StrokeCap.Round))

                        val p7 = Path().apply {
                            moveTo(cx - 60f, cy + 65f); lineTo(cx - 35f, cy + 65f)
                            moveTo(cx + 35f, cy + 65f); lineTo(cx + 60f, cy + 65f)
                        }
                        drawPath(p7, color = Color.Gray, style = Stroke(width = 2f))

                        val p8 = Path().apply { addOval(Rect(cx - 4f, cy + 36f, cx + 4f, cy + 44f)) }
                        drawPath(p8, color = secondaryColor, style = Fill)
                    }
                    else -> {
                        // Apple Pay & ZATCA Digital Card (8+ Paths)
                        val p1 = Path().apply { addRoundRect(androidx.compose.ui.geometry.RoundRect(cx - 75f, cy - 45f, cx + 75f, cy + 45f, 16f, 16f)) }
                        drawPath(p1, color = primaryColor, style = Fill)
                        drawPath(p1, color = secondaryColor, style = Stroke(width = 4f))

                        val p2 = Path().apply { addRect(Rect(cx - 55f, cy - 25f, cx - 25f, cy - 5f)) }
                        drawPath(p2, color = Color(0xFFF59E0B), style = Fill)

                        val p3 = Path().apply {
                            moveTo(cx - 40f, cy - 25f); lineTo(cx - 40f, cy - 5f)
                            moveTo(cx - 55f, cy - 15f); lineTo(cx - 25f, cy - 15f)
                        }
                        drawPath(p3, color = Color.Black, style = Stroke(width = 2f))

                        val p4 = Path().apply {
                            arcTo(Rect(cx + 25f, cy - 30f, cx + 45f, cy - 10f), 200f, 140f, false)
                        }
                        drawPath(p4, color = Color.White, style = Stroke(width = 3f, cap = StrokeCap.Round))

                        val p5 = Path().apply {
                            arcTo(Rect(cx + 20f, cy - 35f, cx + 50f, cy - 5f), 200f, 140f, false)
                        }
                        drawPath(p5, color = Color.White, style = Stroke(width = 3f, cap = StrokeCap.Round))

                        val p6 = Path().apply {
                            moveTo(cx - 55f, cy + 15f); lineTo(cx + 35f, cy + 15f)
                        }
                        drawPath(p6, color = Color.White.copy(alpha = 0.5f), style = Stroke(width = 4f))

                        val p7 = Path().apply {
                            moveTo(cx - 55f, cy + 28f); lineTo(cx - 15f, cy + 28f)
                        }
                        drawPath(p7, color = secondaryColor, style = Stroke(width = 3f))

                        val p8 = Path().apply { addOval(Rect(cx + 40f, cy + 15f, cx + 56f, cy + 31f)) }
                        drawPath(p8, color = Color(0xFF10B981), style = Fill)
                    }
                }
            }
            "dynamic_island_tracker" -> {
                when (screenIdx) {
                    1 -> {
                        // Dynamic Island Capsule Vector (8+ Paths)
                        val p1 = Path().apply { addRoundRect(androidx.compose.ui.geometry.RoundRect(cx - 80f, cy - 25f, cx + 80f, cy + 25f, 25f, 25f)) }
                        drawPath(p1, color = Color.Black, style = Fill)
                        drawPath(p1, color = secondaryColor, style = Stroke(width = 4f))

                        val p2 = Path().apply { addOval(Rect(cx - 65f, cy - 12f, cx - 41f, cy + 12f)) }
                        drawPath(p2, color = primaryColor, style = Fill)

                        val p3 = Path().apply {
                            moveTo(cx - 58f, cy); lineTo(cx - 48f, cy)
                            moveTo(cx - 53f, cy - 5f); lineTo(cx - 53f, cy + 5f)
                        }
                        drawPath(p3, color = Color.White, style = Stroke(width = 2f))

                        val p4 = Path().apply {
                            moveTo(cx - 25f, cy); lineTo(cx + 35f, cy)
                        }
                        drawPath(p4, color = primaryColor, style = Stroke(width = 6f, cap = StrokeCap.Round))

                        val p5 = Path().apply { addOval(Rect(cx + 50f, cy - 10f, cx + 70f, cy + 10f)) }
                        drawPath(p5, color = Color(0xFF10B981), style = Fill)

                        val p6 = Path().apply {
                            moveTo(cx + 56f, cy); lineTo(cx + 60f, cy + 4f); lineTo(cx + 66f, cy - 3f)
                        }
                        drawPath(p6, color = Color.White, style = Stroke(width = 2f, cap = StrokeCap.Round))

                        val p7 = Path().apply {
                            moveTo(cx - 25f, cy - 10f); lineTo(cx + 5f, cy - 10f)
                        }
                        drawPath(p7, color = secondaryColor, style = Stroke(width = 3f))

                        val p8 = Path().apply {
                            moveTo(cx, cy - 35f); lineTo(cx, cy - 25f)
                        }
                        drawPath(p8, color = secondaryColor, style = Stroke(width = 3f))
                    }
                    2 -> {
                        // Dynamic Route Telemetry & ETA (8+ Paths)
                        val p1 = Path().apply {
                            moveTo(cx - 130f, cy + 30f)
                            cubicTo(cx - 70f, cy - 40f, cx + 50f, cy + 50f, cx + 120f, cy - 20f)
                        }
                        drawPath(p1, color = primaryColor, style = Stroke(width = 8f, cap = StrokeCap.Round))

                        val p2 = Path().apply { addOval(Rect(cx - 142f, cy + 18f, cx - 118f, cy + 42f)) }
                        drawPath(p2, color = primaryColor, style = Fill)

                        val p3 = Path().apply { addOval(Rect(cx - 15f, cy - 5f, cx + 5f, cy + 15f)) }
                        drawPath(p3, color = secondaryColor, style = Fill)

                        val p4 = Path().apply { addOval(Rect(cx + 108f, cy - 32f, cx + 132f, cy - 8f)) }
                        drawPath(p4, color = Color(0xFF10B981), style = Fill)

                        val p5 = Path().apply {
                            moveTo(cx + 114f, cy - 22f); lineTo(cx + 119f, cy - 17f); lineTo(cx + 127f, cy - 25f)
                        }
                        drawPath(p5, color = Color.White, style = Stroke(width = 3f, cap = StrokeCap.Round))

                        val p6 = Path().apply {
                            moveTo(cx - 20f, cy + 5f); lineTo(cx + 10f, cy + 5f)
                        }
                        drawPath(p6, color = Color.White, style = Stroke(width = 2f))

                        val p7 = Path().apply { addOval(Rect(cx - 22f, cy - 12f, cx + 12f, cy + 22f)) }
                        drawPath(p7, color = secondaryColor, style = Stroke(width = 2f))

                        val p8 = Path().apply {
                            moveTo(cx - 80f, cy - 25f); lineTo(cx - 60f, cy - 25f)
                        }
                        drawPath(p8, color = secondaryColor, style = Stroke(width = 3f))
                    }
                    else -> {
                        // Digital Handshake & Order Delivered Seal (8+ Paths)
                        val p1 = Path().apply { addOval(Rect(cx - 60f, cy - 60f, cx + 60f, cy + 60f)) }
                        drawPath(p1, color = primaryColor, style = Fill)
                        drawPath(p1, color = secondaryColor, style = Stroke(width = 6f))

                        val p2 = Path().apply { addOval(Rect(cx - 48f, cy - 48f, cx + 48f, cy + 48f)) }
                        drawPath(p2, color = Color.White, style = Stroke(width = 2f))

                        val p3 = Path().apply {
                            moveTo(cx - 22f, cy); lineTo(cx - 5f, cy + 18f); lineTo(cx + 25f, cy - 15f)
                        }
                        drawPath(p3, color = Color(0xFF10B981), style = Stroke(width = 7f, cap = StrokeCap.Round))

                        val p4 = Path().apply { addOval(Rect(cx - 4f, cy - 70f, cx + 4f, cy - 62f)) }
                        drawPath(p4, color = secondaryColor, style = Fill)

                        val p5 = Path().apply { addOval(Rect(cx - 4f, cy + 62f, cx + 4f, cy + 70f)) }
                        drawPath(p5, color = secondaryColor, style = Fill)

                        val p6 = Path().apply { addOval(Rect(cx - 70f, cy - 4f, cx - 62f, cy + 4f)) }
                        drawPath(p6, color = secondaryColor, style = Fill)

                        val p7 = Path().apply { addOval(Rect(cx + 62f, cy - 4f, cx + 70f, cy + 4f)) }
                        drawPath(p7, color = secondaryColor, style = Fill)

                        val p8 = Path().apply {
                            moveTo(cx, cy - 48f); lineTo(cx, cy - 35f)
                            moveTo(cx, cy + 35f); lineTo(cx, cy + 48f)
                        }
                        drawPath(p8, color = Color.White, style = Stroke(width = 2f))
                    }
                }
            }
            else -> {
                val p1 = Path().apply { addOval(Rect(cx - 50f, cy - 50f, cx + 50f, cy + 50f)) }
                drawPath(p1, color = primaryColor, style = Stroke(width = 4f))
                val p2 = Path().apply { moveTo(cx - 20f, cy); lineTo(cx + 20f, cy) }
                drawPath(p2, color = secondaryColor, style = Stroke(width = 4f))
            }
        }
    }
}
