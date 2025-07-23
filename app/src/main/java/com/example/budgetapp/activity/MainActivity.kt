package com.example.budgetapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
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
    private var movements: List<EntityMovement> = emptyList()

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
            movements = dao.getAllMovements()

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

    private fun showDeleteDialog(movement: EntityMovement) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)

        btnCancel?.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm?.setOnClickListener {
            dialog.dismiss()
            Thread {
                dao.deleteMovement(movement)
                runOnUiThread {
                    loadMovements()
                }
            }.start()
        }

        dialog.show()
    }

    private fun loadMovements() {
        Thread {
            movements = dao.getAllMovements()
            this.setBalance()
            runOnUiThread {
                adapter = MovementAdapter(
                    movements = movements,
                    onEditClick = { movement ->
                        val editIntent = Intent(this, MovementForm::class.java)
                        editIntent.putExtra("movementId", movement.id)
                        startActivity(editIntent)
                    },
                    onDeleteClick = { movement ->
                        showDeleteDialog(movement)
                    }
                )
                recyclerView.adapter = adapter
            }
        }.start()
    }

    private fun setBalance() {
        Thread {
            val totalBalance = movements.sumOf {
                if (it.sign == "+") it.amount else -it.amount
            }
            runOnUiThread {
                binding.textViewBalance.text = "$totalBalance"
                if (totalBalance < 0) {
                    binding.textViewBalance.setTextColor("#FF0B55".toColorInt())
                } else {
                    binding.textViewBalance.setTextColor("#0BFF96".toColorInt())
                }
            }
        }.start()
    }

    private fun showAddMovement() {
        val movementForm = Intent(this, MovementForm::class.java)
        startActivity(movementForm)
    }
}