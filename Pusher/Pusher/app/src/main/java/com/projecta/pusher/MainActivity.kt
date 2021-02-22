package com.projecta.pusher

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    // val mEmail  = findViewById<View>(R.id.usernameText)
    // val mPassword  = findViewById<View>(R.id.passwordText)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        if (currentUser != null) {
            val intent = Intent(applicationContext,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun loginClicked(view : View){
        //firebase login authentication

        if (userEmailText.text.toString().isEmpty() || passwordText.text.toString().isEmpty()){
           Toast.makeText(applicationContext,"E-posta ve parola boş bırakılamaz!",Toast.LENGTH_LONG).show()
        }else {

            auth.signInWithEmailAndPassword(userEmailText.text.toString(), passwordText.text.toString()).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    //Signed In
                    Toast.makeText(applicationContext, "Hoşgeldiniz: ${auth.currentUser?.email.toString()}", Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage.toString(), Toast.LENGTH_LONG).show()
            }


        }
    }

    fun regClicked (view : View){
        val intent = Intent(applicationContext,RegisterActivity::class.java)

        startActivity(intent)
        //finish()
    }

    fun forgotClicked (view : View){
        val intent = Intent(applicationContext,ForgetPasswordActivity::class.java)
        startActivity(intent)
    }
}