package com.project.expensetrackerapp.ExpenseRoomDB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Expense_Table")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val amount: Int,
    val category: String,
    val createdAt: Long = System.currentTimeMillis()
)
