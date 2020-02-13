package com.example.movieapp.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class LoginActivity : AppCompatActivity() {

    var emailAddress:String? = null
    var password:String? = null
    var isLogin = false

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
            validateFields()
        }
    }

    private fun validateFields() {
        emailAddress = email_address.text.toString()
        password = pass.text.toString()

        if (TextUtils.isEmpty(emailAddress) && TextUtils.isEmpty(password)){
            showAlertDialog("Please enter data")
        }else if (TextUtils.isEmpty(emailAddress)){
            showAlertDialog("Email shouldn't be empty")
            //pass.text.clear()
        }else if (TextUtils.isEmpty(password)){
            showAlertDialog("Password shouldn't be empty")
            //email_address.text.clear()
        }else if (password!!.length < 5){
            showAlertDialog("Password is too short")
        }else{
            performLogin()
        }
    }

    private fun performLogin() {
        for (credential in DUMMY_CREDENTIALS) {
            val pieces = credential.split(":")
            if (pieces[0] == emailAddress && pieces[1] == password) {
                isLogin = true
                val intent = Intent(this, LandingActivity::class.java)
                startActivity(intent)
            }
        }
        if (!isLogin) {
            showAlertDialog("Invalid Email id or Password")
            //email_address.text.clear()
            //pass.text.clear()
        }
    }

    private fun showAlertDialog(msg: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setCancelable(true)

        val dialog: AlertDialog = builder.create()
        dialog.show()

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                dialog.dismiss()
                timer.cancel() //this will cancel the timer of the system
                if (emailAddress!!.isNotEmpty() || password!!.isNotEmpty()) {
                    email_address.text.clear()
                    pass.text.clear()
                }
            }
        }, 2000) // the timer will count 2 seconds....
    }

    override fun onRestart() {
        super.onRestart()
        email_address.text.clear()
        pass.text.clear()
    }
}