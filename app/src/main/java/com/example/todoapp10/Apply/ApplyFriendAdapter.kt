package com.example.todoapp10.Apply

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_friend.view.*
import kotlinx.android.synthetic.main.item_todo.view.viewColorTag
import todoapp10.R
import java.util.*

class ApplyFriendAdapter(val list: List<ApplyFriendModel>) : RecyclerView.Adapter<ApplyFriendAdapter.ApplyFriendViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyFriendViewHolder {
        return ApplyFriendViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friend_apply, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ApplyFriendViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    class ApplyFriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(ApplyFriendModel: ApplyFriendModel) {
            with(itemView) {
                val colors = resources.getIntArray(R.array.random_color)
                val randomColor = colors[Random().nextInt(colors.size)]
                viewColorTag.setBackgroundColor(randomColor)

                txtShowName.text = ApplyFriendModel.name
                //txtShowCategory.text = FriendModel.name
            }
        }
    }
}


