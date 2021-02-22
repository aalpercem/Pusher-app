package com.projecta.pusher.ui.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projecta.pusher.R
import kotlinx.android.synthetic.main.profile_game_row.view.*

class ProfileGameMenuAdapter(val arrayList_profile: ArrayList<ProfileGameMenuModel>, private val context: Context) :
    RecyclerView.Adapter<ProfileGameMenuAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: ProfileGameMenuModel) {
            itemView.profileGameMenuRowNick.text = model.title
            itemView.profileGameMenuImage.setImageResource(model.image)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileGameMenuAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.profile_game_row, parent, false)
        return ProfileGameMenuAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList_profile.size
    }

    override fun onBindViewHolder(holder: ProfileGameMenuAdapter.ViewHolder, position: Int) {
        holder.bindItems(arrayList_profile[position])
        holder.itemView.setOnClickListener {
            if (arrayList_profile[position].title == "League Of Legends") {
                val intent = (Intent(context, ProfileAddGame::class.java))
                intent.putExtra("title", arrayList_profile[position].title)
                intent.putExtra("image", (arrayList_profile[position].image))
                context.startActivity(intent)
            } else if (arrayList_profile[position].title == "Counter-Strike\nGlobal Offensive") {
                val intent = (Intent(context, ProfileAddGame::class.java))
                intent.putExtra("title", arrayList_profile[position].title)
                intent.putExtra("image", arrayList_profile[position].image)
                context.startActivity(intent)
            } else if (arrayList_profile[position].title == "PUBG") {
                val intent = (Intent(context, ProfileAddGame::class.java))
                intent.putExtra("title", arrayList_profile[position].title)
                intent.putExtra("image", arrayList_profile[position].image)
                context.startActivity(intent)
            }
        }
    }
}