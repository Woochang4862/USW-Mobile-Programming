package com.example.kioskapp.repository

import com.example.kioskapp.model.OrderItem
import com.example.kioskapp.model.ToppingItem

class OrderRepository {
    
    private val _orderItems = mutableListOf<OrderItem>()
    val orderItems: List<OrderItem> get() = _orderItems.toList()
    
    fun addOrderItem(orderItem: OrderItem) {
        // 같은 메뉴와 토핑 조합이 있는지 확인
        val existingItem = _orderItems.find { 
            it.id == orderItem.id && 
            it.toppings.map { topping -> topping.id }.sorted() == 
            orderItem.toppings.map { topping -> topping.id }.sorted()
        }
        
        if (existingItem != null) {
            // 기존 아이템의 수량 증가
            existingItem.quantity += orderItem.quantity
        } else {
            // 새 아이템 추가
            _orderItems.add(orderItem)
        }
    }
    
    fun removeOrderItem(orderItem: OrderItem) {
        _orderItems.remove(orderItem)
    }
    
    fun updateOrderItemQuantity(orderItem: OrderItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeOrderItem(orderItem)
        } else {
            orderItem.quantity = newQuantity
        }
    }
    
    fun getTotalPrice(): Int {
        return _orderItems.sumOf { it.getItemTotal() }
    }
    
    fun clearOrder() {
        _orderItems.clear()
    }
    
    fun getOrderItemsForIntent(): ArrayList<OrderItem> {
        return ArrayList(_orderItems)
    }
} 