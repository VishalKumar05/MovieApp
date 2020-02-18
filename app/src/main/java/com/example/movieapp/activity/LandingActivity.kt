package com.example.movieapp.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.movieapp.R
import com.example.movieapp.fragment.LandingFragment
import com.example.movieapp.utils.SharedPreference
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity(),TabLayout.OnTabSelectedListener {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val sharedPreferences = SharedPreference()

    companion object{
        private val TAG = LandingActivity::class.java.simpleName
    }

    private val MENU_ITEMS = mapOf(
        "HOME" to "now_playing",
        "POPULAR" to "popular",
        "TOP RATED" to "top_rated",
        "UPCOMING" to "upcoming"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        setup()
    }

    private fun setup() {
        setSupportActionBar(toolBar)
        for (tabs in MENU_ITEMS){
            tabLayout.addTab(tabLayout.newTab().setText(tabs.key))
        }
        tabLayout.addOnTabSelectedListener(this)
        navigateToTab(MENU_ITEMS["HOME"])

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))        //pass server's client ID to the requestIdToken method
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onTabReselected(p0: TabLayout.Tab?) { Log.d(TAG,"Tab Reselected") }

    override fun onTabUnselected(p0: TabLayout.Tab?) { Log.d(TAG,"Tab Unselected") }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        Log.d(TAG,"Tab Selected")
        val tabId = MENU_ITEMS[p0?.text]
        navigateToTab(tabId)
    }

    private fun navigateToTab(tabId: String?) {
        supportFragmentManager.beginTransaction().replace(R.id.pageContainer,LandingFragment(tabId!!)).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_search -> {
                val intent = Intent(this,SearchActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_exit -> {
                Toast.makeText(this,"Exit Clicked",Toast.LENGTH_SHORT).show()
                showAlertDialog(getString(R.string.signout_msg))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //method will show alert dialog
    private fun showAlertDialog(msg: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Sign out")
        builder.setMessage(msg)
        builder.setCancelable(true)
        builder.setPositiveButton("Proceed",DialogInterface.OnClickListener { dialog, i ->
            Log.d(TAG,"Proceed clicked")
            FirebaseAuth.getInstance().signOut()
            googleSignInClient.signOut().addOnCompleteListener {
                sharedPreferences.setValue(this,0)
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        })
        builder.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //To kill app directly
        finishAffinity()
    }

}
