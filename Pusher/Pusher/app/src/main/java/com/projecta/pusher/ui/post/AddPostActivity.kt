package com.projecta.pusher.ui.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projecta.pusher.HomeActivity
import com.projecta.pusher.R
import kotlinx.android.synthetic.main.activity_add_post.*

class AddPostActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val title = intent.getStringExtra("title")
        val image = intent.getIntExtra("image", R.drawable.error)
        val maxFriend = intent.getIntExtra("maxFriend", 0)

        addProfileGameText.setText(title)
        gameImgProfile.setImageResource(image)
        var selectPer: Int = 0
        if (maxFriend == 4) {
            friend5.visibility = View.GONE
        }

        //Strategy Pattern

        val changeOne = ChangeOne(
            friend1,
            friend2,
            friend3,
            friend4,
            friend5,
            selectPer
        )
        val changeTwo = ChangeTwo(
            friend1,
            friend2,
            friend3,
            friend4,
            friend5,
            selectPer
        )
        val changeThree = ChangeThree(
            friend1,
            friend2,
            friend3,
            friend4,
            friend5,
            selectPer
        )
        val changeFour = ChangeFour(
            friend1,
            friend2,
            friend3,
            friend4,
            friend5,
            selectPer
        )
        val changeFive = ChangeFive(
            friend1,
            friend2,
            friend3,
            friend4,
            friend5,
            selectPer
        )

        friend1.setOnClickListener {
            selectPer = StrategyPattern(changeOne).fChange()
        }
        friend2.setOnClickListener {
            selectPer = StrategyPattern(changeTwo).fChange()
        }
        friend3.setOnClickListener {
            selectPer = StrategyPattern(changeThree).fChange()
        }
        friend4.setOnClickListener {
            selectPer = StrategyPattern(changeFour).fChange()
        }
        friend5.setOnClickListener {
            selectPer = StrategyPattern(changeFive).fChange()
        }


//        **********************************

        addGameButtonProfile.setOnClickListener {
            val user = auth.currentUser!!

            if (selectPer == 0) {
                Toast.makeText(
                    applicationContext,
                    "Lütfen geçerli arkadaş sayısı seçiniz!",
                    Toast.LENGTH_LONG
                ).show()
            } else if (selectPer == 4 && maxFriend == 4) {
                Toast.makeText(
                    applicationContext,
                    "Takımın doluyken arkadaş arayamazsın!",
                    Toast.LENGTH_LONG
                ).show()
            } else if (selectPer == 5 && maxFriend == 5) {
                Toast.makeText(
                    applicationContext,
                    "Takımın doluyken arkadaş arayamazsın!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val postMap = hashMapOf<String, Any>()
                val docRef = db.collection("Users").document(user.uid)
                lateinit var username: String
                lateinit var userUid: String
                lateinit var profileUrl: String

                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            Log.d("one", "DocumentSnapshot data: ${document.data}")
                            username = document.getString("username").toString()
                            profileUrl = document.getString("downloadUrl").toString()
                            postMap.put("gameUrl", image)
                            postMap.put("userUid", user.uid)
                            postMap.put("postDowUrl", profileUrl)
                            postMap.put("postTime", com.google.firebase.Timestamp.now())
                            postMap.put("postUsername", username)
                            postMap.put("selectPer", selectPer.toString())
                            db.collection("Posts").add(postMap).addOnCompleteListener { task ->
                                if (task.isComplete && task.isSuccessful) {
                                    val intent =
                                        Intent(applicationContext, HomeActivity::class.java)
                                    startActivity(intent)
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
        cancel_action.setOnClickListener {
            finish()
        }
    }
}