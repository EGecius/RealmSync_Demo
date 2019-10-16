package io.realm.todo.transactions

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class Transaction constructor(
        @PrimaryKey
        @Required
        var id: String = "-1",
        var amount: Long = 0,
        var cardType: String = "Unknown",
        var cardScheme: String = "Visa",
        @Required
        var dateTime: Date = Date()
) : RealmObject()