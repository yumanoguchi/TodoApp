package com.example.todoapp10.friendtask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bs_tender.FriendTaskDeserializer
import com.example.bs_tender.UserListDeserializer
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_main.*
import todoapp10.R
import java.text.SimpleDateFormat


class FriendTaskActivity : AppCompatActivity() {

    val list = arrayListOf<FriendTaskModel>()
    var adapter = FriendTaskAdapter(list)

    override fun onResume() {
        super.onResume()

        val id = 1

            val baseUrl: String = "http://160.16.141.77:50180/user/${id}/friend/task"
            val endpoint: String = "/match.json"
            val url: String = baseUrl

            url.httpGet().responseObject(FriendTaskDeserializer()) { req, res, result ->
                val(users,err) = result
                Log.i("test","${users}")
                list.clear()
                if (users != null) {
                    users.forEach {
                        var myCalendar = java.util.Calendar.getInstance()
                        val sdf = SimpleDateFormat("yyyy/MM/dd")
                        myCalendar.time = sdf.parse(it.date.toString())

                        list.add(FriendTaskModel(it.id,it.userName,it.title,it.category,it.date,it.priority,it.share,it.tag))
                    }
                    Log.i("testlist","${list}")
                }

                adapter.notifyDataSetChanged()
            }
            Log.i("testadapter","abc")


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_task)
        setSupportActionBar(toolbar)
        todoRv.apply {
            layoutManager = LinearLayoutManager(this@FriendTaskActivity)
            adapter = this@FriendTaskActivity.adapter
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

                    val sdf = SimpleDateFormat("yyyy/MM/dd")


                    val taskId = list[position].id
                    val id = 1
                    val name = list[position].title
                    val deadLine = sdf.format(list[position].date)
                    val priority = list[position].priority
                    list[position].share = true
                    val tag = list[position].tag



                    val bodyJson = """
                        {"taskId":${taskId},
                        "userId":${id},
                        "name":"${name}",
                        "deadLine":"${deadLine}",
                        "priority":${priority},
                        "share":true,
                        "tag":"${tag}"
                        }
                        """.trimIndent()

                    val baseUrl: String = "http://160.16.141.77:50180/user/${id}/task/${taskId}?name=kato&pass=soma"
                    val endpoint: String = "/match.json"
                    val url: String = baseUrl

                    val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")


                    url.httpPost().header(header).body(bodyJson).response() { req, res, result ->
                            Log.i("test","${res}")
                        }
                    onResume()


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

                        paint.color = Color.parseColor("#4169E1")

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
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val item = menu.findItem(R.id.search)
        val searchView = item.actionView as SearchView
        item.setOnActionExpandListener(object :MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                displayTodo()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                displayTodo()
                return true
            }

        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(!newText.isNullOrEmpty()){
                    displayTodo(newText)
                }
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    fun displayTodo(newText: String = "") {


         */


    }
}






