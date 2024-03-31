package com.cs4520.assignment5.util

import com.cs4520.assignment5.data.models.Products

/**
 * filters the list of product removing duplicates and removing items with empty data.
 */
fun List<Products>.filterList(): List<Products> {
    // Remove repeated products
    val uniqueProducts = this.toSet().toList()

    // Filter out products with missing or empty data
    val filteredProducts = uniqueProducts.filter { product ->
        // Check if any required field is null or empty
        product.name.isNotBlank() && product.type.isNotBlank() && product.price > 0.0
    }

    return filteredProducts
}