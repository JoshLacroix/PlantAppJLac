package com.example.plantapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
class PlantViewModel : ViewModel() {
    private val _plants = MutableStateFlow<List<Plant>>(emptyList())
    val plants: StateFlow<List<Plant>> = _plants

    init {
        _plants.value = listOf(
            Plant(
                1,
                "Basil",
                "Ocimum basilicum",
                R.drawable.ic_launcher_foreground,
                LocalDate.now(),
                7,
                "Needs sunlight."
            ),
            Plant(
                2,
                "Rose",
                "Rosa",
                R.drawable.ic_launcher_foreground,
                LocalDate.now().minusDays(5),
                3,
                "Water roots only."
            )
        )
    }

    fun getPlantById(id: Int): Plant? = _plants.value.find { it.id == id }

    fun addPlant(plant: Plant) {
        _plants.value =
            _plants.value + plant.copy(id = (_plants.value.maxOfOrNull { it.id } ?: 0) + 1)
    }

    fun updatePlant(updatedPlant: Plant) {
        _plants.value = _plants.value.map { if (it.id == updatedPlant.id) updatedPlant else it }
    }
}