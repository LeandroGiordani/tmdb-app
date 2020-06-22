package com.tmdb.codechallenge.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tmdb.codechallenge.R
import com.tmdb.codechallenge.home.ui.HomeFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()
    }
}
