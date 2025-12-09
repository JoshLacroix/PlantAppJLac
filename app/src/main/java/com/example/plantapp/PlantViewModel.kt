package com.example.plantapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantapp.data.AppDatabase
import com.example.plantapp.data.PlantEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
//import java.time.LocalDate
import org.threeten.bp.LocalDate

data class Plant(
    val id: Int,
    val name: String,
    val species: String,
    val imageResId: Int,
    val lastWatered: LocalDate,
    val wateringInterval: Int,
    val notes: String
)
class PlantViewModel(application: Application) : AndroidViewModel(application) {
    private val plantDao = AppDatabase.getDatabase(application).plantDao()
    val plants: Flow<List<Plant>> = plantDao.getAllPlants().map { entities ->
        entities.map { entity ->
            Plant(
                id = entity.id,
                name = entity.name,
                species = entity.name,
                imageResId = entity.imageResId,
                lastWatered = entity.lastWatered,
                wateringInterval = entity.wateringInterval,
                notes = entity.notes
            )
        }
    }



    init {
        viewModelScope.launch {
            val existing = plantDao.getAllPlants().firstOrNull()
            if (existing.isNullOrEmpty()) {
                plantDao.insertPlant(PlantEntity(
                    name = "Basil",
                    species = "Ocimum basilicum",
                    imageResId = R.drawable.ic_launcher_foreground,
                    lastWatered = LocalDate.now(),
                    wateringInterval = 7,
                    notes = "Needs sunlight."
                ))
                plantDao.insertPlant(PlantEntity(
                    name = "Rose",
                    species = "Rosa",
                    imageResId = R.drawable.ic_launcher_foreground,
                    lastWatered = LocalDate.now().minusDays(5),
                    wateringInterval = 3,
                    notes = "Water roots only."
                ))
            }
        }
    }

    fun getPlantById(id: Int): Flow<Plant?> {
        return plantDao.getAllPlants().map { entities ->
            entities.find { it.id == id }?.let { entity ->
                Plant(
                    id = entity.id,
                    name = entity.name,
                    species = entity.species,
                    imageResId = entity.imageResId,
                    lastWatered = entity.lastWatered,
                    wateringInterval = entity.wateringInterval,
                    notes = entity.notes
                )
            }
        }
    }

    fun addPlant(plant: Plant) {
        viewModelScope.launch {
            val entity = PlantEntity(
                name = plant.name,
                species = plant.species,
                imageResId = plant.imageResId,
                lastWatered = plant.lastWatered,
                wateringInterval = plant.wateringInterval,
                notes = plant.notes
            )
            plantDao.insertPlant(entity)
        }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch {
            val entity = PlantEntity(
                id = plant.id,
                name = plant.name,
                species = plant.species,
                imageResId = plant.imageResId,
                lastWatered = plant.lastWatered,
                wateringInterval = plant.wateringInterval,
                notes = plant.notes
            )
            plantDao.updatePlant(entity)
        }
    }
}