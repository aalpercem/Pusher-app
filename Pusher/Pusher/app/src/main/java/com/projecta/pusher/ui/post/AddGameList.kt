package com.projecta.pusher.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projecta.pusher.R
import kotlinx.android.synthetic.main.activity_add_game_list.*

class AddGameList : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var nickLol: String
    private lateinit var nickCs: String
    private lateinit var nickPubg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game_list)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val user = auth.currentUser!!
        val docRef = db.collection("Users").document(user.uid)
        val arrayList = ArrayList<Model>()


        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                Log.d("one", "DocumentSnapshot data: ${document.data}")

                                nickLol = document.get("nickLol") as String
                                nickPubg = document.get("nickPubg") as String
                                nickCs = document.get("nickCs") as String

                                arrayList.add(Model("League Of Legends",R.drawable.lol_logo,5,nickLol))
                                arrayList.add(Model("PUBG", R.drawable.pubg_logo, 4, nickPubg))
                                arrayList.add(Model("Counter-Strike\nGlobal Offensive", R.drawable.cs_logo, 5, nickCs))

                                val MyAdapter = GameListAdapter(arrayList, this)
                                recyclerView.layoutManager = LinearLayoutManager(this)
                                recyclerView.adapter = MyAdapter
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