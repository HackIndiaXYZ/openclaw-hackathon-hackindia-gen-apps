package com.project.expensetrackerapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.expensetrackerapp.ExpenseRoomDB.DAO.ExpenseDao
import com.project.expensetrackerapp.ExpenseRoomDB.Entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExpenseViewModel(private val dao: ExpenseDao): ViewModel() {

    val allExpenses: Flow<List<ExpenseEntity>> = dao.getAllExpenses()

    fun insertExpense(name: String, amount: Int, category: String) {
        viewModelScope.launch {
            val expense = ExpenseEntity(id = 0, name = name, amount = amount, category = category)
            dao.InsertExpense(expense)
        }
    }


    fun delteExpense(expense: ExpenseEntity){
        viewModelScope.launch {
            dao.deleteExpense(expense)
        }
    }
}