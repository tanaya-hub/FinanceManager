package com.example.financemanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financemanager.ui.components.CategoryChips
import com.example.financemanager.ui.components.DayHeader
import com.example.financemanager.ui.components.HeaderQuote
import com.example.financemanager.ui.components.SummaryCard
import com.example.financemanager.ui.components.TransactionRow

@Composable
fun LedgerScreen(
    transactions: List<TxnUi>,
    modifier: Modifier = Modifier,
    onTransactionClick: (TxnUi) -> Unit
) {
    var selected by remember { mutableStateOf<String?>(null) }

    val categories = transactions.map { it.category }.distinct()
    val activeFilter = selected?.takeIf { it in categories }
    val shown = if (activeFilter == null) {
        transactions
    } else {
        transactions.filter { it.category == activeFilter }
    }

    val totalIn = shown.filter { it.amountPaise > 0 }.sumOf { it.amountPaise }
    val totalOut = shown.filter { it.amountPaise < 0 }.sumOf { it.amountPaise }

    // Newest day first, then grouped by day
    val groupedByDay = shown
        .sortedByDescending { it.epochDay }
        .groupBy { it.epochDay }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            HeaderQuote(
                before = "Know where it ",
                highlight = "goes",
                after = ".",
                subtitle = "Choose where it grows."
            )
        }
        item {
            SummaryCard(totalIn = totalIn, totalOut = totalOut)
        }
        item {
            CategoryChips(
                categories = categories,
                selected = activeFilter,
                onSelect = { selected = it }
            )
        }
        groupedByDay.forEach { (day, dayItems) ->
            item(key = "header-$day") {
                DayHeader(
                    label = dayLabel(day),
                    netPaise = dayItems.sumOf { it.amountPaise }
                )
            }
            items(dayItems, key = { it.id }) { txn ->
                TransactionRow(txn = txn, onClick = { onTransactionClick(txn) })
            }
        }
    }
}