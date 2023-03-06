package com.example.todolist.data.task.dto

import androidx.room.*
import com.example.todolist.data.task.TaskDataItemDTO
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskItemDao {

    @Query("SELECT * FROM task_item_table ORDER BY id DESC")
    fun getAll(): Flow<List<TaskDataItemDTO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskItem(taskDataItemDTO: TaskDataItemDTO)

    @Update
    suspend fun updateTaskItem(taskDataItemDTO: TaskDataItemDTO)

    @Query("DELETE FROM task_item_table WHERE id = :id")
    suspend fun deleteTaskItem(id : Long)


}