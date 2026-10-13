package com.example.financemanager.ui.insights

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.financemanager.CategorySpend
import com.example.financemanager.TxnUi
import com.example.financemanager.formatRupees
import com.example.financemanager.spendByCategory
import com.example.financemanager.ui.theme.Surface
import com.example.financemanager.ui.theme.SurfaceHigh
import com.example.financemanager.ui.theme.TextMuted
import com.example.financemanager.ui.theme.categoryColor
import kotlin.math.roundToInt

@Composable
fun InsightsScreen(
    transactions: List<TxnUi>,
    modifier: Modifier = Modifier
) {
    val spend = spendByCategory(transactions)
    val total = spend.sumOf { it.spentPaise }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text("Insights", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(4.dp))
                Text(
                    "Where your money went",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted
                )
            }
        }
        if (total == 0L) {
            item { Text("No spending to show yet.", color = TextMuted) }
        } else {
            item { SpendRingCard(spend, total) }
            items(spend, key = { it.category }) { row ->
                CategoryRow(row, total)
            }
        }
    }
}

@Composable
private fun SpendRingCard(spend: List<CategorySpend>, total: Long) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceHigh)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(190.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 22.dp.toPx()
                    val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)
                    val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
                    val ringStyle = Stroke(width = strokeWidth)
                    val gap = if (spend.size > 1) 4f else 0f

                    var start = -90f
                    spend.forEach { slice ->
                        val sweep = 360f * slice.spentPaise.toFloat() / total.toFloat()
                        drawArc(
                            color = categoryColor(slice.category),
                            startAngle = start + gap / 2,
                            sweepAngle = maxOf(sweep - gap, 0.5f),
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = ringStyle
                        )
                        start += sweep
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Spent", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                    Text(formatRupees(-total), style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Composable
private fun CategoryRow(row: CategorySpend, total: Long) {
    val fraction = row.spentPaise.toFloat() / total.toFloat()
    val percent = (fraction * 100).roundToInt()
    val color = categoryColor(row.category)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    row.category,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    formatRupees(-row.spentPaise),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50))
                    .background(SurfaceHigh)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction.coerceIn(0.02f, 1f))
                        .height(6.dp)
                        .clip(RoundedCornerShape(50))
                        .background(color)
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                "$percent% of spending",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )
        }
    }
}