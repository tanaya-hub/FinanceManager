
package com.example.financemanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val merchant: String,
    val amountPaise: Long,
    val category: String,
    val account: String,
    val epochDay: Long
)
