package com.cs4520.assignment5.data.models

//our classical product name, date,price, and type (for the image)
sealed class Products(
    val name: String, val date: String?,
    val price: Double, val type: String
) {

    data class EquipmentProduct(
        val equipmentName: String, val equipmentDate: String?, val equipmentPrice: Double,
    ) :
        Products(equipmentName, equipmentDate, equipmentPrice, "Equipment")

    data class FoodProduct(
        val foodName: String, val foodDate: String?, val foodPrice: Double,
    ) :
        Products(foodName, foodDate, foodPrice, "Food")
}



