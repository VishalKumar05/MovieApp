package com.example.movieapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.MainActivity
import com.example.movieapp.R
import com.example.movieapp.utils.AppConstants

class SplashActivity : AppCompatActivity() {

    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mHandler.postDelayed(mRunnable,AppConstants.SPLASH_DELAY)
    }

    private val mRunnable:Runnable = Runnable{
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

