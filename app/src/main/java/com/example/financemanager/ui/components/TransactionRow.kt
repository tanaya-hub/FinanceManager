package com.example.financemanager.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.financemanager.TxnUi
import com.example.financemanager.formatRupees
import com.example.financemanager.ui.theme.CreditGreen
import com.example.financemanager.ui.theme.DebitRose
import com.example.financemanager.ui.theme.Surface
import com.example.financemanager.ui.theme.TextMuted

@Composable
fun TransactionRow(txn: TxnUi, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MerchantAvatar(txn.merchant)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(txn.merchant, style = MaterialTheme.typography.titleMedium)
                Text(
                    "${txn.category} · ${txn.account}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )
            }
            Text(
                formatRupees(txn.amountPaise),
                style = MaterialTheme.typography.titleMedium,
                color = if (txn.amountPaise < 0) DebitRose else CreditGreen
            )
        }
    }
}