package com.example.plantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plantapp.Screens.AddEditPlantScreen
import com.example.plantapp.Screens.PlantDetailScreen
import com.example.plantapp.Screens.PlantsScreen
import com.example.plantapp.ui.theme.PlantAppTheme
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        enableEdgeToEdge()
        setContent {
            PlantAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "plants", modifier = modifier) {
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
    }
}