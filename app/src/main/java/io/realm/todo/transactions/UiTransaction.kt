package io.realm.todo.transactions

import android.support.annotation.DrawableRes

class UiTransaction(
        val amount: String,
        val cardType: String,
        @DrawableRes
        val cardSchemeImg: Int,
        val dateTime: String)
