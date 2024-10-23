package com.example.stodoapp.data.models

import java.io.Serializable


data class Task(
    val id: Int,
    val title: String,
    val description: String,
    var isCompleted: Boolean = false
) : Serializable