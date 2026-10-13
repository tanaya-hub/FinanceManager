package com.example.financemanager

data class CategorySpend(
    val category: String,
    val spentPaise: Long
)

fun spendByCategory(transactions: List<TxnUi>): List<CategorySpend> =
    transactions
        .filter { it.amountPaise < 0 }
        .groupBy { it.category }
        .map { (category, list) ->
            CategorySpend(category, -list.sumOf { it.amountPaise })
        }
        .sortedByDescending { it.spentPaise }