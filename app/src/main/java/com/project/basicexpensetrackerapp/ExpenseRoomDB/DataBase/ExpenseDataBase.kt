package com.project.expensetrackerapp.ExpenseRoomDB.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.expensetrackerapp.ExpenseRoomDB.DAO.ExpenseDao
import com.project.expensetrackerapp.ExpenseRoomDB.Entity.ExpenseEntity

@Database(entities = [ExpenseEntity::class], version = 1, exportSchema = true)
abstract class ExpenseDataBase : RoomDatabase(){
    companion object{
        const val DBname = "ExpenseDB"
    }

    abstract fun getExpenseDao():ExpenseDao
}