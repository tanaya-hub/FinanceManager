package com.example.financemanager.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financemanager.ui.theme.Accent
import com.example.financemanager.ui.theme.TextMuted
import com.example.financemanager.ui.theme.TextPrimary

@Composable
fun HeaderQuote(
    before: String,
    highlight: String,
    after: String,
    subtitle: String
) {
    val quote = buildAnnotatedString {
        append(before)
        withStyle(SpanStyle(color = Accent)) { append(highlight) }
        append(after)
    }

    Column {
        Text(
            text = quote,
            color = TextPrimary,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            lineHeight = 36.sp
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = TextMuted,
            letterSpacing = 0.6.sp
        )
    }
}
