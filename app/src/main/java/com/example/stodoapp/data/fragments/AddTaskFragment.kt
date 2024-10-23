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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)


        titleEditText = view.findViewById(R.id.editTextTask)
        descriptionEditText = view.findViewById(R.id.editTextDescription)
        addButton = view.findViewById(R.id.buttonAddTask)


        recyclerView = view.findViewById(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(context)


        taskAdapter = TaskAdapter(
            onTaskClick = { task ->  },
            onTaskChecked = { task -> taskViewModel.updateTaskCompletion(task) }
        )
        recyclerView.adapter = taskAdapter

        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)


        taskViewModel.pendingTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.submitList(tasks)
        }

        addButton.setOnClickListener { addTask() }


        val buttonCompletedTasks: Button = view.findViewById(R.id.buttonCompletedTasks)
        buttonCompletedTasks.setOnClickListener {
            findNavController().navigate(R.id.fragment_completed_tasks)
        }

        return view
    }

    private fun addTask() {
        val title = titleEditText.text.toString()
        val description = descriptionEditText.text.toString()

        if (title.isNotEmpty() && description.isNotEmpty()) {
            val newTask = Task(id = generateTaskId(), title = title, description = description)
            taskViewModel.addTask(newTask)
            titleEditText.text.clear()
            descriptionEditText.text.clear()
            Toast.makeText(requireContext(), "Tarea añadida.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateTaskId(): Int {
        return (System.currentTimeMillis() % 1000).toInt()
    }
}