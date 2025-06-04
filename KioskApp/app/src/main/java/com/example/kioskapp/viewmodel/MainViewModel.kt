package com.example.kioskapp.viewmodel

import android.app.Application
import com.example.kioskapp.model.CategoryItem
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kioskapp.model.MenuItem
import com.example.kioskapp.model.OrderItem
import com.example.kioskapp.model.ToppingItem
import com.example.kioskapp.repository.MenuRepository
import com.example.kioskapp.repository.OrderRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    
    private val menuRepository = MenuRepository(application)
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
        viewModelScope.launch {
            try {
                val categories = menuRepository.getCategories()
                _categories.value = categories
                
                // 첫 번째 카테고리를 기본 선택
                if (categories.isNotEmpty()) {
                    selectCategory(categories[0])
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading categories", e)
            }
        }
    }
    
    fun selectCategory(category: CategoryItem) {
        viewModelScope.launch {
            try {
                _selectedCategory.value = category
                val menuItems = menuRepository.getMenuItems(category)
                _menuItems.value = menuItems
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error selecting category", e)
            }
        }
    }
    
    fun addOrderItem(menuItem: MenuItem, quantity: Int, toppings: List<ToppingItem>) {
        // 토핑 가격을 포함한 총 가격 계산
        val toppingPrice = toppings.filter { it.selected }.sumOf { it.price }
        val totalItemPrice = menuItem.price + toppingPrice
        
        val orderItem = OrderItem(
            id = menuItem.id,
            name = menuItem.name,
            price = totalItemPrice,
            imageUrl = menuItem.imageUrl,
            quantity = quantity,
            toppings = toppings.filter { it.selected }
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
    
    fun getToppings(callback: (List<ToppingItem>) -> Unit) {
        viewModelScope.launch {
            try {
                val toppings = menuRepository.getToppings()
                callback(toppings)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading toppings", e)
                callback(emptyList())
            }
        }
    }
    
    // 특정 메뉴의 토핑만 가져오는 새로운 메서드
    fun getToppingsForMenu(menuItem: MenuItem, callback: (List<ToppingItem>) -> Unit) {
        viewModelScope.launch {
            try {
                val toppings = menuRepository.getToppingsByMenu(menuItem)
                Log.d("MainViewModel", "Loaded ${toppings.size} toppings for menu: ${menuItem.name}")
                callback(toppings)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading toppings for menu", e)
                callback(emptyList())
            }
        }
    }
    
    // 토핑 가격을 포함한 메뉴 총 가격 계산
    fun calculateTotalPrice(menuItem: MenuItem, selectedToppings: List<ToppingItem>): Int {
        val toppingPrice = selectedToppings.filter { it.selected }.sumOf { it.price }
        return menuItem.price + toppingPrice
    }
} 