package com.dankanq.aston_intensiv_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dankanq.aston_intensiv_6.fragments.ContactsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            launchContactsFragment()
        }
    }

    private fun launchContactsFragment() {
        val fragment = ContactsFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }
}