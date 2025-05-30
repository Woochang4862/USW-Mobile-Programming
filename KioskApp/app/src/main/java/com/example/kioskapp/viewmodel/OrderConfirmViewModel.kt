package com.example.kioskapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kioskapp.model.OrderItem
import com.example.kioskapp.repository.OrderRepository

class OrderConfirmViewModel : ViewModel() {
    
    private val orderRepository = OrderRepository()
    
    // Order Items
    private val _orderItems = MutableLiveData<List<OrderItem>>()
    val orderItems: LiveData<List<OrderItem>> = _orderItems
    
    // Total Price
    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice
    
    fun setOrderItems(items: List<OrderItem>) {
        // Repository에 주문 아이템들을 설정
        orderRepository.clearOrder()
        items.forEach { orderRepository.addOrderItem(it) }
        
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
    
    fun checkout() {
        // 결제 로직 구현
        // 실제 앱에서는 결제 API 호출 등을 수행
        orderRepository.clearOrder()
        updateOrderData()
    }
} 