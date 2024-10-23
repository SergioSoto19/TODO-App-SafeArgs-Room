package com.example.stodoapp.data.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stodoapp.R


class TaskDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_details, container, false)

        val taskName = arguments?.getString("taskName")
        val isCompleted = arguments?.getBoolean("isCompleted", false) ?: false
        val taskDescription = arguments?.getString("taskDescription")

        view.findViewById<TextView>(R.id.textViewTaskName).text = taskName
        view.findViewById<TextView>(R.id.textViewTaskStatus).text =
            if (isCompleted) "Completada" else "Pendiente"
        view.findViewById<TextView>(R.id.textViewTaskDescription).text = taskDescription

        return view
    }
}