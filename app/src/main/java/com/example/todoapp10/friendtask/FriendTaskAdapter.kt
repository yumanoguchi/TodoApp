package com.example.todoapp10.friendtask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_friend_task.view.*
import kotlinx.android.synthetic.main.item_todo.view.*
import kotlinx.android.synthetic.main.item_todo.view.txtShowCategory
import kotlinx.android.synthetic.main.item_todo.view.txtShowDate
import kotlinx.android.synthetic.main.item_todo.view.txtShowTitle
import kotlinx.android.synthetic.main.item_todo.view.viewColorTag
import todoapp10.R
import java.text.SimpleDateFormat
import java.util.*

class FriendTaskAdapter(val list: List<FriendTaskModel>) : RecyclerView.Adapter<FriendTaskAdapter.FriendTaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendTaskViewHolder {
        return FriendTaskViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friend_task, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: FriendTaskViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    class FriendTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(FriendTaskModel: FriendTaskModel) {
            with(itemView) {
                val colors = resources.getIntArray(R.array.random_color)
                val randomColor = colors[Random().nextInt(colors.size)]

                viewColorTag.setBackgroundColor(randomColor)
                txtShowTitle.text = FriendTaskModel.title
                txtShowCategory.text = FriendTaskModel.category
                updateDate(FriendTaskModel.date)
            }
        }

        private fun updateDate(time: Long) {
            //Mon, 5 Jan 2020
            val myformat = "EEE, d MMM yyyy"
            val sdf = SimpleDateFormat(myformat)
            itemView.txtShowDate.text = sdf.format(Date(time))

        }


    }

}




