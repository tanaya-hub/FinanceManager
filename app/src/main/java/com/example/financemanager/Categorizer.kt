package com.example.financemanager

enum class Category(val label: String) {
    FOOD("Food"),
    TRANSPORT("Transport"),
    SHOPPING("Shopping"),
    BILLS("Bills"),
    INCOME("Income"),
    OTHER("Other")
}

object Categorizer {

    private val rules: List<Pair<Category, List<String>>> = listOf(
        Category.FOOD to listOf("swiggy", "zomato", "domino", "mcdonald", "starbucks", "kfc", "pizza", "cafe", "restaurant"),
        Category.TRANSPORT to listOf("uber", "ola", "rapido", "irctc", "metro", "petrol", "fuel"),
        Category.SHOPPING to listOf("amazon", "flipkart", "myntra", "ajio", "meesho"),
        Category.BILLS to listOf("electricity", "bill", "jio", "airtel", "recharge", "broadband", "gas"),
        Category.INCOME to listOf("salary", "interest")
    )

    fun categorize(merchant: String, amountPaise: Long): Category {
        val name = merchant.lowercase()
        for ((category, keywords) in rules) {
            if (keywords.any { name.contains(it) }) return category
        }
        return if (amountPaise > 0) Category.INCOME else Category.OTHER
    }
}