package com.cs4520.assignment5.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cs4520.assignment5.data.models.Products
import com.cs4520.assignment5.domain.ProductListViewModel

@Composable
fun ProductListScreen(
    productListViewModel: ProductListViewModel = viewModel()
) {
    val context = LocalContext.current

    val errorMessageState = productListViewModel.errorMssgState.value

    ProductListPage(productListViewModel = productListViewModel)

    if (errorMessageState.isNullOrEmpty().not()) {
        Toast.makeText(context, errorMessageState, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun LoadingProgressBarScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth().background(Color.White),
        contentAlignment = Alignment.Center,
    )
    {
        CircularProgressIndicator()
    }
}


@Composable 
fun NoProductsAvailableScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth().background(Color.White),
        contentAlignment = Alignment.Center,
    )
    {
        Text(text = "No products available :,(")
    }
}

@Composable
fun ProductListPage(
    productListViewModel: ProductListViewModel,
) {
    val isLoading = productListViewModel.loading.value

    val productListState = productListViewModel.productsListState.value

    Column (
        modifier = Modifier.background(Color.White)
    ){
        if (isLoading) {
            LoadingProgressBarScreen(
                modifier = Modifier.weight(0.9f)
            )
        }

        productListState?.run {
            if (this.isEmpty()) {
                NoProductsAvailableScreen(
                    modifier = Modifier.weight(0.9f)
                )
            } else {
                ProductList(
                    modifier = Modifier.weight(0.9f),
                    products = productListViewModel.productsListState.value ?: emptyList()
                )
            }
        }

        Box(
            modifier = Modifier.weight(0.1f).fillMaxWidth()
        ) {
            PaginationComponent(
                page = productListViewModel.pageNumState.value,
                onPageNext = { productListViewModel.onNextPageClick() },
                onPageBack = { productListViewModel.onBackPageClick() }
            )
        }
    }
}

@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    products: List<Products>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(products) { product ->
            ProductCard(product = product)
        }
    }
}

@Composable
fun PaginationComponent(
    page: Int,
    onPageNext: () -> Unit,
    onPageBack: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(vertical = 10.dp).background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center
        ) {
            if (page > 1) {
                Button(onClick = onPageBack) {
                    Text(text = "Back")
                }
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 15.dp), text = page.toString()
        )
        Box(
            modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center
        ) {
            Button(onClick = onPageNext) {
                Text(text = "Next")
            }
        }
    }
}
