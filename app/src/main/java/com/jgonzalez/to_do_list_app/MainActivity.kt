package com.jgonzalez.to_do_list_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jgonzalez.to_do_list_app.adapter.TaskAdapter
import com.jgonzalez.to_do_list_app.databinding.ActivityMainBinding
import com.jgonzalez.to_do_list_app.model.Task
import com.jgonzalez.to_do_list_app.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TaskAdapter(
            onEditTask = { task ->
                Intent(this, TarefasAdd::class.java).also {
                    startActivity(it)
                }
            },
            onDeleteTask = { task ->
                deleteTask(task)

            }
        )

        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = adapter

        taskViewModel.allTasks.observe(this, Observer { tasks ->
            tasks?.let { adapter.setTask(it) }

        })

        binding.addFAB.setOnClickListener {
            Intent(this, TarefasAdd::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun deleteTask(task: Task) {
        taskViewModel.delete(task)
    }
}