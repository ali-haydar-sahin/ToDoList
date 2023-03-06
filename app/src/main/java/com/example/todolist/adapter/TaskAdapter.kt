package com.example.todolist.ui.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.task.TaskDataItemDTO
import kotlin.collections.ArrayList


class TaskAdapter() :
    RecyclerView.Adapter<TaskDataItemViewHolder>() {
    private var listener: OnTaskItemClickListener? = null
    private var taskData: ArrayList<TaskDataItemDTO> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskDataItemViewHolder {
        val infilater = LayoutInflater.from(parent.context)
        val view = infilater.inflate(R.layout.list_item_home_card_view, parent, false)
        return TaskDataItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: TaskDataItemViewHolder, position: Int) {
        val taskItem = taskData[position]
        holder.bind(taskItem, listener)
    }

    override fun getItemCount(): Int = taskData.size

    /*fun addItem(newTask: TaskDataItemDTO) {
        taskMenuItem.add(0, newTask)
        notifyItemInserted(0)
    }*/

    /*fun updateItem(updateTask: TaskDataItemDTO) {
        val indexOf = taskMenuItem.indexOf(updateTask)
        if (indexOf == -1) return
        taskMenuItem[indexOf].let {
            it.headerText = updateTask.headerText
           *//* it.time = updateTask.time*//*
            it.taskDescriptionText = updateTask.taskDescriptionText
            notifyItemChanged(indexOf)
        }
        //NewTaskBottomSheet(updateTask).show(FragmentManager, "args")
    }*/

    /*fun removeItem(adapterPosition: Int) {
        taskData.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }*/

    fun getItem(position: Int): TaskDataItemDTO {
        return taskData[position]
    }

    fun setListener(listener: OnTaskItemClickListener) {
        this.listener = listener
    }

    fun updateData(newTaskData: ArrayList<TaskDataItemDTO> = arrayListOf()) {
        this.taskData = newTaskData
        notifyDataSetChanged()
    }


}

interface OnTaskItemClickListener {
    fun onTaskItemClick(view: View, listItem: TaskDataItemDTO)
}

class TaskDataItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val headerTaskInput: TextView = view.findViewById(R.id.taskHeader)
    private val descriptionInput: TextView = view.findViewById(R.id.taskContent)
    private val taskTime: TextView = view.findViewById(R.id.taskTime)

    fun bind(taskItem: TaskDataItemDTO, listener: OnTaskItemClickListener?) {
        taskTime.text = taskItem.dueDate.toString()
        headerTaskInput.text = taskItem.headerText
        descriptionInput.text = taskItem.taskDescriptionText

        view.setOnClickListener {
            listener?.onTaskItemClick(view = view, taskItem)
        }

    }




}
