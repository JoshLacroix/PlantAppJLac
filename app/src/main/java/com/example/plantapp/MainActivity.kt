package com.example.plantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plantapp.Screens.AddEditPlantScreen
import com.example.plantapp.Screens.PlantDetailScreen
import com.example.plantapp.Screens.PlantsScreen
import com.example.plantapp.Screens.StoreScreen
import com.example.plantapp.ui.theme.PlantAppTheme
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        enableEdgeToEdge()
        setContent {
            PlantAppTheme {
                AppNavigation(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Plant App") },
                actions = {
                    Button(onClick = { navController.navigate("store") }) {
                        Text("Store")
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "plants", modifier = modifier.padding(innerPadding)) {
            composable("plants") {
                PlantsScreen(navController)
            }
            composable("plantDetail/{plantId}") { backStackEntry ->
                val plantId = backStackEntry.arguments?.getString("plantId")?.toIntOrNull()
                PlantDetailScreen(navController, plantId )
            }
            composable("addPlant") {
                AddEditPlantScreen(navController, null)
            }
            composable("editPlant/{plantId}") { backStackEntry ->
                val plantId = backStackEntry.arguments?.getString("plantId")?.toIntOrNull()
                AddEditPlantScreen(navController, plantId)
            }
            composable("store") {
                StoreScreen(navController)
            }
        }
    }

}