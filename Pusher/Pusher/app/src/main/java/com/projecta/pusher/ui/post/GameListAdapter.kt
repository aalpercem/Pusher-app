package com.projecta.pusher.ui.post

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projecta.pusher.R
import kotlinx.android.synthetic.main.add_game_list_row.view.*


class GameListAdapter(val arrayList: ArrayList<Model>, private val context: Context) :
    RecyclerView.Adapter<GameListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: Model) {
            itemView.addGameRowTitle.text = model.title
            itemView.addGameRowImage.setImageResource(model.image)
            itemView.addGameRowNick.text = model.gameNick
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.add_game_list_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
        holder.itemView.setOnClickListener {
            if (arrayList[position].title == "League Of Legends") {
                val intent = (Intent(context, AddPostActivity::class.java))
                intent.putExtra("title", arrayList[position].title)
                intent.putExtra("image", (arrayList[position].image))
                intent.putExtra("maxFriend",arrayList[position].maxFriend)
                context.startActivity(intent)
            } else if (arrayList[position].title == "Counter-Strike\nGlobal Offensive") {
                val intent = (Intent(context, AddPostActivity::class.java))
                intent.putExtra("title", arrayList[position].title)
                intent.putExtra("image", arrayList[position].image)
                intent.putExtra("maxFriend",arrayList[position].maxFriend)
                context.startActivity(intent)
            } else if (arrayList[position].title == "PUBG") {
                val intent = (Intent(context, AddPostActivity::class.java))
                intent.putExtra("title", arrayList[position].title)
                intent.putExtra("image", arrayList[position].image)
                intent.putExtra("maxFriend",arrayList[position].maxFriend)
                context.startActivity(intent)
            }
        }
    }

}