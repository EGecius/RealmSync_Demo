package io.realm.todo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.todo.transactions.realmResultsProfile
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        showProfile()
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
}