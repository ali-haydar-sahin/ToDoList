package com.example.todolist.ui.task

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.task.TaskDataItemDTO
import com.example.todolist.databinding.FragmentTaskBinding
import com.example.todolist.model.TaskViewModel
import com.example.todolist.util.dp

class TaskFragment : Fragment(), View.OnClickListener, OnTaskItemClickListener {

    private var _binding: FragmentTaskBinding? = null

    private val binding get() = _binding!!

    private lateinit var swipeHelper: ItemTouchHelper

    private val viewModel: TaskViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)

        setObservers()

        val taskAdapter = TaskAdapter()
        taskAdapter.setListener(this)
        childFragmentManager.setFragmentResultListener(NewTaskBottomSheet.RESULT_LISTENER_KEY, this) { requestKey, bundle ->
            if (bundle.containsKey(NewTaskBottomSheet.RESULT_NEW_TASK_KEY)) {
                bundle.getParcelable<TaskDataItemDTO>(NewTaskBottomSheet.RESULT_NEW_TASK_KEY)?.let { data ->
                    viewModel.addItems(data)
                    binding.taskRecyclerView.smoothScrollToPosition(0)
                }
            } else if (bundle.containsKey(NewTaskBottomSheet.RESULT_UPDATE_TASK_KEY)) {
                bundle.getParcelable<TaskDataItemDTO>(NewTaskBottomSheet.RESULT_UPDATE_TASK_KEY)?.let {
                    viewModel.updateItems(it)
                }
            }
        }




        binding.taskRecyclerView.adapter = taskAdapter



        binding.floatingActionButton.setOnClickListener(this)

        swipeHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.LEFT ,
            ItemTouchHelper.LEFT
        ) {


            val displayMetrics: DisplayMetrics = resources.displayMetrics
            val width = (displayMetrics.widthPixels / displayMetrics.density).toInt().dp



            val deleteColor = resources.getColor(R.color.secondaryColor)

            val background = ColorDrawable()


            val deleteIcon = resources.getDrawable(R.drawable.delete_icon, null)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = true

            override fun onMoved(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                fromPos: Int,
                target: RecyclerView.ViewHolder,
                toPos: Int,
                x: Int,
                y: Int
            ) {


                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.d(TaskFragment::class.java.name, "$direction")

               when (direction) {

                    ItemTouchHelper.LEFT -> {
                        val item = taskAdapter.getItem(viewHolder.adapterPosition)
                        viewModel.deleteItems(item.id)
                    }

                    ItemTouchHelper.RIGHT -> {
                        /*(binding.taskRecyclerView.adapter as TaskAdapter).updateItem(
                            NewTaskBottomSheet.show(childFragmentManager, NewTaskBottomSheet.TAG))*/
                        binding.taskRecyclerView.adapter?.notifyItemChanged(viewHolder.adapterPosition)
                    }
                }

            }

            @SuppressLint("ResourceAsColor")
            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean,
            ) {


                Log.d(TaskFragment::class.java.name, "dx, $dX dy $dY")

                val itemView = viewHolder.itemView

                background.color = deleteColor
                background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                background.draw(canvas)

                background.setBounds(itemView.left + dX.toInt(), itemView.top, itemView.left, itemView.bottom)
                background.draw(canvas)


                val textMargin = (16f).toInt()

                deleteIcon.bounds = Rect(
                    width - textMargin - deleteIcon.intrinsicWidth,
                    viewHolder.itemView.top,
                    width - textMargin,
                    viewHolder.itemView.top + deleteIcon.intrinsicHeight
                )
                deleteIcon.draw(canvas)
                super.onChildDraw(
                    canvas,
                    recyclerView,
                    viewHolder,
                    dX / 2,
                    dY,
                    actionState,
                    isCurrentlyActive
                )


            }

        })


        swipeHelper.attachToRecyclerView(binding.taskRecyclerView)

        return binding.root
    }



    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }




    override fun onClick(v: View?) {
        openTaskFormBottomSheet()
    }

    override fun onTaskItemClick(view: View, listItem: TaskDataItemDTO) {

        openTaskFormBottomSheet(bundleOf(NewTaskBottomSheet.ARG_TASK to listItem))

    }

    private fun openTaskFormBottomSheet(bundle: Bundle? = null) {
        val taskBottomSheet = NewTaskBottomSheet()
        bundle?.let {
            taskBottomSheet.arguments = it
        }
        taskBottomSheet.show(childFragmentManager, null)
    }

    private fun setObservers() {
        viewModel.taskDataItemDTO.observe(viewLifecycleOwner){ newData ->
            binding.taskRecyclerView.apply {
                (adapter as TaskAdapter).updateData(newData)
            }
        }
    }
}



