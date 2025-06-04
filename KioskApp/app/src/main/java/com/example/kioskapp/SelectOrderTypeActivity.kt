package com.example.kioskapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kioskapp.databinding.ActivitySelectOrderTypeBinding
import com.example.kioskapp.repository.MenuRepository

class SelectOrderTypeActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySelectOrderTypeBinding
    private var selectedOrderType: String = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectOrderTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MenuRepository(application)

        setupUI()
        setupListeners()
    }
    
    private fun setupUI() {
        // 맥도날드 로고 설정
        Glide.with(this)
            .load("https://www.mcdonalds.co.kr/kor/images/common/logo.png")
            .into(binding.mcdonaldsLogo)
    }
    
    private fun setupListeners() {
        with(binding) {
            // 매장 식사 선택
            cardDineIn.setOnClickListener {
                selectOption("DINE_IN", "매장에서 식사")
                updateCardSelection(true, false)
            }
            
            // 포장 선택
            cardTakeout.setOnClickListener {
                selectOption("TAKEOUT", "포장")
                updateCardSelection(false, true)
            }
            
            // 시작 버튼
            btnStart.setOnClickListener {
                if (selectedOrderType.isNotEmpty()) {
                    val intent = Intent(this@SelectOrderTypeActivity, MainActivity::class.java)
                    intent.putExtra("ORDER_TYPE", selectedOrderType)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
    
    private fun selectOption(orderType: String, displayText: String) {
        selectedOrderType = orderType
        with(binding) {
            selectedOption.text = "선택: $displayText"
            selectedOption.visibility = android.view.View.VISIBLE
            btnStart.isEnabled = true
        }
    }
    
    private fun updateCardSelection(dineInSelected: Boolean, takeoutSelected: Boolean) {
        with(binding) {
            // 매장 식사 카드 선택 상태
            if (dineInSelected) {
                cardDineIn.strokeWidth = 6
                cardDineIn.setCardBackgroundColor(resources.getColor(android.R.color.white, null))
            } else {
                cardDineIn.strokeWidth = 0
                cardDineIn.setCardBackgroundColor(resources.getColor(android.R.color.white, null))
            }
            
            // 포장 카드 선택 상태
            if (takeoutSelected) {
                cardTakeout.strokeWidth = 6
                cardTakeout.setCardBackgroundColor(resources.getColor(android.R.color.white, null))
            } else {
                cardTakeout.strokeWidth = 0
                cardTakeout.setCardBackgroundColor(resources.getColor(android.R.color.white, null))
            }
        }
    }
} 