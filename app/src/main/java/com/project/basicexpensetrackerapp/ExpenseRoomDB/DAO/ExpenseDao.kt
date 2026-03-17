package com.project.expensetrackerapp.ExpenseRoomDB.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.expensetrackerapp.ExpenseRoomDB.Entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert
    suspend fun InsertExpense(expenseEntity: ExpenseEntity)

    @Query("SELECT * FROM Expense_Table ORDER BY createdAt DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Delete
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)
}