package com.example.todolist.data.task.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.todolist.data.task.TaskDataItemDTO
import com.example.todolist.data.task.dto.TaskItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskItemDao: TaskItemDao) {


    val getAllTaskItems : Flow<ArrayList<TaskDataItemDTO>> = taskItemDao.getAll().map { ArrayList(it) }


    @WorkerThread
    suspend fun insertTaskItems(taskDataItemDTO: TaskDataItemDTO) {
        Log.d("TaskRepository", Thread.currentThread().name)
        taskItemDao.insertTaskItem(taskDataItemDTO)
    }

    suspend fun updateTaskItems(taskDataItemDTO: TaskDataItemDTO) {
        Log.d("TaskRepositorry", Thread.currentThread().name)
        taskItemDao.updateTaskItem(taskDataItemDTO)
    }

    @WorkerThread
    suspend fun deleteTaskItems(id: Long) {
        taskItemDao.deleteTaskItem(id)
    }


}