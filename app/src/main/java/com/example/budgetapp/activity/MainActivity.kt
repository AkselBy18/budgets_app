package com.example.budgetapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.R
import com.example.budgetapp.adapters.MovementAdapter
import com.example.budgetapp.data.database_new.DbConnect
import com.example.budgetapp.data.database_new.entities.EntityMovement
import com.example.budgetapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovementAdapter
    private val dao by lazy { DbConnect.getDB(this).daoMovements() }
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            showAddMovement()
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        Thread {
            val movements = dao.getAllMovements()

            Log.d(TAG, "Number of movements: ${movements.size}")

            if (movements.isEmpty()) {
                dao.insertMovement(EntityMovement(
                    0,
                    100.0,
                    "+",
                    "Initial deposit",
                    "Savings",
                    "17-07-2025"
                ))
            }

        }.start()
    }

    override fun onResume() {
        super.onResume()
        loadMovements()
    }

    private fun loadMovements() {
        Thread {
            val movements = dao.getAllMovements()
            runOnUiThread {
                adapter = MovementAdapter(movements)
                recyclerView.adapter = adapter
            }
        }.start()
    }

    private fun showAddMovement() {
        val movementForm = Intent(this, MovementForm::class.java)
        startActivity(movementForm)
    }
}