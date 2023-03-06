package com.example.todolist

import android.app.Application
import com.example.todolist.data.task.dto.TaskItemDatabase
import com.example.todolist.data.task.repository.TaskRepository

class TodoApplication: Application() {

    private val database by lazy { TaskItemDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        TASK_REPOSITORY = TaskRepository(database.taskItemDao())
    }



    companion object {
        lateinit var TASK_REPOSITORY: TaskRepository
    }
}