package io.realm.todo.transactions

import io.realm.*
import io.realm.todo.Constants

var realm: Realm = Realm.getInstance(transactionsRealmConfig)

val realmResultsTransactions: RealmResults<Transaction> = realm
        .where(Transaction::class.java)
        .sort("dateTime", Sort.DESCENDING)
        .findAllAsync()

val realmResultsProfile: RealmResults<Profile> = realm
        .where(Profile::class.java)
        .findAllAsync()

private val transactionsRealmConfig: RealmConfiguration
    get() {
        val user = SyncUser.current()
        val instance = Constants.INSTANCE_ADDRESS
        val url = "realms://$instance/~/Transactions"
        return user.createConfiguration(url).build()
    }