package com.joesemper.justnotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.joesemper.justnotes.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun navigateTo(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .add(R.id.fragment_container_view, fragment)
            .addToBackStack("notes")
            .commit()
    }
}