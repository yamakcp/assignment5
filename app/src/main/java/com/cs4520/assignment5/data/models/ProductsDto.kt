package com.cs4520.assignment5.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ProductDto (
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "expiryDate") val expiryDate: String?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "type") val type: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

fun ProductDto.toProduct(): Products {
    return when (this.type) {
        "Food"->
            Products.FoodProduct(this.name.orEmpty(),
                                this.expiryDate, this.price ?: 0.0)

        "Equipment" -> Products.EquipmentProduct(this.name.orEmpty(),
            this.expiryDate, this.price ?: 0.0)
        else ->
            Products.EquipmentProduct(this.name.orEmpty(),
                                    this.expiryDate, this.price ?: 0.0)
    }
}