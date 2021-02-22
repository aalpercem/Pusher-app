package com.projecta.pusher.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projecta.pusher.Notification
import com.projecta.pusher.R
import com.squareup.picasso.Picasso

class AdapterHome(
    private val postUidArray: ArrayList<String>,
    private val gameUrlArray: ArrayList<String>,
    private val postDowUrlArray: ArrayList<String>,
    private val postTimeArray: ArrayList<String>,
    private val postUsernameArray: ArrayList<String>,
    private val selectPerArray: ArrayList<String>
) :
    RecyclerView.Adapter<AdapterHome.PostHolder>() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var userPostToken: String
    private lateinit var username: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.home_row, parent, false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return postUsernameArray.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val usernameRef = db.collection("Users").document(auth.currentUser?.uid.toString())

        usernameRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    usernameRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                Log.d("one", "DocumentSnapshot data: ${document.data}")
                                username = document.getString("username").toString()
                            } else {
                                Log.d("two", "No such document")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d("three", "get failed with ", exception)
                        }
                    }

                }


        holder.homeRowGameImg?.setImageResource(gameUrlArray[position].toInt())
        Picasso.get().load(postDowUrlArray[position]).into(holder.homePp)
        holder.homeRowNick?.text = postUsernameArray[position]

        val usersRef = db.collection("Users").document(postUidArray[position])
        val notification: Notification =
            Notification()
        holder.postJoinButton?.setOnClickListener {
            usersRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("one", "DocumentSnapshot data: ${document.data}")
                        userPostToken = document.getString("userToken").toString()
                        notification.sendNotification(
                            userPostToken,
                            "Katılma İsteği",
                            "$username oyununuza katılmak istiyor."
                        )
                    } else {
                        Log.d("two", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("three", "get failed with ", exception)
                }

        }

        if (selectPerArray[position].toInt() == 1) {
            if (R.drawable.pubg_logo == gameUrlArray[position].toInt()) {
                holder.home_check_5?.visibility = View.GONE
                holder.home_check_1?.setImageResource(R.drawable.deactive)
                holder.home_check_2?.setImageResource(R.drawable.deactive)
                holder.home_check_3?.setImageResource(R.drawable.deactive)
                holder.home_check_4?.setImageResource(R.drawable.deactive)
                holder.home_check_1?.setImageResource(R.drawable.active)
            } else {
                holder.home_check_1?.setImageResource(R.drawable.deactive)
                holder.home_check_2?.setImageResource(R.drawable.deactive)
                holder.home_check_3?.setImageResource(R.drawable.deactive)
                holder.home_check_4?.setImageResource(R.drawable.deactive)
                holder.home_check_5?.setImageResource(R.drawable.deactive)
                holder.home_check_1?.setImageResource(R.drawable.active)
            }
        } else if (selectPerArray[position].toInt() == 2) {
            if (R.drawable.pubg_logo == gameUrlArray[position].toInt()) {
                holder.home_check_5?.visibility = View.GONE
                holder.home_check_1?.setImageResource(R.drawable.deactive)
                holder.home_check_2?.setImageResource(R.drawable.deactive)
                holder.home_check_3?.setImageResource(R.drawable.deactive)
                holder.home_check_4?.setImageResource(R.drawable.deactive)
                holder.home_check_1?.setImageResource(R.drawable.active)
                holder.home_check_2?.setImageResource(R.drawable.active)
            } else {
                holder.home_check_1?.setImageResource(R.drawable.deactive)
                holder.home_check_2?.setImageResource(R.drawable.deactive)
                holder.home_check_3?.setImageResource(R.drawable.deactive)
                holder.home_check_4?.setImageResource(R.drawable.deactive)
                holder.home_check_5?.setImageResource(R.drawable.deactive)
                holder.home_check_1?.setImageResource(R.drawable.active)
                holder.home_check_2?.setImageResource(R.drawable.active)
            }
        } else if (selectPerArray[position].toInt() == 3) {
            if (R.drawable.pubg_logo == gameUrlArray[position].toInt()) {
                holder.home_check_5?.visibility = View.GONE
                holder.home_check_1?.setImageResource(R.drawable.deactive)
                holder.home_check_2?.setImageResource(R.drawable.deactive)
                holder.home_check_3?.setImageResource(R.drawable.deactive)
                holder.home_check_4?.setImageResource(R.drawable.deactive)
                holder.home_check_1?.setImageResource(R.drawable.active)
                holder.home_check_2?.setImageResource(R.drawable.active)
                holder.home_check_3?.setImageResource(R.drawable.active)
            } else {
                holder.home_check_1?.setImageResource(R.drawable.deactive)
                holder.home_check_2?.setImageResource(R.drawable.deactive)
                holder.home_check_3?.setImageResource(R.drawable.deactive)
                holder.home_check_4?.setImageResource(R.drawable.deactive)
                holder.home_check_5?.setImageResource(R.drawable.deactive)
                holder.home_check_1?.setImageResource(R.drawable.active)
                holder.home_check_2?.setImageResource(R.drawable.active)
                holder.home_check_3?.setImageResource(R.drawable.active)
            }
        } else if (selectPerArray[position].toInt() == 4) {
            if (R.drawable.pubg_logo != gameUrlArray[position].toInt()) {
                holder.home_check_1?.setImageResource(R.drawable.deactive)
                holder.home_check_2?.setImageResource(R.drawable.deactive)
                holder.home_check_3?.setImageResource(R.drawable.deactive)
                holder.home_check_4?.setImageResource(R.drawable.deactive)
                holder.home_check_5?.setImageResource(R.drawable.deactive)
                holder.home_check_1?.setImageResource(R.drawable.active)
                holder.home_check_2?.setImageResource(R.drawable.active)
                holder.home_check_3?.setImageResource(R.drawable.active)
                holder.home_check_4?.setImageResource(R.drawable.active)
            }
        } else if (selectPerArray[position].toInt() == 5) {
            //delete post
        }
        holder.homeRowTime?.text = postTimeArray[position]
    }

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {

        var homePp: ImageView? = null
        var homeRowGameImg: ImageView? = null
        var homeRowNick: TextView? = null
        var rowLinearLayout: LinearLayout? = null
        var homeRowTime: TextView? = null
        var postJoinButton: ExtendedFloatingActionButton? = null
        var home_check_1: ImageView? = null
        var home_check_2: ImageView? = null
        var home_check_3: ImageView? = null
        var home_check_4: ImageView? = null
        var home_check_5: ImageView? = null

        init {
            homePp = view.findViewById(R.id.homePp)
            homeRowGameImg = view.findViewById(R.id.homeRowGameImg)
            homeRowNick = view.findViewById(R.id.homeRowNick)
            rowLinearLayout = view.findViewById(R.id.rowLinearLayout)
            homeRowTime = view.findViewById(R.id.homeRowTime)
            postJoinButton = view.findViewById(R.id.postJoinButton)
            home_check_1 = view.findViewById(R.id.home_check_1)
            home_check_2 = view.findViewById(R.id.home_check_2)
            home_check_3 = view.findViewById(R.id.home_check_3)
            home_check_4 = view.findViewById(R.id.home_check_4)
            home_check_5 = view.findViewById(R.id.home_check_5)
        }
    }
}