package com.example.kioskapp.viewmodel

import CategoryItem
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kioskapp.model.MenuItem
import com.example.kioskapp.model.OrderItem
import com.example.kioskapp.model.ToppingItem
import com.example.kioskapp.repository.MenuRepository
import com.example.kioskapp.repository.OrderRepository

class MainViewModel : ViewModel() {
    
    private val menuRepository = MenuRepository()
    private val orderRepository = OrderRepository()
    
    // Categories
    private val _categories = MutableLiveData<List<CategoryItem>>()
    val categories: LiveData<List<CategoryItem>> = _categories
    
    // Selected Category
    private val _selectedCategory = MutableLiveData<CategoryItem>()
    val selectedCategory: LiveData<CategoryItem> = _selectedCategory
    
    // Menu Items
    private val _menuItems = MutableLiveData<List<MenuItem>>()
    val menuItems: LiveData<List<MenuItem>> = _menuItems
    
    // Order Items
    private val _orderItems = MutableLiveData<List<OrderItem>>()
    val orderItems: LiveData<List<OrderItem>> = _orderItems
    
    // Total Price
    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice
    
    init {
        loadCategories()
    }
    
    private fun loadCategories() {
        val categories = menuRepository.getCategories()
        _categories.value = categories
        
        // 첫 번째 카테고리를 기본 선택
        if (categories.isNotEmpty()) {
            Log.d("TAG", "loadCategories: ")
            selectCategory(categories[0])
        }
    }
    
    fun selectCategory(category: CategoryItem) {
        _selectedCategory.value = category
        _menuItems.value = menuRepository.getMenuItems(category)
        Log.d("TAG", "selectCategory: ${menuItems.value}")
    }
    
    fun addOrderItem(menuItem: MenuItem, quantity: Int, toppings: List<ToppingItem>) {
        val orderItem = OrderItem(
            id = menuItem.id.toLong(),
            name = menuItem.name,
            price = menuItem.price,
            imageUrl = menuItem.imageUrl,
            quantity = quantity,
            toppings = toppings
        )
        
        orderRepository.addOrderItem(orderItem)
        updateOrderData()
    }
    
    fun updateOrderItemQuantity(orderItem: OrderItem, newQuantity: Int) {
        orderRepository.updateOrderItemQuantity(orderItem, newQuantity)
        updateOrderData()
    }
    
    fun removeOrderItem(orderItem: OrderItem) {
        orderRepository.removeOrderItem(orderItem)
        updateOrderData()
    }
    
    private fun updateOrderData() {
        _orderItems.value = orderRepository.orderItems
        _totalPrice.value = orderRepository.getTotalPrice()
    }
    
    fun getOrderItemsForIntent(): ArrayList<OrderItem> {
        return orderRepository.getOrderItemsForIntent()
    }
    
    fun getToppings(): List<ToppingItem> {
        return menuRepository.getToppings()
    }
} 