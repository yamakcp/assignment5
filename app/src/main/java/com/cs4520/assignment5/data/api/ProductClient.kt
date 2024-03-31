package com.cs4520.assignment5.data.api

import com.cs4520.assignment5.data.models.ProductDto
import com.cs4520.assignment5.util.constants.Api
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.Response

class ProductClient(
    private val productApi: ProductApi) {
    suspend fun getProducts(page: Int): Response<List<ProductDto>> {
        return productApi.getProducts(page)
    }

    companion object {
        @Volatile
        private var INST: ProductClient? = null

        //similar to local getInstance
        fun getInstance(): ProductClient {
            synchronized(this) {
                var instance = INST

                if (instance == null) {
                    val retrofit = Retrofit.Builder().baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                    val productApi: ProductApi = retrofit.create(ProductApi::class.java)
                    instance = ProductClient(productApi)
                    INST = instance
                }
                return instance
            }
        }
    }
}