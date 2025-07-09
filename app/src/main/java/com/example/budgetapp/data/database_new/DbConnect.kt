package com.example.budgetapp.data.database_new

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.budgetapp.data.database_new.entities.EntityMovement
import com.example.budgetapp.data.database_new.query.DaoMovements

@Database(entities = [EntityMovement::class], version = 1, exportSchema = false)
abstract class DbConnect:RoomDatabase() {
    abstract fun daoMovements(): DaoMovements
    companion object {
        @Volatile
        private var INSTANCE: DbConnect? = null

        fun getDB(context: Context): DbConnect {
            return INSTANCE ?: synchronized(DbConnect::class.java) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DbConnect::class.java,
                    "budget_database_new.db"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}