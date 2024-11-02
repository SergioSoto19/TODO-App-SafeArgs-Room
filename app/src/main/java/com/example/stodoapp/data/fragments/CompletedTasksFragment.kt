package com.example.stodoapp.data.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stodoapp.R
import com.example.stodoapp.data.adapters.TaskAdapter
import com.example.stodoapp.data.models.Task
import com.example.stodoapp.data.viewModel.TaskViewModel


class CompletedTasksFragment : Fragment() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_completed_tasks, container, false)


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCompletedTasks)
        recyclerView.layoutManager = LinearLayoutManager(context)


        taskAdapter = TaskAdapter(
            onTaskClick = { task -> navigateToTaskDetails(task) },
            onTaskChecked = { task -> taskViewModel.updateTaskCompletion(task) },
            onTaskDelete = { task -> taskViewModel.deleteTask(task) }
        )
        recyclerView.adapter = taskAdapter


        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)

        taskViewModel.completedTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.submitList(tasks)
        }

        return view
    }


    private fun navigateToTaskDetails(task: Task) {
        val action = CompletedTasksFragmentDirections.actionCompletedTasksToTaskDetails(task)
        findNavController().navigate(action)
    }
}