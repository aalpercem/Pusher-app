package com.projecta.pusher.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.projecta.pusher.R
import kotlinx.android.synthetic.main.activity_add_game_list.*

class ProfileGameMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_game_menu)
        val arrayList_profile = ArrayList<ProfileGameMenuModel>()
        arrayList_profile.add(ProfileGameMenuModel("League Of Legends", R.drawable.lol_logo))
        arrayList_profile.add(ProfileGameMenuModel("PUBG", R.drawable.pubg_logo))
        arrayList_profile.add(ProfileGameMenuModel("Counter-Strike\nGlobal Offensive", R.drawable.cs_logo))
        val MyAdapter = ProfileGameMenuAdapter(arrayList_profile,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter
    }
}