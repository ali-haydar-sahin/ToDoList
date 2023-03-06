package com.example.todolist.model


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.TodoApplication
import com.example.todolist.data.task.TaskDataItemDTO
import com.example.todolist.data.task.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(): ViewModel() {

    private val repository: TaskRepository = TodoApplication.TASK_REPOSITORY

    var taskDataItemDTO: LiveData<ArrayList<TaskDataItemDTO>> = repository.getAllTaskItems.asLiveData()




    fun addItems(newTask: TaskDataItemDTO) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTaskItems(newTask)
    }

    fun updateItems(taskItem: TaskDataItemDTO) = viewModelScope.launch {
        repository.updateTaskItems(taskItem)
    }

    fun deleteItems(id : Long) = viewModelScope.launch {
        repository.deleteTaskItems(id)
    }




}