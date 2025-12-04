package com.example.plantapp.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
//import java.time.LocalDate
//import java.time.temporal.ChronoUnit
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

@Composable
fun PlantDetailScreen(navController: NavController,plantId: Int?) {
    val viewModel: PlantViewModel = viewModel()
    val plant = plantId?.let { viewModel.getPlantById(it) }

    if (plant == null) {
        Text("Plant not found", modifier = Modifier.fillMaxSize().wrapContentSize())
        return
    }

    val date = LocalDate.now()
    val nextWatering = plant.lastWatered.plusDays(plant.wateringInterval.toLong())
    val daysUntilNext = ChronoUnit.DAYS.between(date, nextWatering)
    val wateringStatus = if (daysUntilNext < 0) "Overdue by ${-daysUntilNext} days" else "$daysUntilNext days"

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = plant.imageResId),
            contentDescription = plant.name,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Name ${plant.name}")
        Text("Species: ${plant.species}")
        Text("Last Watered: ${plant.lastWatered}")
        Text("Days Until Next Watering: ${wateringStatus}")
        Text("Notes: ${plant.notes}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {navController.navigate("editPlant/${plant.id}")}) {
            Text("Edit Plant")
        }
        Button(onClick = {navController.popBackStack()}) {
            Text("Back")
        }
    }
}