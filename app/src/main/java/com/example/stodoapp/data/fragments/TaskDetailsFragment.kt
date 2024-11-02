package com.example.stodoapp.data.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.stodoapp.R


class TaskDetailsFragment : Fragment() {

    private val args: TaskDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_details, container, false)

        val task = args.task
        view.findViewById<TextView>(R.id.textViewTaskName).text = task.title
        view.findViewById<TextView>(R.id.textViewTaskStatus).text =
            if (task.isCompleted) "Completada" else "Pendiente"
        view.findViewById<TextView>(R.id.textViewTaskDescription).text = task.description

        return view
    }
}