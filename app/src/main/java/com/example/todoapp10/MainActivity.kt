package com.example.todoapp10

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bs_tender.UserListDeserializer
import com.example.todoapp10.Apply.ApplyFriendActivity
import com.example.todoapp10.friend.FriendActivity
import com.example.todoapp10.friendtask.FriendTaskActivity
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import kotlinx.android.synthetic.main.activity_main.*
import todoapp10.R
import com.github.kittinunf.result.Result;
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.HashMap


class MainActivity : AppCompatActivity() {

    val list = arrayListOf<TodoModel>()
    var adapter = TodoAdapter(list)

    override fun onResume() {
        super.onResume()

            val baseUrl: String = "http://160.16.141.77:50180/user/1/task?name=kato&pass=soma"
            val endpoint: String = "/match.json"
            val url: String = baseUrl

            url.httpGet().responseObject(UserListDeserializer()) { req, res, result ->
                val(users,err) = result
                Log.i("test","${users}")
                list.clear()
                if (users != null) {
                    users.forEach {
                        var myCalendar = java.util.Calendar.getInstance()
                        val sdf = SimpleDateFormat("yyyy/MM/dd")
                        myCalendar.time = sdf.parse(it.deadLine)

                        list.add(TodoModel(it.name,it.tag,myCalendar.time.time,it.priority,it.share,it.taskId.toLong(),it.tag))
                    }
                    Log.i("testlist","${list}")
                }

                adapter.notifyDataSetChanged()
            }
            Log.i("testadapter","abc")


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        todoRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        initSwipe()
    }

    fun initSwipe() {


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

    }


    //?????????????????????

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.frendlist -> {
                startActivity(Intent(this, FriendActivity::class.java))
            }
            R.id.searchfriend -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }
            R.id.applyfriend-> {
                startActivity(Intent(this, ApplyFriendActivity::class.java))
            }
            R.id.friendtask-> {
                startActivity(Intent(this, FriendTaskActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }




    //??????????????????????????????
    fun openNewTask(view: View) {
        startActivity(Intent(this, TaskActivity::class.java))
    }

    //?????????????????????

}



//???????????????

class HttpAccessor {

    fun getJson(url: String): JSONObject {
        var red = JSONObject(mapOf("null" to 1))
        url.httpGet().responseJson() { request, response, result ->
            when (result) {
                is Result.Failure -> {

                    val ex = result.getException()
                    Log.i("Test", "${ex.toString()}")
                    red = JSONObject(mapOf("message" to ex.toString()))
                }
                is Result.Success -> {

                    red = result.get().obj()

                }
            }
        }
        return red
    }
    fun getJsonArray(url: String): JSONArray {
        var red = JSONArray()
        url.httpGet().responseJson() { request, response, result ->
            when (result) {
                is Result.Failure -> {

                    val ex = result.getException()
                    Log.i("Test","${ex.toString()}")
                    red.put(JSONObject(mapOf("message" to ex.toString())))
                }
                is Result.Success -> {

                    red = result.get() as JSONArray

                }
            }
        }
        return red
    }


}



