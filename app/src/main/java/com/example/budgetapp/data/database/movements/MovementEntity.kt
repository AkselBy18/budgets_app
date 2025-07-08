package com.example.budgetapp.data.database.movements

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "movements")
class MovementEntity {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0;
    val amount: Double = 0.0;
    val sign: String = "";
    val detail: String = "";
    val category: String = "";
    val date: Date = Date();
}