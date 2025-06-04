package com.example.kioskapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kioskapp.databinding.ActivityPaymentBinding
import com.example.kioskapp.model.OrderItem

class PaymentActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPaymentBinding
    private var totalAmount: Int = 0
    private var orderItems: ArrayList<OrderItem> = arrayListOf()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Intent에서 데이터 받기
        totalAmount = intent.getIntExtra("TOTAL_AMOUNT", 0)
        orderItems = intent.getParcelableArrayListExtra("ORDER_ITEMS") ?: arrayListOf()
        
        setupUI()
        setupListeners()
    }
    
    private fun setupUI() {
        binding.totalAmount.text = "${totalAmount}원"
    }
    
    private fun setupListeners() {
        with(binding) {
            // 뒤로가기 버튼
            btnBack.setOnClickListener {
                finish()
            }
            
            // 카드 결제
            cardPayment.setOnClickListener {
                startPaymentProcess("CARD", "카드 결제")
            }
            
            // 현금 결제
            cashPayment.setOnClickListener {
                startPaymentProcess("CASH", "현금 결제")
            }
            
            // 모바일 결제
            mobilePayment.setOnClickListener {
                startPaymentProcess("MOBILE", "모바일 결제")
            }
        }
    }
    
    private fun startPaymentProcess(paymentType: String, paymentTypeName: String) {
        val intent = Intent(this, PaymentProcessActivity::class.java)
        intent.putExtra("PAYMENT_TYPE", paymentType)
        intent.putExtra("PAYMENT_TYPE_NAME", paymentTypeName)
        intent.putExtra("TOTAL_AMOUNT", totalAmount)
        intent.putParcelableArrayListExtra("ORDER_ITEMS", orderItems)
        startActivity(intent)
        finish()
    }
} 