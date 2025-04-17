package com.example.class0410

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var expressionTextView: TextView

    private val numberButtonIds = arrayOf(
        R.id.number_zero_button,
        R.id.number_one_button,
        R.id.number_two_button,
        R.id.number_three_button,
        R.id.number_four_button,
        R.id.number_five_button,
        R.id.number_six_button,
        R.id.number_seven_button,
        R.id.number_eight_button,
        R.id.number_nine_button
    )
    private val numberButtons = arrayListOf<Button>()

    private lateinit var additionButton: Button
    private lateinit var subtractionButton: Button
    private lateinit var multiplicationButton: Button
    private lateinit var divisionButton: Button
    private lateinit var clearButton: Button
    private lateinit var calculateButton: Button

    private lateinit var resultTextView: TextView

    private val operations = "+-*/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 뷰 초기화
        expressionTextView = findViewById(R.id.expression_text_view)

        for (id in numberButtonIds) {
            numberButtons.add(findViewById(id))
        }

        additionButton = findViewById(R.id.addition_button)
        subtractionButton = findViewById(R.id.subtraction_button)
        multiplicationButton = findViewById(R.id.multiplication_button)
        divisionButton = findViewById(R.id.division_button)
        clearButton = findViewById(R.id.clear_button)
        calculateButton = findViewById(R.id.calculate_button)

        resultTextView = findViewById(R.id.result_text_view)

        // Listener 등록
        for (button in numberButtons) {
            button.setOnClickListener {
                // 숫자 버튼은 눌렸을때 버튼의 적힌 숫자를 expression 에 적용
                writeExpression(button.text.toString())
            }
        }

        additionButton.setOnClickListener {
            writeExpression("+")
        }
        subtractionButton.setOnClickListener {
            writeExpression("-")
        }
        multiplicationButton.setOnClickListener {
            writeExpression("*")
        }
        divisionButton.setOnClickListener {
            writeExpression("/")
        }

        clearButton.setOnClickListener {
            expressionTextView.text = "0"
        }

        calculateButton.setOnClickListener {
            val expression = expressionTextView.text.toString()
            if (isValidExpression(expression)) {
                val result = evaluateExpression(expression)
                resultTextView.text = "계산결과 : $result"
            } else {
                Toast.makeText(this, "식을 완성해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 주어진 문자열이 유효한 사칙연산 수식인지 검사합니다.
     *
     * 유효한 수식의 조건:
     * - 양의 정수와 사칙연산자(+,-,*,/)로만 구성되어야 함
     * - 수식은 연산자로 시작하거나 끝나면 안 됨
     * - 숫자와 연산자가 번갈아 나와야 함
     * - 공백은 무시함
     *
     * @param expression 검사할 수식 문자열
     * @return 유효한 수식이면 `true`, 그렇지 않으면 `false`
     */
    private fun isValidExpression(expression: String): Boolean {
        if (expression.isEmpty()) return false

        val cleaned = expression.replace(" ", "")

        // 정규식으로 숫자 또는 연산자를 구분
        val regex = Regex("(\\d+|[+\\-*/])")
        val tokens = regex.findAll(cleaned).map { it.value }.toList()

        // 토큰이 비어있거나 파싱이 안 됐을 경우
        if (tokens.isEmpty()) return false

        // 토큰이 숫자부터 시작하고, 숫자와 연산자가 번갈아 나와야 함
        for (i in tokens.indices) {
            val token = tokens[i]
            if (i % 2 == 0) {
                // 짝수 인덱스는 숫자여야 함
                if (!token.matches(Regex("\\d+"))) return false
            } else {
                // 홀수 인덱스는 연산자여야 함
                if (!token.matches(Regex("[+\\-*/]"))) return false
            }
        }

        // 마지막 토큰은 숫자여야 함
        return tokens.last().matches(Regex("\\d+"))
    }

    /**
     * 숫자 또는 연산자 문자열을 넘겨주면 문자를 연결한 새로운 식을 만들어 [expressionTextView] 에 적용한다
     *
     *  @param strToAdd - 추가할 문자, 숫자 또는 연산자
     * */
    private fun writeExpression(strToAdd: String) {
        /**
         * 규칙
         *  1. 마지막 글자가 연산자고 [strToAdd] 도 연산자면 [strToAdd] 로 변경한다.
         *  2. [expressionTextView] 에 아무것도 입력되어 있지 않으면 0 으로 설정한다.
         *  => 즉, [expressionTextView] 가 0 이면 아무것도 없는 식이다.
         *  3. [expressionTextView] 의 내용이 비어 있는데 [strToAdd] 가 연산자라면 입력이 불가능하다.
         * */
        var currentText = expressionTextView.text.toString().let { // 2번
            if (it == "0") "" else it
        }

        val lastWord = currentText.run {
            if (isEmpty()) "" else last().toString()
        }

        if (currentText == "" && strToAdd in operations) return // 3번

        // currentText 는 빈 문자열 or 식 ( 끝이 숫자 or 연산자 )
        currentText = currentText.run {
            if (length >= 2 && lastWord in operations && strToAdd in operations) { // 1번
                substring(0..currentText.length - 2)
            } else {
                this
            }
        } + strToAdd

        expressionTextView.text = currentText

    }

    /**
     * 문자열로 주어진 사칙연산 수식을 계산하여 결과를 반환합니다.
     *
     * 입력 수식은 공백이 없고, 양의 정수 또는 소수와 사칙연산자로만 구성되어야 합니다.
     *
     * @param expression 계산할 수식 문자열
     * @return 수식 계산 결과를 Double로 반환
     * @throws NumberFormatException 숫자 파싱에 실패한 경우
     * @throws IndexOutOfBoundsException 잘못된 수식 구조일 경우
     */
    private fun evaluateExpression(expression: String): Double {
        val tokens = mutableListOf<String>()
        var number = ""

        // 1. 숫자와 연산자 분리
        for (ch in expression) {
            if (ch.isDigit()) {
                number += ch
            } else if (ch in operations) {
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
                tokens.removeAt(i)     // 연산자 제거
                tokens.removeAt(i)     // 우측 피연산자 제거
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