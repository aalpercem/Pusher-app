package com.projecta.pusher.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projecta.pusher.R

class ProfileTimelineAdapter(
    private val gameNickArray: ArrayList<String>,
    private val gameLinkArray: ArrayList<String>,
    private val gameImgArray: ArrayList<Int>
) :
    RecyclerView.Adapter<ProfileTimelineAdapter.PostHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.profile_game_menu_row, parent, false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return gameNickArray.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.gameImg?.setImageResource(gameImgArray[position])
        holder.gameNick?.text = gameNickArray[position]
        holder.gameLink?.text = gameLinkArray[position]
    }

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        var gameImg: ImageView? = null
        var gameNick: TextView? = null
        var gameLink: TextView? = null

        init {
            gameImg = view.findViewById(R.id.profileGameMenuImage)
            gameNick = view.findViewById(R.id.profileGameMenuRowNick)
            gameLink = view.findViewById(R.id.profileGameMenuRowLink)
        }

    }


}