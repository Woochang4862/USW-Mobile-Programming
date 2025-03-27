package com.example.class0320
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
enum class Operation { ADD, SUB, MUL, DIV, MOD }
class MainActivity : AppCompatActivity() {
    lateinit var number1EditText: EditText
    lateinit var number2EditText: EditText
    lateinit var addButton: Button
    lateinit var subButton: Button
    lateinit var mulButton: Button
    lateinit var divButton: Button
    lateinit var modButton: Button
    lateinit var resultTextView: TextView
    private val operationMap = hashMapOf(
        R.id.add_button to Operation.ADD,
        R.id.sub_button to Operation.SUB,
        R.id.mul_button to Operation.MUL,
        R.id.div_button to Operation.DIV,
        R.id.mod_button to Operation.MOD
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        number1EditText = findViewById(R.id.number1)
        number2EditText = findViewById(R.id.number2)
        addButton = findViewById(R.id.add_button)
        subButton = findViewById(R.id.sub_button)
        mulButton = findViewById(R.id.mul_button)
        divButton = findViewById(R.id.div_button)
        modButton = findViewById(R.id.mod_button)
        resultTextView = findViewById(R.id.result)
        addButton.setOnClickListener(callback)
        subButton.setOnClickListener(callback)
        mulButton.setOnClickListener(callback)
        divButton.setOnClickListener(callback)
        modButton.setOnClickListener(callback)
    }
    val callback = View.OnClickListener {
        val num1 = number1EditText.text.toString().toDouble()
        val num2 = number2EditText.text.toString().toDouble()
        resultTextView.setText("계산 결과 : ${try { calculate(num1, num2, operationMap[it.id]).toString() } catch (e: Exception) { e.message ?: "🤮" }}")
    }
    private fun calculate(num1: Double, num2: Double, operation: Operation?): Double {
        if (operation in listOf(Operation.DIV, Operation.MOD) && num2 == 0.0)
            throw Exception("0으로 나눌 수 없습니다.")
        return when (operation) {
            Operation.ADD -> num1 + num2
            Operation.SUB -> num1 - num2
            Operation.MUL -> num1 * num2
            Operation.DIV -> num1 / num2
            Operation.MOD -> num1 % num2
            null -> throw Exception("올바르지 않은 연산자입니다.")
        }
    }
}