package com.example.filmotecaapp.ui

import androidx.appcompat.app.AppCompatActivity
import com.example.filmotecaapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            finishAffinity()
        }
    }
}