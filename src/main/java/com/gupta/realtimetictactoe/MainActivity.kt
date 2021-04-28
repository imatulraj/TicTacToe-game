package com.gupta.realtimetictactoe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private lateinit var mauth: FirebaseAuth
    var database = FirebaseDatabase.getInstance()
    var myRef = database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        load.visibility=View.GONE
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mauth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        var user=mauth.currentUser
        if (user!=null)
        etEmail.setText(user.email)
        load.visibility=View.VISIBLE
        load.setOnClickListener {
       loadmail()
        }

    }
    fun bulogin(view: View) {
       var email=etEmail.text.toString()
        var pass=etPassword.text.toString()
        createAccount(email, pass)
    }
    private fun createAccount(email: String, password: String) {

        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("ResponceLogin", "createUserWithEmail:success")
                    Toast.makeText(this, "LoginSuccesfull", Toast.LENGTH_SHORT).show()
                    val user = mauth.currentUser
                    if (user!=null)
                    myRef.child("user").child(SplitString(user.email!!.toString())).child("Request").setValue(user.uid)
                    loadmail()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("ResponceLogin", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        // [END create_user_with_email]
    }

     fun loadmail(){
         try {

             var user = mauth.currentUser!!
             if (user != null) {
                 var intent = Intent(this, tictactoe::class.java)
                 intent.putExtra("email", user.email)
                 intent.putExtra("uid", user.uid)
                 startActivity(intent)
                 load.visibility = View.GONE
                 finish()
             }
         }catch (E:Exception){}
    }

    private fun SplitString(Str: String): String {
        var retrn=Str.split("@")
        return retrn[0]

    }
}