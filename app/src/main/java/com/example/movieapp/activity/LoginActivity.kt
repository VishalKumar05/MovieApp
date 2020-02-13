package com.example.movieapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    companion object{
        private val TAG = LoginActivity.javaClass.simpleName
        private val DUMMY_CREDENTIALS = arrayOf(
            "foo@example.com:hello",
            "admin:admin",
            "abcde:abcde"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        Log.d(TAG,"Inside performLogin method")
    }

}