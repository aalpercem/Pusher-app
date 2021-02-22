package com.projecta.pusher.ui.home

import android.annotation.SuppressLint
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.iid.FirebaseInstanceId
import com.projecta.pusher.R
import com.projecta.pusher.ui.post.AddGameList
import java.text.DateFormat


class HomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    var gameUrlArrayFromFB: ArrayList<String> = ArrayList()
    var postUidArrayFromFB: ArrayList<String> = ArrayList()
    var postDowUrlArrayFromFB: ArrayList<String> = ArrayList()
    var postTimeArrayFromFB: ArrayList<String> = ArrayList()
    var postUsernameArrayFromFB: ArrayList<String> = ArrayList()
    var selectPerArrayFromFB: ArrayList<String> = ArrayList()

    var adapter: AdapterHome? = null

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val rv = root.findViewById<View>(R.id.recyclerViewHome) as RecyclerView
        val hashMap = hashMapOf<String, Any>()
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                val userToken = task.result?.token
                hashMap.put("userToken", userToken.toString())
                db.collection("Users").document(auth.currentUser!!.uid).update(hashMap)
                    .addOnCompleteListener { task ->
                        print("token alındı")
                    }.addOnFailureListener { exception ->
                      //  Toast.makeText(activity, exception.localizedMessage, Toast.LENGTH_LONG).show()
                    }
            })

        getDataFromFirestore()
        val layoutManager = LinearLayoutManager(activity)
        rv.layoutManager = layoutManager
        adapter = AdapterHome(
            postUidArrayFromFB,
            gameUrlArrayFromFB,
            postDowUrlArrayFromFB,
            postTimeArrayFromFB,
            postUsernameArrayFromFB,
            selectPerArrayFromFB
        )
        rv.adapter = adapter


        val fab_add = root.findViewById<FloatingActionButton>(R.id.addPostClick)
        fab_add.setOnClickListener {
            val intent = Intent(activity, AddGameList::class.java)
            startActivity(intent)
        }
        return root
    }

    private fun getDataFromFirestore() {
        db.collection("Posts").orderBy("postTime",
            Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(activity, exception.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (snapshot != null) {
                    if (!snapshot.isEmpty) {

                        postUidArrayFromFB.clear()
                        gameUrlArrayFromFB.clear()
                        postDowUrlArrayFromFB.clear()
                        postTimeArrayFromFB.clear()
                        postUsernameArrayFromFB.clear()
                        selectPerArrayFromFB.clear()

                        val documents = snapshot.documents
                        for (document in documents) {
                            val userUid = document.get("userUid") as String
                            val gameUrl = document.get("gameUrl") as Long
                            val postDowUrl = document.get("postDowUrl") as String
                            val postTime = document.get("postTime") as com.google.firebase.Timestamp
                            val postUsername = document.get("postUsername") as String
                            val selectPer = document.get("selectPer") as String

                            val date: java.util.Date = postTime.toDate()
                            val format: String = DateFormat.getTimeInstance().format(date)

                            postUidArrayFromFB.add(userUid)
                            gameUrlArrayFromFB.add(gameUrl.toString())
                            postDowUrlArrayFromFB.add(postDowUrl)
                            postTimeArrayFromFB.add(format)
                            postUsernameArrayFromFB.add(postUsername)
                            selectPerArrayFromFB.add(selectPer)

                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
}