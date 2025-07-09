package com.example.budgetapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgetapp.data.database_new.DbConnect
import com.example.budgetapp.data.database_new.entities.EntityMovement

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val database = DbConnect.getDB(this)
        val movements = database.daoMovements().getAllMovements()

        if (movements.isEmpty()) {
            database.daoMovements().insertMovement(EntityMovement(
                0,
                100.0,
                "+",
                "Initial deposit",
                "Savings",
                java.util.Date().toString()
            ))
        }
    }
}