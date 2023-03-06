package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist.databinding.ActivityMainBinding
import android.widget.EditText
import android.widget.TimePicker
import androidx.core.graphics.drawable.toDrawable
import com.example.todolist.data.task.TaskDataItemDTO

import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import java.lang.Exception
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)



        try {

            val myDataBase = this.openOrCreateDatabase("ToDoList", MODE_PRIVATE, null)

            myDataBase.execSQL("CREATE TABLE IF NOT EXISTS toDoList (title VARCHAR, description VARCHAR)")

            myDataBase.execSQL("INSERT INTO toDoList(title, description) VALUES('Yeni Başlık', 'ui işlemleri')")

        }catch (e: Exception) {

        }

        setContentView(binding.root)

    }



}


