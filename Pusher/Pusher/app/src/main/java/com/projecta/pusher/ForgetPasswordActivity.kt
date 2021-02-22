package com.projecta.pusher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        auth = FirebaseAuth.getInstance()

    }

    fun sendMailButton(view: View) {
        val email = forgetMailText.text.toString().trim()

        if (email.isEmpty()) {
            forgetMailText.error = "E-posta boş bırakılamaz."
            forgetMailText.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(forgetMailText.text.toString()).matches()) {
            forgetMailText.error = "Lütfen geçerli bir e-posta giriniz."
            forgetMailText.requestFocus()
        } else {
            forgetPassword(email)
        }
    }

    private fun forgetPassword(email: String) {

        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isComplete) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(
                    applicationContext,
                    "Şifre sıfırlama bağıntısı e-postanıza gönderildi. Lütfen gelen kutunuzu kontrol edin.",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }


    }
}