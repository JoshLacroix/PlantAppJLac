package com.example.plantapp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class StoreItem(
    val name: String,
    val price: Double
)

class StoreViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<StoreItem>>(
        listOf(
            StoreItem("Pots", 9.99),
            StoreItem("Soil", 6.99),
            StoreItem("Fertilizer", 7.99)
        )
    )
    val items: StateFlow<List<StoreItem>> = _items
    private val _quantities = MutableStateFlow<Map<String, Int>>(emptyMap())
    val quantities: StateFlow<Map<String, Int>> = _quantities
    val total: Double
        get() = _quantities.value.entries.sumOf { (name, qty) ->
            val item = _items.value.find { it.name == name }
            item?.price?.times(qty) ?: 0.0
        }
    fun addItem(itemName: String) {
        val currentQty = _quantities.value[itemName] ?: 0
        _quantities.value = _quantities.value + (itemName to currentQty + 1)
    }
}