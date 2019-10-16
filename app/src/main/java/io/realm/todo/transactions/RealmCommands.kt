package io.realm.todo.transactions

import android.annotation.SuppressLint
import android.util.Log
import io.realm.log.RealmLog
import java.util.*

@SuppressLint("LongLogTag")
fun setProfile() {
    realm.executeTransactionAsync({ realm ->
        val id = UUID.randomUUID().toString()
        val firstName = "Jag"
        val surname = "Kintali"
        val email = "jag@jag.io"
        val tradingName = "Payment Sense"
        val profile = Profile(id, firstName, surname, email, tradingName)
        realm.insert(profile)
    }, { error ->
        Log.e("Eg:TransactionsActivity:72", "onCreate error:$error")
        RealmLog.error(error)
    })
}