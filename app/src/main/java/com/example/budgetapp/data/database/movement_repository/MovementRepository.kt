package com.example.budgetapp.data.database.movement_repository

import com.example.budgetapp.data.database.movements.MovementDao
import com.example.budgetapp.data.database.movements.MovementEntity
import kotlinx.coroutines.flow.Flow

class MovementRepository(private val dao: MovementDao) {

    val allMovements: Flow<List<MovementEntity>> = dao.getAll()

    suspend fun insert(movement: MovementEntity) = dao.insert(movement)
    suspend fun update(movement: MovementEntity) = dao.update(movement)
    suspend fun delete(movement: MovementEntity) = dao.delete(movement)
}