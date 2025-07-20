package com.example.budgetapp.data.database_new.query

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.budgetapp.data.database_new.entities.EntityMovement

@Dao
interface DaoMovements {

    @Insert fun insertMovement(movement: EntityMovement)
    @Insert fun insertMovement(movement: List<EntityMovement>)

    @Update fun updateMovement(movement: EntityMovement)
    @Update fun updateMovement(movement: List<EntityMovement>)

    @Query("SELECT * FROM movements") fun getAllMovements(): List<EntityMovement>
    @Query("SELECT * FROM movements WHERE id = :id") fun getMovementById(id: Int): EntityMovement?

    @Delete fun deleteMovement(movement: EntityMovement)
    @Delete fun deleteMovement(movement: List<EntityMovement>)
}
