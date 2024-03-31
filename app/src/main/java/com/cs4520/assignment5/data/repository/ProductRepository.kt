package com.cs4520.assignment5.data.repository

import com.cs4520.assignment5.data.models.Products


interface ProductRepository {
    suspend fun getProduct(page: Int): List<Products>?
}