package com.example.todoapp10

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.httpPost
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.activity_task.SearchBtn
import todoapp10.R
import java.text.SimpleDateFormat
import java.util.*

class SearchActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        SearchBtn.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.SearchBtn -> {
                saveTodo()
            }
        }
    }

    private fun saveTodo() {

        val ID = FriendIDInpLay.editText?.text.toString()
        val id = 1
        val name = "kato"
        val pass = "soma"

        val baseUrl: String = "http://160.16.141.77:50180/user/${id}/friend/search?name=${name}&pass=${pass}&friendId=${ID}"
        val endpoint: String = "/match.json"
        val url: String = baseUrl

        url.httpPost().response { request, response, result ->
            Log.i("testresponse","${response}")
        }
        finish()
    }

}
