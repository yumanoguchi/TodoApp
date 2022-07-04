package com.example.todoapp10.friend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bs_tender.FriendListDeserializer
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_main.*
import todoapp10.R

class FriendActivity : AppCompatActivity() {

    val list = arrayListOf<FriendModel>()
    var adapter = FriendAdapter(list)

    override fun onResume() {
        super.onResume()

        val id = 1
        val name = "kato"
        val pass = "soma"

        val baseUrl: String = "http://160.16.141.77:50180/user/${id}/friend?name=${name}&pass=${pass}"
        val endpoint: String = "/match.json"
        val url: String = baseUrl

        url.httpGet().responseObject(FriendListDeserializer()) { req, res, result ->
            val(users,err) = result
            Log.i("testres","${result}")

            list.clear()
            if (users != null) {
                users.forEach {

                    list.add(it)
                }
                Log.i("testfl","${list}")
            }

            adapter.notifyDataSetChanged()
        }
        Log.i("testadapter","abc")


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)
        setSupportActionBar(toolbar)
        todoRv.apply {
            layoutManager = LinearLayoutManager(this@FriendActivity)
            adapter = this@FriendActivity.adapter
        }
        initSwipe()
    }

    fun initSwipe() {

        /*
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {

                    val taskId = list[position].id

                    list.removeAt(position)
                    adapter.notifyItemRemoved(position)

                    val baseUrl: String = "http://160.16.141.77:50180/user/1/task/${taskId}?name=kato&pass=soma"
                    val endpoint: String = "/match.json"
                    val url: String = baseUrl

                    url.httpDelete().response() { req, res, result ->
                        Log.i("test","${res}")
                    }


                } else if (direction == ItemTouchHelper.RIGHT) {

                    val taskId = list[position].id
                    list.removeAt(position)
                    adapter.notifyItemRemoved(position)

                    val baseUrl: String = "http://160.16.141.77:50180/user/1/task/${taskId}?name=kato&pass=soma"
                    val endpoint: String = "/match.json"
                    val url: String = baseUrl

                    url.httpDelete().response() { req, res, result ->
                        Log.i("test","${res}")
                    }
                }
            }
            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView

                    val paint = Paint()
                    val icon: Bitmap

                    if (dX > 0) {

                        icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_check_white_png)

                        paint.color = Color.parseColor("#388E3C")

                        canvas.drawRect(
                            itemView.left.toFloat(), itemView.top.toFloat(),
                            itemView.left.toFloat() + dX, itemView.bottom.toFloat(), paint
                        )

                        canvas.drawBitmap(
                            icon,
                            itemView.left.toFloat(),
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )


                    } else {
                        icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_delete_white_png)

                        paint.color = Color.parseColor("#D32F2F")

                        canvas.drawRect(
                            itemView.right.toFloat() + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                        )

                        canvas.drawBitmap(
                            icon,
                            itemView.right.toFloat() - icon.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    }
                    viewHolder.itemView.translationX = dX


                } else {
                    super.onChildDraw(
                        canvas,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }


        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(todoRv)

         */
    }
}
