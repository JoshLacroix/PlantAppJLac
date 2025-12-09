package com.example.plantapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val species: String,
    val imageResId: Int,
    val lastWatered: LocalDate,
    val wateringInterval: Int,
    val notes: String
)