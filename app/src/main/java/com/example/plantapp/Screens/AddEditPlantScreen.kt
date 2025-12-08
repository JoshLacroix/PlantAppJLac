package com.example.plantapp.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantapp.Plant
import com.example.plantapp.PlantViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun AddEditPlantScreen(navController: NavController,plantId: Int?) {
    val viewModel: PlantViewModel = viewModel()
    val existingPlant = plantId?.let { viewModel.getPlantById(it) }
    var name by remember { mutableStateOf(existingPlant?.name ?: "") }
    var species by remember { mutableStateOf(existingPlant?.species ?: "") }
    var imageResId by remember { mutableStateOf(existingPlant?.imageResId?.toString() ?: "") }
    var lastWatered by remember { mutableStateOf(existingPlant?.lastWatered ?: LocalDate.now()) }
    var wateringInterval by remember { mutableStateOf(existingPlant?.wateringInterval?.toString() ?: "7") }
    var notes by remember { mutableStateOf(existingPlant?.notes ?: "") }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = lastWatered.toEpochDay() * 86400000L
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(if (existingPlant != null) "Edit Plant" else "Add Plant")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(
            value = species,
            onValueChange = { species = it },
            label = { Text("Species") })
        OutlinedTextField(
            value = imageResId,
            onValueChange = { imageResId = it },
            label = { Text("Image Resource ID") })
        OutlinedTextField(
            value = lastWatered.format(DateTimeFormatter.ISO_LOCAL_DATE),
            onValueChange = {},
            readOnly = true,
            label = { Text("Last Watered") },
            modifier = Modifier.clickable { showDatePicker = true })
        OutlinedTextField(
            value = wateringInterval,
            onValueChange = { wateringInterval = it },
            label = { Text("Watering Interval (Days)") })
        OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Notes") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            try {
                val interval = wateringInterval.toInt()
                val imageId = imageResId.toIntOrNull() ?: com.example.plantapp.R.drawable.ic_launcher_background
                val plant = Plant(
                    id = existingPlant?.id ?: 0,
                    name = name,
                    species = species,
                    imageResId = imageId,
                    lastWatered = lastWatered,
                    wateringInterval = interval,
                    notes = notes
                )
                if (existingPlant != null) {
                    viewModel.updatePlant(plant)
                } else {
                    viewModel.addPlant(plant)
                }
                navController.popBackStack()
            } catch (e: Exception) {
            }
        }) { Text("Save") }
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        lastWatered = LocalDate.ofEpochDay(it / 86400000L)
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}