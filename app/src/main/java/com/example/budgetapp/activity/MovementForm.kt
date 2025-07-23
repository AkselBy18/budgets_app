package com.example.budgetapp.activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetapp.R
import com.example.budgetapp.databinding.ActivityMovementFormBinding
import android.util.Log
import androidx.core.graphics.toColorInt
import com.example.budgetapp.data.database_new.DbConnect
import com.example.budgetapp.data.database_new.entities.EntityMovement

class MovementForm : AppCompatActivity() {

    private var TAG: String = "MovementForm"
    private lateinit var binding: ActivityMovementFormBinding
    private var sign: String = "-"
    private var database = DbConnect.getDB(this)
    private var movementDao = database.daoMovements()
    private var isUpdate: Boolean = false
    private var idMovement: Int = -1


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

        Thread {
            val movementId = intent.getIntExtra("movementId", -1)
            if (movementId != -1) {
                idMovement = movementId
                val movement = movementDao.getMovementById(movementId)
                Log.d(TAG, "Movement: $movement")
                if (movement !== null) {
                    isUpdate = true
                    runOnUiThread {
                        binding.editTextAmount.setText(movement.amount.toString())
                        binding.editTextCategory.setText(movement.categoryName)
                        binding.editTextDetail.setText(movement.detail)
                        binding.editTextDate.setText(movement.date)
                        sign = movement.sign
                        updateButtonToggle()
                    }
                }
            }
        }.start()
        updateButtonToggle()
    }

    private fun getValues() {
        val amount = binding.editTextAmount.text.toString().toDoubleOrNull() ?: 0.0
        val description = binding.editTextDetail.text.toString()
        val category = binding.editTextCategory.text.toString()
        val date = binding.editTextDate.text.toString()

        if (isUpdate) {
            movementDao.updateMovement(
                EntityMovement(
                    idMovement,
                    amount,
                    sign,
                    description,
                    category,
                    date
                )
            )
        } else {
            movementDao.insertMovement(
                EntityMovement(
                    0,
                    amount,
                    sign,
                    description,
                    category,
                    date
                )
            )
        }
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