package com.cs4520.assignment5.domain

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.AndroidViewModel

import androidx.work.WorkManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.Data
import androidx.work.Constraints
import androidx.work.NetworkType

import com.cs4520.assignment5.domain.worker.FetchProducts
import com.cs4520.assignment5.util.filterList

import com.cs4520.assignment5.data.api.ProductClient
import com.cs4520.assignment5.data.repository.ProductRepository
import com.cs4520.assignment5.data.models.Products
import com.cs4520.assignment5.data.local.ProductDatabase
import com.cs4520.assignment5.data.repository.ProductRepositoryImpl

import java.util.concurrent.TimeUnit
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val app: Application

) : AndroidViewModel(app) {


    private val db = ProductDatabase.getInstance(app.applicationContext)

    private val productsClient: ProductClient = ProductClient.getInstance()

    private val productsDao = db.productDao()

    private val repository: ProductRepository = ProductRepositoryImpl(productsDao, productsClient)

    var productsListState: MutableState<List<Products>?> = mutableStateOf(null)

    var loading: MutableState<Boolean> = mutableStateOf(true)

    var pageNumState: MutableState<Int> = mutableStateOf(1)

    var errorMssgState: MutableState<String?> = mutableStateOf(null)


    init {
        viewModelScope.launch { fetchProducts(pageNumState.value) }

        val data = Data.Builder().putInt("page", pageNumState.value).build()

        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val work = PeriodicWorkRequest
            .Builder(FetchProducts::class.java, 1, TimeUnit.HOURS).setInputData(data)
            .setConstraints(constraints).setInitialDelay(1, TimeUnit.HOURS).build()

        WorkManager.getInstance(app.applicationContext)
            .enqueueUniquePeriodicWork(
                    "fetchProducts", ExistingPeriodicWorkPolicy.UPDATE, work)}

    private fun fetchProducts(page: Int) {
        loading.value = true

        productsListState.value = null

        viewModelScope.launch {
            try {
                val response = repository.getProduct(page)

                response?.let { productsListState.value = it.filterList()

                } ?: run {
                    errorMssgState.value = "Error occurred in API response"
                }

            } catch (e: Exception){
                errorMssgState.value = e.message

            } finally {
                loading.value = false
            }
        }
    }

    fun onBackPageClick() {
        if (1 < pageNumState.value) {
            pageNumState.value -= 1
        }

        fetchProducts(pageNumState.value)
    }

    fun onNextPageClick() {
        pageNumState.value = pageNumState.value + 1
        fetchProducts(pageNumState.value)
    }

}