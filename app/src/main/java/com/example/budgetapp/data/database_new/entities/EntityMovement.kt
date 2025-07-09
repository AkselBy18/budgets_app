package com.example.budgetapp.data.database_new.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "movements")
class EntityMovement(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var amount: Double = 0.0,
    var sign: String = "",
    var detail: String = "",

    @ColumnInfo(name = "category")
    var categoryName: String = "",
    var date: String = ""
) { }