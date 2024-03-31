package com.cs4520.assignment5.data.repository

import android.util.Log
import com.cs4520.assignment5.data.api.ProductClient
import com.cs4520.assignment5.data.models.Products
import com.cs4520.assignment5.data.local.ProductDao
import com.cs4520.assignment5.data.models.toProduct

class ProductRepositoryImpl(
    private val productsDao: ProductDao,
    private val apiClient: ProductClient) : ProductRepository {

    override suspend fun getProduct(page: Int): List<Products>? {
        return try {
            val result = apiClient.getProducts(page)

            if (result.isSuccessful) {
                Log.e("[API] SUCCESSFUL", result.body().toString())

                val productsListsFromApi = result.body()

                if (!productsListsFromApi.isNullOrEmpty()) {
                    Log.e("[ROOM] ADDING TO THE DB", productsListsFromApi.toString())
                    productsDao.deleteAll()
                    productsDao.addAllProducts(productsListsFromApi)
                }

                productsListsFromApi?.map{ productDto -> productDto.toProduct() }

            }

            Log.e("[API] NOT SUCCESSFUL", result.message())
            getProductsFromDatabase()

        } catch (e: Exception) {
            Log.e("[API] ERROR EXCEPTION", e.message.toString())

            getProductsFromDatabase()
        }
    }

    private suspend fun getProductsFromDatabase() : List<Products> {
        val databaseProductList = productsDao.getAllProducts()

        Log.e("[ROOM] LOADING FROM DATABASE", databaseProductList.toString())

        return databaseProductList.map{
            it.toProduct()
        }
    }
}