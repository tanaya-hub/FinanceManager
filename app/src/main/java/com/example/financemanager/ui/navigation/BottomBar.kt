package com.example.financemanager.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.financemanager.ui.theme.Accent
import com.example.financemanager.ui.theme.Surface
import com.example.financemanager.ui.theme.TextMuted
import com.example.financemanager.ui.theme.TextPrimary

enum class AppTab(val label: String) {
    LEDGER("Ledger"),
    INSIGHTS("Insights"),
    SETTINGS("Settings")
}

@Composable
fun BottomBar(current: AppTab, onSelect: (AppTab) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            AppTab.entries.forEach { tab ->
                val selected = tab == current
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onSelect(tab) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(3.dp)
                            .clip(RoundedCornerShape(50))
                            .background(if (selected) Accent else Color.Transparent)
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selected) TextPrimary else TextMuted
                    )
                }
            }
        }
    }
}