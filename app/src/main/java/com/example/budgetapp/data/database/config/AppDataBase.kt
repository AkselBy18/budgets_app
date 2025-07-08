package com.example.budgetapp.data.database.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budgetapp.data.database.movements.MovementDao
import com.example.budgetapp.data.database.movements.MovementEntity

@Database(entities = [MovementEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun movementDao(): MovementDao;
}