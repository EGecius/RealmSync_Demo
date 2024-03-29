package io.realm.todo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import io.realm.log.RealmLog
import io.realm.todo.transactions.Profile
import io.realm.todo.transactions.realm
import io.realm.todo.transactions.realmResultsProfile
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    lateinit var profile: Profile

    private val firstNameTextChangeListener = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateProfileOnRealm()
        }
    }
    private val surnameTextChangeListener = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateProfileOnRealm()
        }
    }
    private val emailTextChangeListener = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateProfileOnRealm()
        }
    }
    private val tradingNameTextChangeListener = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateProfileOnRealm()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(findViewById(R.id.toolbar))
        showProfile()
        setTextChangeListeners()
    }

    private fun showProfile() {
        realmResultsProfile.addChangeListener { results ->
            if (results.isNotEmpty()) {
                removeTextChangeListeners()
                profile = results[0]!!
                first_name.setText(profile.firstName)
                surname.setText(profile.surname)
                email.setText(profile.email)
                tradingName.setText(profile.tradingName)
                setTextChangeListeners()
            }
        }
    }

    private fun removeTextChangeListeners() {
        first_name.removeTextChangedListener(firstNameTextChangeListener)
        surname.removeTextChangedListener(surnameTextChangeListener)
        email.removeTextChangedListener(emailTextChangeListener)
        tradingName.removeTextChangedListener(tradingNameTextChangeListener)
    }

    private fun setTextChangeListeners() {
        first_name.addTextChangedListener(firstNameTextChangeListener)
        surname.addTextChangedListener(surnameTextChangeListener)
        email.addTextChangedListener(emailTextChangeListener)
        tradingName.addTextChangedListener(tradingNameTextChangeListener)
    }

    @SuppressLint("LongLogTag")
    private fun updateProfileOnRealm() {
        realm.executeTransactionAsync({ realm ->
            val id = profile.contactId
            val firstName = getText(first_name)
            val surname = getText(surname)
            val email = getText(email)
            val tradingName = getText(tradingName)
            val profile = Profile(id, firstName, surname, email, tradingName)
            realm.insertOrUpdate(profile)
        }, { error ->
            Log.e("Eg:TransactionsActivity:72", "onCreate error:$error")
            RealmLog.error(error)
        })
    }

    private fun getText(editText: EditText) = editText.text.toString()

    companion object {
        fun openFrom(originActivity: Activity) {
            val intent = Intent(originActivity, ProfileActivity::class.java)
            originActivity.startActivity(intent)
        }
    }
}