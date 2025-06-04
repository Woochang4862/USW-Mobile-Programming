package com.example.kioskapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.kioskapp.databinding.ActivityPaymentProcessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaymentProcessActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPaymentProcessBinding
    private val handler = Handler(Looper.getMainLooper())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val paymentType = intent.getStringExtra("PAYMENT_TYPE") ?: "CARD"
        val paymentTypeName = intent.getStringExtra("PAYMENT_TYPE_NAME") ?: "카드 결제"
        val totalAmount = intent.getIntExtra("TOTAL_AMOUNT", 0)
        
        setupUI(paymentType, paymentTypeName, totalAmount)
        startPaymentProcess()
    }
    
    private fun setupUI(paymentType: String, paymentTypeName: String, totalAmount: Int) {
        with(binding) {
            // 맥도날드 로고 설정
            Glide.with(this@PaymentProcessActivity)
                .load("https://www.mcdonalds.co.kr/kor/images/common/logo.png")
                .into(mcdonaldsLogo)
            
            // 결제 방식에 따른 아이콘 설정
            val iconRes = when(paymentType) {
                "CARD" -> R.drawable.ic_card
                "CASH" -> R.drawable.ic_cash
                "MOBILE" -> R.drawable.ic_phone
                else -> R.drawable.ic_card
            }
            paymentIcon.setImageResource(iconRes)
            
            // 상태 텍스트 설정
            processStatus.text = "${paymentTypeName} 진행 중..."
            paymentAmount.text = "${totalAmount}원"
            
            // 결제 방식에 따른 안내 메시지
            val instructionText = when(paymentType) {
                "CARD" -> "카드를 삽입하고 잠시 기다려주세요"
                "CASH" -> "현금을 투입해주세요"
                "MOBILE" -> "휴대폰을 단말기에 대어주세요"
                else -> "잠시 기다려주세요"
            }
            this.instructionText.text = instructionText
        }
    }
    
    private fun startPaymentProcess() {
        lifecycleScope.launch {
            delay(3000)
            showPaymentComplete()
            delay(5000)
            goToInitialScreen()
        }
    }
    
    private fun showPaymentComplete() {
        with(binding) {
            // 진행 중 요소들 숨기기
            processStatus.visibility = View.GONE
            progressBar.visibility = View.GONE
            instructionText.visibility = View.GONE
            amountContainer.visibility = View.GONE
            paymentIcon.visibility = View.GONE
            
            // 완료 상태 보이기
            successContainer.visibility = View.VISIBLE
        }
    }
    
    private fun goToInitialScreen() {
        val intent = Intent(this, SelectOrderTypeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
} 