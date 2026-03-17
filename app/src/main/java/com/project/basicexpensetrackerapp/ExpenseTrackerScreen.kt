package com.project.expensetrackerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.basicexpensetrackerapp.R
import com.project.expensetrackerapp.ExpenseRoomDB.Entity.ExpenseEntity
import com.project.expensetrackerapp.ViewModel.ExpenseViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ExpenseTrackerScreen(viewModel: ExpenseViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf("") }
    val expenses by viewModel.allExpenses.collectAsState(initial = emptyList())

    val totalIncome = expenses.filter { it.category == "Income" }.sumOf { it.amount }
    val totalExpense = expenses.filter { it.category == "Expense" }.sumOf { it.amount }
    val totalSaved = totalIncome - totalExpense

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        // 🔥 Summary Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryCard("Income", totalIncome, Color(0xFF2E7D32))
            SummaryCard("Expense", totalExpense, Color(0xFFC62828))
            SummaryCard("Saved", totalSaved, Color(0xFF1565C0))
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🔥 Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = {
                    selectedType = "Income"
                    showDialog = true
                }
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_income),
                    contentDescription = "Income"
                )
            }

            IconButton(
                onClick = {
                    selectedType = "Expense"
                    showDialog = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_expense),
                    contentDescription = "Expense"
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🔥 Expense List
        LazyColumn {
            items(expenses) { expense ->
                ExpenseCard(expense = expense, viewModel = viewModel)
            }
        }
    }
    if (showDialog) {
        AddExpenseDialog(
            type = selectedType,
            onDismiss = { showDialog = false },
            onAdd = { title, amount ->
                viewModel.insertExpense(title, amount, selectedType)
                showDialog = false
            }
        )
    }
}
@Composable
fun ExpenseCard(viewModel: ExpenseViewModel, expense: ExpenseEntity) {

    val formattedDate = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
        .format(Date(expense.createdAt))

    val color = if (expense.category == "Income")
        Color(0xFF2E7D32) else Color(0xFFC62828)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        onClick = {
            viewModel.delteExpense(expense)
        }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = expense.category,
                color = color,
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = "₹${expense.amount}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun AddExpenseDialog(
    type: String,
    onDismiss: () -> Unit,
    onAdd: (String, Int) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if (title.isNotEmpty() && amount.isNotEmpty()) {
                    onAdd(title, amount.toInt())
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Add $type") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") }
                )
            }
        }
    )
}

@Composable
fun SummaryCard(title: String, amount: Int, color: Color) {
    Card(
        modifier = Modifier
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, color = color)
            Text(
                text = "₹$amount",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}