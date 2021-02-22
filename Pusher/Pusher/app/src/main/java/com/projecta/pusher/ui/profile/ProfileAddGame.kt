package com.projecta.pusher.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.projecta.pusher.HomeActivity
import com.projecta.pusher.R
import kotlinx.android.synthetic.main.activity_add_post.gameImgProfile
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.add_game_profile.*

class ProfileAddGame : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        lateinit var nickLol: String
        lateinit var nickPubg: String
        lateinit var nickCs: String
        var linkLol: String?
        var linkPubg: String?
        var linkCs: String?

        setContentView(R.layout.add_game_profile)

        val title = intent.getStringExtra("title")
        val image = intent.getIntExtra("image", R.drawable.error)
        val user = auth.currentUser!!

        addGameButtonProfile.setOnClickListener {
            if (gameNick.text.toString().isEmpty()) {
                gameNick.error = "Lütfen oyun içinde ki isminizi yazınız."
                gameNick.requestFocus()
            } else {
                val gameMap = hashMapOf<String, Any>()
                val docRef = db.collection("Users").document(user.uid)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            Log.d("one", "DocumentSnapshot data: ${document.data}")
                            if (title == "League Of Legends") {
                                nickLol = gameNick.text.toString()
                                gameMap.put("nickLol", nickLol)
                                if (gameLink.toString().isNotEmpty()) {
                                    linkLol = gameLink.text.toString()
                                    gameMap.put("linkLol", linkLol!!)
                                }
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else if (title == "Counter-Strike\nGlobal Offensive") {
                                nickCs = gameNick.text.toString()
                                gameMap.put("nickCs", nickCs)
                                if (gameLink.toString().isNotEmpty()) {
                                    linkCs = gameLink.text.toString()
                                    gameMap.put("linkCs", linkCs!!)
                                }
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else if (title == "PUBG") {
                                nickPubg = gameNick.text.toString()
                                gameMap.put("nickPubg", nickPubg)
                                if (gameLink.toString().isNotEmpty()) {
                                    linkPubg = gameLink.text.toString()
                                    gameMap.put("linkPubg", linkPubg!!)
                                }
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                println("Tebrikler açığımızı buldunuz ! Lütfen bize bildirin !")
                            }
                            docRef.update(gameMap).addOnCompleteListener { task ->
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
                        } else {
                            Log.d("two", "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("three", "get failed with ", exception)
                    }
            }

        }

        cancelGameButtonProfile.setOnClickListener {
            finish()
        }
        addProfileGameText.setText(title)
        gameImgProfile.setImageResource(image)
    }
}