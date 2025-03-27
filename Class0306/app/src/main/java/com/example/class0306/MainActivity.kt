package com.example.class0306

import android.graphics.Color
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var naverButton: Button
    lateinit var fireStationButton: Button
    lateinit var kakaoButton: Button
    lateinit var finishButton: Button

    private val callback: OnClickListener = OnClickListener { view ->
        val text = (view as Button).text.toString()
        showToast(text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        title = "ASd"

        // init -
        naverButton = findViewById(R.id.btn_naver)
        fireStationButton = findViewById(R.id.btn_fire_station)
        kakaoButton = findViewById(R.id.btn_kakao)
        finishButton = findViewById(R.id.btn_finish)

        // init - setOnClickListener
        naverButton.setOnClickListener(callback)
        fireStationButton.setOnClickListener(callback)
        kakaoButton.setOnClickListener(callback)
        finishButton.setOnClickListener {
            finish()
        }

    }

    private fun showToast(text: String) {
        Toast.makeText(this, "$text Clicked!", Toast.LENGTH_SHORT).show()
    }
}