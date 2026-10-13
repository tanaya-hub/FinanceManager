package com.example.financemanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.financemanager.ui.theme.Accent
import com.example.financemanager.ui.theme.SurfaceHigh
import com.example.financemanager.ui.theme.TextMuted
import com.example.financemanager.ui.theme.TextPrimary

@Composable
fun CategoryChips(
    categories: List<String>,
    selected: String?,
    onSelect: (String?) -> Unit,
    showAll: Boolean = true
) {
    val options: List<String?> = if (showAll) listOf(null) + categories else categories

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(options) { option ->
            val isSelected = option == selected
            Text(
                text = option ?: "All",
                style = MaterialTheme.typography.labelLarge,
                color = if (isSelected) TextPrimary else TextMuted,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) Accent else SurfaceHigh)
                    .clickable { onSelect(option) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}