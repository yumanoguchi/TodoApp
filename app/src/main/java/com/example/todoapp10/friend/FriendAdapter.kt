package com.example.todoapp10.friend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_friend.view.*
import kotlinx.android.synthetic.main.item_todo.view.viewColorTag
import todoapp10.R
import java.util.*

class FriendAdapter(val list: List<FriendModel>) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friend, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(FriendModel: FriendModel) {
            with(itemView) {
                val colors = resources.getIntArray(R.array.random_color)
                val randomColor = colors[Random().nextInt(colors.size)]
                viewColorTag.setBackgroundColor(randomColor)

                txtShowName.text = FriendModel.name
                //txtShowCategory.text = FriendModel.name
            }
        }
    }
}


