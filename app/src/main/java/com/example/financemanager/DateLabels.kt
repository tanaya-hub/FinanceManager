package com.example.financemanager

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun todayEpochDay(): Long = LocalDate.now().toEpochDay()

fun dayLabel(epochDay: Long): String {
    val today = todayEpochDay()
    return when (epochDay) {
        today -> "Today"
        today - 1 -> "Yesterday"
        else -> LocalDate.ofEpochDay(epochDay)
            .format(DateTimeFormatter.ofPattern("d MMM, EEE", Locale.ENGLISH))
    }
}