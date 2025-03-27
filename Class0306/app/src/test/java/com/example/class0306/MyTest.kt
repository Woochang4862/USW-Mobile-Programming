package com.example.class0306

import android.util.Log
import org.junit.Test

class MyTest {
    @Test
    fun testSum() {
        val a = 10
        val b = 20
        val sum = a + b
        println("sum : $sum")
        assert(sum == a + b)
    }

    @Test
    fun testSet() {
        val fruits = mutableSetOf("apple", "banana", "pineapple")
        fruits.add("peach")
        Log.d("TAG", fruits.toString())
    }
}