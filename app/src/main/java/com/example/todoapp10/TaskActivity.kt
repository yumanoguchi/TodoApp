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
import kotlinx.android.synthetic.main.activity_task.*
import todoapp10.R
import java.text.SimpleDateFormat
import java.util.*

const val DB_NAME = "todo.db"

class TaskActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var myCalendar: Calendar

    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    var finalDate = 0L
    var finalTime = 0L

    //優先度
    private val labels = arrayListOf("★", "★★", "★★★", "★★★★", "★★★★★")




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        dateEdt.setOnClickListener(this)
        SearchBtn.setOnClickListener(this)

        setUpSpinner()
    }

    private fun setUpSpinner() {
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, labels)

        labels.sort()

        spinnerCategory.adapter = adapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dateEdt -> {
                setListener()
            }
            R.id.timeEdt -> {
                setTimeListener()
            }
            R.id.SearchBtn -> {
                saveTodo()
            }
        }

    }

    private fun saveTodo() {
        val tag = spinnerCategory.selectedItem.toString()
        val priority = labels.withIndex().first { tag == it.value }.index+1
        val title = titleInpLay.editText?.text.toString()
        val id = 1
        val name = "kato"
        val pass = "soma"

        var myCalendar = java.util.Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd")

        val deadLine = sdf.format(finalDate)

        val bodyJson = """
            {"taskId":1,
             "userId":${id},
             "name":"${title}",
             "deadLine":"${deadLine}",
             "priority":${priority},
             "share":false,
             "tag":""
            }
        """.trimIndent()



        val baseUrl: String = "http://160.16.141.77:50180/user/${id}/task?name=${name}&pass=${pass}"
        val endpoint: String = "/match.json"
        val url: String = baseUrl

        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")

        url.httpPost().header(header).body(bodyJson).response { request, response, result ->
            Log.i("testtask","${response}")
            Log.i("testjson","${bodyJson}")

        }

        finish()
    }


    private fun setTimeListener() {
        myCalendar = Calendar.getInstance()

        timeSetListener =
            TimePickerDialog.OnTimeSetListener() { _: TimePicker, hourOfDay: Int, min: Int ->
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalendar.set(Calendar.MINUTE, min)
                updateTime()
            }

        val timePickerDialog = TimePickerDialog(
            this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()
    }


    private fun updateTime() {
        //Mon, 5 Jan 2020
        val myformat = "h:mm a"
        val sdf = SimpleDateFormat(myformat)
        finalTime = myCalendar.time.time


    }

    private fun setListener() {
        myCalendar = Calendar.getInstance()

        dateSetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate()

            }

        val datePickerDialog = DatePickerDialog(
            this, dateSetListener, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateDate() {
        //Mon, 5 Jan 2020
        val myformat = "EEE, d MMM yyyy"
        val sdf = SimpleDateFormat(myformat)
        finalDate = myCalendar.time.time
        dateEdt.setText(sdf.format(myCalendar.time))



    }




}
