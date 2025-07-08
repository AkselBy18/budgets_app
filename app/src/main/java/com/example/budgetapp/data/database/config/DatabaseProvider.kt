package com.example.budgetapp.data.database.config

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDataBase? = null;

    fun getDataBase(context: Context): AppDataBase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "budget_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}