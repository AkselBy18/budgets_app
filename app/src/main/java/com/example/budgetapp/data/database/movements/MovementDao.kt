package com.example.budgetapp.data.database.movements

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovementDao {
    @Query("SELECT * FROM movements")
    fun getAll(): Flow<List<MovementEntity>>

    @Insert
    suspend fun insert(movement: MovementEntity)

    @Update
    suspend fun update(movement: MovementEntity)

    @Delete
    suspend fun delete(movement: MovementEntity)
}