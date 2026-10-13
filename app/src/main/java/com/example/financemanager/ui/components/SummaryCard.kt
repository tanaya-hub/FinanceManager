package com.example.financemanager.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.financemanager.formatRupees
import com.example.financemanager.ui.theme.CreditGreen
import com.example.financemanager.ui.theme.DebitRose
import com.example.financemanager.ui.theme.Surface
import com.example.financemanager.ui.theme.SurfaceHigh
import com.example.financemanager.ui.theme.TextMuted
import kotlin.math.abs

@Composable
fun SummaryCard(totalIn: Long, totalOut: Long) {
    val net = totalIn + totalOut
    val outAbs = abs(totalOut)
    val flow = totalIn + outAbs

    // Share of the ring that is "money in" (0f to 1f). Used only for drawing.
    val inShare = if (flow == 0L) 0f else totalIn.toFloat() / flow
    val animatedInShare by animateFloatAsState(
        targetValue = inShare,
        animationSpec = tween(durationMillis = 900),
        label = "inShare"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceHigh)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(140.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 16.dp.toPx()
                    val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)
                    val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
                    val ringStyle = Stroke(width = strokeWidth)

                    // The dark track behind the arcs
                    drawArc(
                        color = Surface,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = ringStyle
                    )

                    if (flow > 0L) {
                        val hasBoth = animatedInShare > 0.001f && animatedInShare < 0.999f
                        val gap = if (hasBoth) 8f else 0f
                        val inSweep = 360f * animatedInShare
                        val outSweep = 360f - inSweep

                        if (inSweep > 0f) {
                            drawArc(
                                color = CreditGreen,
                                startAngle = -90f + gap / 2,
                                sweepAngle = inSweep - gap,
                                useCenter = false,
                                topLeft = topLeft,
                                size = arcSize,
                                style = ringStyle
                            )
                        }
                        if (outSweep > 0f) {
                            drawArc(
                                color = DebitRose,
                                startAngle = -90f + inSweep + gap / 2,
                                sweepAngle = outSweep - gap,
                                useCenter = false,
                                topLeft = topLeft,
                                size = arcSize,
                                style = ringStyle
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Net", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                    Text(
                        formatRupees(net),
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }

            Spacer(Modifier.width(20.dp))

            Column(modifier = Modifier.weight(1f)) {
                LegendItem("Money in", formatRupees(totalIn), CreditGreen)
                Spacer(Modifier.height(18.dp))
                LegendItem("Money out", formatRupees(totalOut), DebitRose)
            }
        }
    }
}

@Composable
private fun LegendItem(label: String, amount: String, color: Color) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(Modifier.width(8.dp))
            Text(label, style = MaterialTheme.typography.bodySmall, color = TextMuted)
        }
        Spacer(Modifier.height(2.dp))
        Text(amount, style = MaterialTheme.typography.titleMedium, color = color)
    }
}