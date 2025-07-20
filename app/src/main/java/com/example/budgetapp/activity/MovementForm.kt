package com.example.budgetapp.activity

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgetapp.R
import com.example.budgetapp.databinding.ActivityMovementFormBinding
import android.util.Log
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.adapters.MovementAdapter
import com.example.budgetapp.data.database_new.DbConnect
import com.example.budgetapp.data.database_new.entities.EntityMovement

class MovementForm : AppCompatActivity() {

    private var TAG: String = "MovementForm"
    private lateinit var binding: ActivityMovementFormBinding
    private var sign: String = "-"
    private var database = DbConnect.getDB(this)
    private var movementDao = database.daoMovements()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMovementFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            goBack()
        }

        binding.btnSave.setOnClickListener {
            getValues()
        }

        binding.toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnIncome -> {
                        sign = "+"
                    }
                    R.id.btnExpense -> {
                        sign = "-"
                    }
                }
                updateButtonToggle()
            }
        }

        updateButtonToggle()
    }

    private fun getValues() {
        val amount = binding.editTextAmount.text.toString().toDoubleOrNull() ?: 0.0
        val description = binding.editTextDetail.text.toString()
        val category = binding.editTextCategory.text.toString()
        val date = binding.editTextDate.text.toString()

        movementDao.insertMovement(EntityMovement(
            0,
            amount,
            sign,
            description,
            category,
            date
        ))
        goBack()
    }

    private fun updateButtonToggle() {
        if (sign == "+") {
            binding.btnIncome.setBackgroundColor("#0BFF96".toColorInt())
            binding.btnExpense.setBackgroundColor("#7F8CAA".toColorInt())
        } else {
            binding.btnIncome.setBackgroundColor("#7F8CAA".toColorInt())
            binding.btnExpense.setBackgroundColor("#FF0B55".toColorInt())
        }
    }

    private fun goBack() {
        finish()
    }
}