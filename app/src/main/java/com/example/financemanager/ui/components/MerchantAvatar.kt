package com.example.financemanager.ui.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.financemanager.ui.theme.Accent
import com.example.financemanager.ui.theme.SurfaceHigh

@Composable
fun MerchantAvatar(merchant: String) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(SurfaceHigh),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = merchant.take(1).uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = Accent
        )
    }
}