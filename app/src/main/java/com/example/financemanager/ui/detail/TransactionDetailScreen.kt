package com.example.financemanager.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financemanager.Category
import com.example.financemanager.TxnUi
import com.example.financemanager.dayLabel
import com.example.financemanager.formatRupees
import com.example.financemanager.ui.components.CategoryChips
import com.example.financemanager.ui.components.MerchantAvatar
import com.example.financemanager.ui.theme.CreditGreen
import com.example.financemanager.ui.theme.DebitRose
import com.example.financemanager.ui.theme.Surface
import com.example.financemanager.ui.theme.TextMuted

@Composable
fun TransactionDetailScreen(
    txn: TxnUi,
    onBack: () -> Unit,
    onCategoryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDebit = txn.amountPaise < 0

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Text("←", style = MaterialTheme.typography.titleLarge)
            }
            Text("Transaction", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MerchantAvatar(txn.merchant)
            Spacer(Modifier.height(10.dp))
            Text(txn.merchant, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                formatRupees(txn.amountPaise),
                style = MaterialTheme.typography.headlineLarge,
                color = if (isDebit) DebitRose else CreditGreen
            )
        }

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                DetailLine("Category", txn.category)
                DetailLine("Date", dayLabel(txn.epochDay))
                DetailLine("Account", txn.account)
                DetailLine("Type", if (isDebit) "Money out" else "Money in")
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            "Change category",
            style = MaterialTheme.typography.titleMedium,
            color = TextMuted
        )
        Spacer(Modifier.height(10.dp))
        CategoryChips(
            categories = Category.entries.map { it.label },
            selected = txn.category,
            onSelect = { newCategory -> if (newCategory != null) onCategoryChange(newCategory) },
            showAll = false
        )
    }
}

@Composable
private fun DetailLine(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = TextMuted)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}