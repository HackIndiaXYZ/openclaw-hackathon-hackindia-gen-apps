package com.project.expensetrackerapp.ExpenseRoomDB.MainApplication

import android.app.Application
import androidx.room.Room
import com.project.expensetrackerapp.ExpenseRoomDB.DataBase.ExpenseDataBase

class MainApplication: Application() {
    val database: ExpenseDataBase by lazy {
        Room.databaseBuilder(
            this,
            ExpenseDataBase::class.java,
            ExpenseDataBase.DBname
        ).build()
    }
}