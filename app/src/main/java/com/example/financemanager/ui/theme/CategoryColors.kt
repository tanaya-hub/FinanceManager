package com.example.financemanager.ui.theme

import androidx.compose.ui.graphics.Color

fun categoryColor(label: String): Color = when (label) {
    "Food" -> Color(0xFFF2A65A)
    "Transport" -> Color(0xFF5B8DEF)
    "Shopping" -> Color(0xFFB07CF0)
    "Bills" -> Color(0xFF4FC3D9)
    "Income" -> Color(0xFF3FB68B)
    else -> Color(0xFF8B96A5)
}