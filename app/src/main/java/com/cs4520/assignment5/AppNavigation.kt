package com.cs4520.assignment5

enum class Screen {
    LOGIN,
    PRODUCT_LIST,
}
sealed class NavigationItem(val route: String) {
    object ProductList : NavigationItem(Screen.PRODUCT_LIST.name)
    object Login : NavigationItem(Screen.LOGIN.name)
}