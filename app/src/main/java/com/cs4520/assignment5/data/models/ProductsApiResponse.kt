package com.cs4520.assignment5.data.models

import com.google.gson.annotations.SerializedName

class ProductsApiResponse {
    @SerializedName("data")
    private val data: List<Products>? = null

    @SerializedName("message")
    private val message: String? = null

    fun getData(): List<Products>? {
        return data
    }

    fun getMessage(): String? {
        return message
    }


}