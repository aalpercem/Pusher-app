package com.projecta.pusher

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Exception
import java.security.KeyStore
import java.sql.Timestamp
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var selectedPicture: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val imgView = findViewById<ImageView>(R.id.registerImageView)
        imgView.setImageResource(R.drawable.avatar_add)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

    }


    fun registerButtonClicked(view: View) {
        if (registerUser()) {

            val uuid = UUID.randomUUID()
            val imageName = "$uuid.jpg"

            val storage = FirebaseStorage.getInstance()
            val reference = storage.reference
            val imagesReference = reference.child("images").child(imageName)

            if (selectedPicture != null) {
                imagesReference.putFile(selectedPicture!!).addOnSuccessListener { taskSnapshot ->
                    val uploadedPictureReference =
                        FirebaseStorage.getInstance().reference.child("images").child(imageName)
                    uploadedPictureReference.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()
                        val hashMap = hashMapOf<String, Any>()
                        FirebaseInstanceId.getInstance().instanceId
                            .addOnCompleteListener(OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Log.w("TAG", "getInstanceId failed", task.exception)
                                    Toast.makeText(
                                        applicationContext,
                                        "error",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return@OnCompleteListener
                                }
                                val userToken = task.result?.token.toString()
                                hashMap.put("userToken", userToken)
                                hashMap.put("downloadUrl", downloadUrl)
                                hashMap.put("userEmail", auth.currentUser!!.email.toString())
                                hashMap.put("username", registerUsername.text.toString())
                                hashMap.put("registerDate", com.google.firebase.Timestamp.now())
                                hashMap.put("nickLol", "")
                                hashMap.put("linkLol", "")
                                hashMap.put("nickCs", "")
                                hashMap.put("linkCs", "")
                                hashMap.put("nickPubg", "")
                                hashMap.put("linkPubg", "")

                                db.collection("Users").document(auth.currentUser!!.uid).set(hashMap)
                                    .addOnCompleteListener { task ->
                                        if (task.isComplete && task.isSuccessful) {
                                            finish()
                                        }
                                    }.addOnFailureListener { exception ->
                                        Toast.makeText(
                                            applicationContext,
                                            exception.localizedMessage,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            })

                    }
                }
            }
        }
    }


    fun registerUser(): Boolean {
        val email = registerEmail.text.toString()
        val username = registerUsername.text.toString()
        val password = registerPassword.text.toString()
        val passwordConfirm = registerPasswordConfirm.text.toString()
        //val image = registerImageView.

        if (selectedPicture == null) {
            Toast.makeText(
                applicationContext,
                "Lütfen bir profil fotoğrafı seçiniz!",
                Toast.LENGTH_LONG
            ).show()
            registerImageView.requestFocus()
            return false
        } else if (email.isEmpty()) {
            registerEmail.error = "E-Posta girilmesi gereklidir!"
            registerEmail.requestFocus()
            return false
        } else if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(registerEmail.text.toString())
                .matches()
        ) {
            registerEmail.error = "Lütfen geçerli bir e-posta giriniz!"
            registerEmail.requestFocus()
            return false
        } else if (username.isEmpty()) {
            registerUsername.error = "Kullanıcı adı boş bırakılamaz"
            registerUsername.requestFocus()
            return false
        } else if (password.isEmpty()) {
            registerPassword.error = "Parola boş bırakılamaz"
            registerPassword.requestFocus()
            return false
        } else if (password.length < 6) {
            registerPassword.error = "Parolanız en az 6 karakterden oluşmalıdır!"
            registerPassword.requestFocus()
            return false
        } else if (passwordConfirm.isEmpty()) {
            registerPasswordConfirm.error = "Parola onayı boş bırakılamaz!"
            registerPasswordConfirm.requestFocus()
            return false
        } else if (password != passwordConfirm) {
            registerPasswordConfirm.error = "Parolalarınız eşleşmedi"
            registerPasswordConfirm.requestFocus()
            return false
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    auth.signInWithEmailAndPassword(
                        registerEmail.text.toString(),
                        registerPassword.text.toString()
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Kayıt başarılı!",
                                Toast.LENGTH_LONG
                            ).show()
                            Toast.makeText(
                                applicationContext,
                                "Hoşgeldiniz: ${auth.currentUser?.email.toString()}",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(
                            applicationContext,
                            exception.localizedMessage.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }.addOnFailureListener { exception ->
                if (exception != null) {
                    Toast.makeText(
                        applicationContext,
                        exception.localizedMessage.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        return true
        }
    }

    fun registerImageViewClicked(view: View) {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            selectedPicture = data.data
            try {
                if (selectedPicture != null) {
                    if (Build.VERSION.SDK_INT >= 28) {
                        val source = ImageDecoder.createSource(contentResolver, selectedPicture!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        registerImageView.setImageBitmap(bitmap)
                    } else {
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, selectedPicture)
                        registerImageView.setImageBitmap(bitmap)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}