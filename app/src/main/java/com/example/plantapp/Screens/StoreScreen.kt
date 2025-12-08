package com.example.plantapp.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantapp.StoreViewModel
import java.text.NumberFormat
import java.util.*

@Composable
fun StoreScreen(navController: NavController) {
    val viewModel: StoreViewModel = viewModel()
    val items by viewModel.items.collectAsState()
    val quantities by viewModel.quantities.collectAsState()
    val total = viewModel.total
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.CANADA)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Store", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        items.forEach { item ->
            val qty = quantities[item.name]?: 0
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("${item.name} - ${currencyFormatter.format(item.price)}")
                    Text("Quantity: $qty")
                }
                Button(onClick = { viewModel.addItem(item.name) }) {
                    Text("Add")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Total: ${currencyFormatter.format(total)}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {navController.popBackStack()}) {
            Text("Back")
        }
    }

}