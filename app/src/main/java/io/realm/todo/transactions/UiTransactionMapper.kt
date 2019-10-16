package io.realm.todo.transactions

import io.realm.todo.R


class UiTransactionMapper {

    fun toUiTransaction(transaction: Transaction): UiTransaction {
        val dateTime = getDateTime(transaction)
        val cardSchemeImg: Int = getCardSchemeImg(transaction)
        val cardType: String = transaction.cardType
        val amount: String = getAmount(transaction)

        return UiTransaction(amount, cardType, cardSchemeImg, dateTime)
    }

    private fun getAmount(transaction: Transaction): String {
        val amountInPounds = transaction.amount.toFloat() / 100
        val amountInPoundsWith2DecimalPoints = String.format("%.02f", amountInPounds)
        return "£$amountInPoundsWith2DecimalPoints"
    }

    private fun getDateTime(transaction: Transaction): String {
        val hours = getTimeUnit(transaction.dateTime.hours)
        val minutes = getTimeUnit(transaction.dateTime.minutes)
        return "$hours:$minutes"
    }

    private fun getTimeUnit(hourAsInt: Int): String {
        return if (hourAsInt < 10) {
            "0$hourAsInt"
        } else {
            "$hourAsInt"
        }
    }

    private fun getCardSchemeImg(transaction: Transaction): Int {
        return when (transaction.cardScheme) {
            "Visa" -> R.drawable.ic_visa
            "Mastercard" -> R.drawable.ic_mastercard
            else -> R.drawable.ic_generic_payment_card
        }
    }
}
