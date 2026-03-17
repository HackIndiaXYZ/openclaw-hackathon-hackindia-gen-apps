package com.project.basicexpensetrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.project.basicexpensetrackerapp.ViewModel.ExpenseViewModelFactory
import com.project.basicexpensetrackerapp.ui.theme.BasicExpenseTrackerAppTheme
import com.project.expensetrackerapp.ExpenseRoomDB.DataBase.ExpenseDataBase
import com.project.expensetrackerapp.ExpenseRoomDB.MainApplication.MainApplication
import com.project.expensetrackerapp.ExpenseTrackerScreen
import com.project.expensetrackerapp.ViewModel.ExpenseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val ExpenseApp = application as MainApplication
            val ExpenseDao = ExpenseApp.database.getExpenseDao()
            val viewModel: ExpenseViewModel = viewModel(
                factory = ExpenseViewModelFactory(dao = ExpenseDao)
            )
            BasicExpenseTrackerAppTheme {
                ExpenseTrackerScreen(viewModel = viewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseTrackerApp(){
//    val dao = MainApplication.amountDb.getAmountDao()
//    val viewModel: ExpenseViewModel = viewModel(
//        factory = ExpenseViewModelFactory(dao)
//    )
//    ExpenseTrackerScreen(viewModel = viewModel)
}