package com.example.todolist.data.task

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.todolist.data.task.converter.DateConverter
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "task_item_table")
@TypeConverters(DateConverter::class)
data class TaskDataItemDTO(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "headerText") var headerText: String,
    @ColumnInfo(name = "descriptionText") var taskDescriptionText: String,
    @ColumnInfo(name = "dueDate") var dueDate: Date? = null,
    ) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskDataItemDTO

        if (id != other.id) return false

        return true
    }



    override fun hashCode(): Int {
        return id.hashCode()
    }
}



object TaskDataProvider {

    @Deprecated("Dummy dataya ihtiyaç kalmadı. Ilerleyen versiyonlarda silinecek")
    fun getTaskItem(): ArrayList<TaskDataItemDTO> {


        return arrayListOf(
            TaskDataItemDTO(1,"Bugün yapılacak işler", "ui işlemlerini bitir",),
            TaskDataItemDTO(2, "Bugün yapılacak işler", "ui işlemlerini bitir"),
            TaskDataItemDTO(3, "Bugün yapılacak işler", "ui işlemlerini bitir"),
            TaskDataItemDTO(4, "Bugün yapılacak işler", "ui işlemlerini bitir"),
            TaskDataItemDTO(5, "Bugün yapılacak işler", "ui işlemlerini bitir"),
        )

    }
}
