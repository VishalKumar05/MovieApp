package com.example.movieapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.utils.AppConstants
import com.example.movieapp.utils.SharedPreference


class SplashActivity : AppCompatActivity() {

    private val mHandler = Handler()
    private val sharedPreferences = SharedPreference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mHandler.postDelayed(mRunnable,AppConstants.SPLASH_DELAY)
    }

    private val mRunnable:Runnable = Runnable{
        val key = sharedPreferences.getValue(this)
        Log.d("Key","Key: $key")
        val intent:Intent
        intent = if (key == 0) Intent(this, LoginActivity::class.java) else Intent(this, LandingActivity::class.java)
        startActivity(intent)
        finish()
    }
}

