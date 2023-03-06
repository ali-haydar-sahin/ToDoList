package com.example.todolist.data.task.dto

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.data.task.TaskDataItemDTO

@Database(entities = ([TaskDataItemDTO::class]), version = 2,exportSchema = false)
abstract class TaskItemDatabase: RoomDatabase() {

    abstract fun taskItemDao(): TaskItemDao

    companion object {

        @Volatile
        private var INSTANCE: TaskItemDatabase? = null


        fun getDatabase(context: Context): TaskItemDatabase{
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskItemDatabase::class.java,
                    "task_item_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}