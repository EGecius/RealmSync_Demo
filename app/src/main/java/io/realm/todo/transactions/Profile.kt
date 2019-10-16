package io.realm.todo.transactions

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Profile(
        @PrimaryKey
        @Required
        var contactId: String = "",
        @Required
        var firstName: String = "",
        @Required
        var surname: String = "",
        var email: String = "",
        var tradingName: String = ""
) : RealmObject()
