package com.projecta.pusher.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projecta.pusher.MainActivity
import com.projecta.pusher.R
import com.projecta.pusher.ui.home.AdapterHome
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import java.lang.Exception
import java.text.DateFormat

class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    var gameNickFromFB: ArrayList<String> = ArrayList()
    var gameLinkFromFB: ArrayList<String> = ArrayList()
    var gameImgResFromFB: ArrayList<Int> = ArrayList()

    var profileUrl: String? = null
    var profileUsername: String? = null

    var adapter: ProfileTimelineAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        getDataFromFirestore()

        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val rv = root.findViewById<View>(R.id.gamesRecycler) as RecyclerView

        val fab_logout = root.findViewById<ExtendedFloatingActionButton>(R.id.logOutButton)


        fab_logout.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Emin misin?")
            builder.setMessage("Oturumun Kapatılacak!")
            builder.setPositiveButton("Evet"){ dialog, which ->
                auth.signOut()
                val i = Intent(
                    activity,
                    MainActivity::class.java
                )
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
                activity?.finish()
            }

            builder.setNegativeButton("Hayır"){ dialog, which ->
            }
            builder.show()
        }
        val newGameButton = root.findViewById<ExtendedFloatingActionButton>(R.id.newGameButton)
        newGameButton.setOnClickListener {
            val i = Intent(
                activity,
                ProfileGameMenu::class.java
            )
            startActivity(i)
        }

        val layoutManager = LinearLayoutManager(activity)
        rv.layoutManager = layoutManager
        adapter = ProfileTimelineAdapter(
            gameNickFromFB,
            gameLinkFromFB,
            gameImgResFromFB
        )
        rv.adapter = adapter
        return root
    }

    private fun getDataFromFirestore() {
        val usernameRef = db.collection("Users").document(auth.currentUser?.uid.toString())

        usernameRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    usernameRef.get()
                        .addOnSuccessListener { document ->

                            gameNickFromFB.clear()
                            gameLinkFromFB.clear()

                            if (document != null) {
                                Log.d("one", "DocumentSnapshot data: ${document.data}")

                                profileUrl = document.getString("downloadUrl").toString()
                                if (profileImgView != null) {
                                    Picasso.get().load(profileUrl).into(profileImgView)
                                }
                                profileUsername = document.getString("username").toString()
                                if (profileUsernameTitle != null) {
                                    profileUsernameTitle.text = profileUsername
                                }
                                val nickLol = document.get("nickLol") as String
                                val linkLol = document.get("linkLol") as String
                                val nickPubg = document.get("nickPubg") as String
                                val linkPubg = document.get("linkPubg") as String
                                val nickCs = document.get("nickCs") as String
                                val linkCs = document.get("linkCs") as String

                                if (nickLol.isNotEmpty()) {
                                    gameNickFromFB.add(nickLol)
                                    gameImgResFromFB.add(R.drawable.lol_logo)
                                    if (linkLol.isEmpty()) {
                                        gameLinkFromFB.add("")
                                    } else if (linkLol.isNotEmpty()) {
                                        gameLinkFromFB.add(linkLol)
                                    }
                                }
                                if (nickPubg.isNotEmpty()) {
                                    gameNickFromFB.add(nickPubg)
                                    gameImgResFromFB.add(R.drawable.pubg_logo)
                                    if (linkPubg.isEmpty()) {
                                        gameLinkFromFB.add("")
                                    } else if (linkPubg.isNotEmpty()) {
                                        gameLinkFromFB.add(linkPubg)
                                    }
                                }
                                if (nickCs.isNotEmpty()) {
                                    gameNickFromFB.add(nickCs)
                                    gameImgResFromFB.add(R.drawable.cs_logo)
                                    if (linkCs.isEmpty()) {
                                        gameLinkFromFB.add("")
                                    } else if (linkCs.isNotEmpty()) {
                                        gameLinkFromFB.add(linkCs)
                                    }
                                }
                                adapter!!.notifyDataSetChanged()
                            } else {
                                Log.d("two", "No such document")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d("three", "get failed with ", exception)
                        }
                }
            }
    }
}