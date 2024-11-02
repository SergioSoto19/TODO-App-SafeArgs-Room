package com.example.stodoapp.data.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.stodoapp.R
import com.example.stodoapp.data.adapters.TaskAdapter
import com.example.stodoapp.data.models.Task
import com.example.stodoapp.data.viewModel.TaskViewModel


class AddTaskFragment : Fragment() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var completedButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)

        titleEditText = view.findViewById(R.id.editTextTask)
        descriptionEditText = view.findViewById(R.id.editTextDescription)
        addButton = view.findViewById(R.id.buttonAddTask)
        completedButton = view.findViewById(R.id.buttonCompletedTasks)
        recyclerView = view.findViewById(R.id.recyclerViewTasks)

        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)

        taskAdapter = TaskAdapter(
            onTaskClick = { task -> navigateToTaskDetails(task) },
            onTaskChecked = { task -> taskViewModel.updateTaskCompletion(task) },
            onTaskDelete = { task -> taskViewModel.deleteTask(task) }
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = taskAdapter


        taskViewModel.pendingTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.submitList(tasks)
        }

        addButton.setOnClickListener { addTask() }
        completedButton.setOnClickListener { navigateToCompletedTasks() }

        return view
    }
    private fun addTask() {
        val title = titleEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()

        if (title.isNotEmpty() && description.isNotEmpty()) {
            val newTask = Task(title = title, description = description)
            taskViewModel.addTask(newTask)
            titleEditText.text.clear()
            descriptionEditText.text.clear()
            Toast.makeText(requireContext(), "Tarea añadida.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToTaskDetails(task: Task) {
        val action = AddTaskFragmentDirections.actionAddTaskToTaskDetails(task)
        findNavController().navigate(action)
    }

    private fun navigateToCompletedTasks() {
        val action = AddTaskFragmentDirections.actionAddTaskToCompletedTasks()
        findNavController().navigate(action)
    }
}