package io.realm.todo.transactions

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import io.realm.SyncUser
import io.realm.log.RealmLog
import io.realm.todo.R
import io.realm.todo.WelcomeActivity
import kotlinx.android.synthetic.main.activity_items.*
import java.util.*

@SuppressLint("LongLogTag")
class TransactionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupFab()
        showProfile()
        showTransactions()
        printChangesInRealm()
    }

    @SuppressLint("SetTextI18n")
    private fun showProfile() {
        realmResultsProfile.addChangeListener { results ->
            if (results.isNotEmpty()) {
                val profile = results[0]!!
                name.text = "${profile.firstName} ${profile.surname}"
                email.text = profile.email
                tradingName.text = profile.tradingName
            }
        }
    }

    private fun setupFab() {
        findViewById<View>(R.id.fab).setOnClickListener { showDialog() }
    }

    private fun showTransactions() {
        val itemsRecyclerAdapter = TransactionsRecyclerAdapter(realmResultsTransactions)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemsRecyclerAdapter
    }

    private fun showDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_transaction, null)
        val editText = dialogView.findViewById<EditText>(R.id.amount)

        AlertDialog.Builder(this@TransactionsActivity)
                .setTitle("Add a new transaction with amount...")
                .setView(dialogView)
                .setPositiveButton("Add") { _, _ ->
                    addDataToRealm(editText)
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }

    private fun addDataToRealm(editText: EditText) {
        realm.executeTransactionAsync({ realm ->
            val id = UUID.randomUUID().toString()
            val amountInPounds = editText.text.toString().toFloat()
            val amountInPennies = (amountInPounds * 100).toLong()
            val cardType = "Debit"
            val timeStamp = Date()
            val cardScheme = "Visa"
            val transaction = Transaction(id, amountInPennies, cardType, cardScheme, timeStamp)

            realm.insert(transaction)
        }, { error ->
            Log.e("Eg:TransactionsActivity:72", "onCreate error:$error")
            RealmLog.error(error)
        })
    }

    private fun printChangesInRealm() {
        realmResultsTransactions.addChangeListener { transactions1 ->
            val asJSON = transactions1.asJSON()
            Log.i("Eg:TransactionsActivity:90", "onCreate asJSON:$asJSON")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            logout()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        val syncUser = SyncUser.current()
        if (syncUser != null) {
            syncUser.logOut()
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
