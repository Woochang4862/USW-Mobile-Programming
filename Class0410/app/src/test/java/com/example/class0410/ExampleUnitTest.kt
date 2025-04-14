package com.example.class0410

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println(evaluateExpression("123+123*0"))
    }

    fun evaluateExpression(expression: String): Double {
        val tokens = mutableListOf<String>()
        var number = ""

        // 1. 숫자와 연산자 분리
        for (ch in expression) {
            if (ch.isDigit() || ch == '.') {
                number += ch
            } else if (ch in "+-*/") {
                if (number.isNotEmpty()) {
                    tokens.add(number)
                    number = ""
                }
                tokens.add(ch.toString())
            }
        }
        if (number.isNotEmpty()) tokens.add(number)

        // 2. *, / 먼저 계산
        var i = 0
        while (i < tokens.size) {
            if (tokens[i] == "*" || tokens[i] == "/") {
                val left = tokens[i - 1].toDouble()
                val right = tokens[i + 1].toDouble()
                val result = if (tokens[i] == "*") left * right else left / right
                tokens[i - 1] = result.toString()
                tokens.removeAt(i)     // remove operator
                tokens.removeAt(i)     // remove right operand
                i -= 1                 // move back
            } else {
                i++
            }
        }

        // 3. +, - 계산
        var result = tokens[0].toDouble()
        i = 1
        while (i < tokens.size) {
            val op = tokens[i]
            val num = tokens[i + 1].toDouble()
            result = when (op) {
                "+" -> result + num
                "-" -> result - num
                else -> result
            }
            i += 2
        }

        return result
    }

}