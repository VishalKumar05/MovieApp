package com.example.movieapp.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class LoginActivity : AppCompatActivity() {

    var emailAddress:String? = null
    var password:String? = null
    var isLogin = false
    //declare_auth
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object{
        private val TAG = LoginActivity::class.java.simpleName
        private const val RC_SIGN_IN = 9001
        private val DUMMY_CREDENTIALS = arrayOf(
            "foo@example.com:hello",
            "admin:admin",
            "abcde:abcde"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FirebaseApp.initializeApp(this)
        setup()
    }

    private fun setup() {
        login_button.setOnClickListener { validateFields() }
        google_signIn_button.setOnClickListener { performGoogleSignIn() }

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))        //pass server's client ID to the requestIdToken method
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
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

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun performGoogleSignIn() {
        Toast.makeText(this,"Google SignIn Button Clicked",Toast.LENGTH_SHORT).show()
        signIn()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "Google sign in failed", e)
            }
        }
    }

    //After a user successfully signs in, get an ID token from the GoogleSignInAccount object,
    // exchange it for a Firebase credential,
    // and authenticate with Firebase using the Firebase credential
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id!!)
        //showProgressBar()
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "SignIn With Credential:success")
                    val user = auth.currentUser
                    updateUI(user)
                    gotoLandingActivity()
                    Toast.makeText(applicationContext,"Google Login Successfull",Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "SignIn With Credential:failure", task.exception)
                    showAlertDialog("Authentication Failed")
                    updateUI(null)
                }
                //hideProgressBar()
            }
    }

    private fun gotoLandingActivity() {
        val intent = Intent(this,LandingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // start google signIn
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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

    private fun updateUI(currentUser: FirebaseUser?) {

    }

    override fun onRestart() {
        super.onRestart()
        email_address.text.clear()
        pass.text.clear()
    }
}