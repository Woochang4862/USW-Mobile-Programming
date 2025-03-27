package com.example.class0320

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    fun func(name1:String, name2:String="asd", name3:String) {

    }

    val lambdaFunc = { a:Int, b:String ->
        a+10
    }

    @Test
    fun intDivideByInt() {
        val a = 98
        val result = a / 10
        func("",name3="")
        lambdaFunc(1,"")
        assertEquals(9, result)
    }
}