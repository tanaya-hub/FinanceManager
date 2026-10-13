package com.example.financemanager.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.financemanager.LedgerScreen
import com.example.financemanager.TxnUi
import com.example.financemanager.sampleTransactions
import com.example.financemanager.ui.detail.TransactionDetailScreen
import com.example.financemanager.ui.insights.InsightsScreen
import com.example.financemanager.ui.settings.SettingsScreen

@Composable
fun AppNavigation() {
    val transactions = remember {
        mutableStateListOf<TxnUi>().apply { addAll(sampleTransactions) }
    }
    var selectedId by remember { mutableStateOf<String?>(null) }
    var tab by remember { mutableStateOf(AppTab.LEDGER) }
    val txn = transactions.firstOrNull { it.id == selectedId }

    BackHandler(enabled = txn == null && tab != AppTab.LEDGER) {
        tab = AppTab.LEDGER
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (txn == null) {
                BottomBar(current = tab, onSelect = { tab = it })
            }
        }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)

        if (txn != null) {
            BackHandler { selectedId = null }
            TransactionDetailScreen(
                txn = txn,
                onBack = { selectedId = null },
                onCategoryChange = { newCategory ->
                    // Change every transaction from the same merchant, quietly.
                    for (i in transactions.indices) {
                        if (transactions[i].merchant.equals(txn.merchant, ignoreCase = true)) {
                            transactions[i] = transactions[i].copy(category = newCategory)
                        }
                    }
                },
                modifier = contentModifier
            )
        } else {
            when (tab) {
                AppTab.LEDGER -> LedgerScreen(
                    transactions = transactions,
                    modifier = contentModifier,
                    onTransactionClick = { selectedId = it.id }
                )
                AppTab.INSIGHTS -> InsightsScreen(
                    transactions = transactions,
                    modifier = contentModifier
                )
                AppTab.SETTINGS -> SettingsScreen(modifier = contentModifier)
            }
        }
    }
}