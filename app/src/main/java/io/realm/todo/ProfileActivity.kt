package io.realm.todo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.todo.transactions.realmResultsProfile
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(findViewById(R.id.toolbar))
        showProfile()
    }

    @SuppressLint("SetTextI18n")
    private fun showProfile() {
        realmResultsProfile.addChangeListener { results ->
            if (results.isNotEmpty()) {
                val profile = results[0]!!
                first_name.setText(profile.firstName)
                surname.setText(profile.surname)
                email.setText(profile.email)
                tradingName.setText(profile.tradingName)
            }
        }
    }

    companion object {
        fun openFrom(originActivity: Activity) {
            val intent = Intent(originActivity, ProfileActivity::class.java)
            originActivity.startActivity(intent)
        }
    }
}