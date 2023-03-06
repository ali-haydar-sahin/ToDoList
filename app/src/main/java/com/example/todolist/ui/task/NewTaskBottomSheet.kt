package com.example.todolist.ui.task

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import com.example.todolist.data.task.TaskDataItemDTO
import com.example.todolist.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*


class NewTaskBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {

    private var taskData: TaskDataItemDTO? = null
    private var _binding: FragmentNewTaskSheetBinding? = null

    private val calendar: Calendar = Calendar.getInstance()
    private val dateTimeFormat = SimpleDateFormat("y/M/d hh:mm", Locale.getDefault())

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentNewTaskSheetBinding.inflate(inflater, container , false)

        binding.taskTitleInput.addTextChangedListener { text ->
            if (binding.taskTitleInput.text!!.length >= 1) {
                binding.saveButton.isEnabled = true
                binding.saveButton.setBackgroundColor(Color.parseColor("#d81b60"))
            } else {
                binding.saveButton.isEnabled = false
                binding.saveButton.setBackgroundColor(Color.parseColor("#ffcdd2"))
            }
        }

        val dueTimePicker = binding.dueTimePicker

        /*val timePicker = ImageView(context).apply {

            layoutParams = ViewGroup.LayoutParams (
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        constraintLayout = ConstraintLayout(requireContext()).apply {

            addView(timePicker)
        }*/

        dueTimePicker.setText(dateTimeFormat.format(calendar.time))




        dueTimePicker.setOnClickListener{
            showDatePicker()
        }
        dueTimePicker.onFocusChangeListener = OnFocusChangeListener { view, focus ->
            if (focus) {
                showDatePicker()
            }
        }

        binding.saveButton.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { bundle ->
            bundle.getParcelable<TaskDataItemDTO>(ARG_TASK)?.let {
                this.taskData = it
                binding.taskTitleInput.setText(it.headerText)
                binding.taskDescriptionInput.setText(it.taskDescriptionText)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }




    companion object {
        const val ARG_TASK = "arg_task"
        const val RESULT_LISTENER_KEY = "taskFragmentResultListener"
        const val RESULT_NEW_TASK_KEY = "newTaskKey"
        const val RESULT_UPDATE_TASK_KEY = "updateTask"

    }

    private fun showDatePicker() {
        val materialDatePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Saat seçin")
            .setNegativeButtonText("iptal")
            .setPositiveButtonText("Tamam")
            .build()
        materialDatePicker.addOnPositiveButtonClickListener { date ->
            calendar.timeInMillis = date
            Log.d(NewTaskBottomSheet::class.simpleName, "$date")
            Log.d(NewTaskBottomSheet::class.simpleName, "${calendar.time}")
            binding.dueTimePicker.setText(dateTimeFormat.format(calendar.time))
            showTimePicker()
        }

        materialDatePicker.show(parentFragmentManager, null)

    }

    private fun showTimePicker() {
        val materialTimePicker = MaterialTimePicker.Builder()
            .setTitleText("Saat seçin")
            .setNegativeButtonText("iptal")
            .setPositiveButtonText("Tamam")
            .build()
        materialTimePicker.addOnPositiveButtonClickListener { date ->
            calendar.set(Calendar.HOUR, materialTimePicker.hour)
            calendar.set(Calendar.MINUTE, materialTimePicker.minute)
            Log.d(NewTaskBottomSheet::class.simpleName, "$date")
            Log.d(NewTaskBottomSheet::class.simpleName, "${calendar.time}")
            binding.dueTimePicker.setText(dateTimeFormat.format(calendar.time))
        }

        materialTimePicker.show(parentFragmentManager, null)

    }

    override fun onClick(v: View?) {
        val taskTitle = (binding.taskTitleInput.text ?: "").toString()
        val descriptionInput = (binding.taskDescriptionInput.text ?: "").toString()

        val newTask = TaskDataItemDTO(System.currentTimeMillis(), taskTitle, descriptionInput, calendar.time)
        if (taskData != null) {
            newTask.id = taskData!!.id
            taskData!!.dueDate == newTask.dueDate
            parentFragmentManager.setFragmentResult(RESULT_LISTENER_KEY, bundleOf(RESULT_UPDATE_TASK_KEY to newTask))
        } else {
            parentFragmentManager.setFragmentResult(RESULT_LISTENER_KEY, bundleOf(RESULT_NEW_TASK_KEY to newTask))
        }
        dismiss()

    }


}

