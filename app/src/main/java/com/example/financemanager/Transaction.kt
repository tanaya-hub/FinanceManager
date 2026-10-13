package com.example.financemanager

data class TxnUi(
    val id: String,
    val merchant: String,
    val amountPaise: Long,
    val category: String,
    val account: String,
    val epochDay: Long
)

private fun txn(
    id: String,
    merchant: String,
    amountPaise: Long,
    account: String,
    daysAgo: Long
) = TxnUi(
    id = id,
    merchant = merchant,
    amountPaise = amountPaise,
    category = Categorizer.categorize(merchant, amountPaise).label,
    account = account,
    epochDay = todayEpochDay() - daysAgo
)

val sampleTransactions = listOf(
    txn("1", "Swiggy", -45000, "HDFC •1234", 0),
    txn("6", "Zomato", -62000, "HDFC Credit •9012", 0),
    txn("3", "Uber", -21800, "SBI •5678", 1),
    txn("9", "Starbucks", -38000, "HDFC •1234", 1),
    txn("4", "Amazon", -149900, "HDFC Credit •9012", 2),
    txn("7", "Ola", -34500, "SBI •5678", 3),
    txn("10", "Jio Recharge", -29900, "SBI •5678", 4),
    txn("5", "Electricity Bill", -184050, "SBI •5678", 5),
    txn("8", "Flipkart", -239900, "HDFC Credit •9012", 6),
    txn("2", "Salary", 5500000, "HDFC •1234", 7)
)

fun formatRupees(paise: Long): String {
    val sign = if (paise < 0) "-" else "+"
    val abs = kotlin.math.abs(paise)
    val rupees = abs / 100
    val rem = (abs % 100).toString().padStart(2, '0')
    return "$sign₹$rupees.$rem"
}