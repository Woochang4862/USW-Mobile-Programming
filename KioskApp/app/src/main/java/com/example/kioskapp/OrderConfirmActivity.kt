package com.example.kioskapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kioskapp.adapter.OrderConfirmRecyclerAdapter
import com.example.kioskapp.databinding.ActivityOrderConfirmBinding
import com.example.kioskapp.model.OrderItem
import com.example.kioskapp.viewmodel.OrderConfirmViewModel

class OrderConfirmActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityOrderConfirmBinding
    private val viewModel: OrderConfirmViewModel by viewModels()
    private lateinit var orderConfirmAdapter: OrderConfirmRecyclerAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Intent에서 주문 목록 받기
        val orderItems = intent.getParcelableArrayListExtra<OrderItem>("orderItems") ?: arrayListOf()
        viewModel.setOrderItems(orderItems)
        
        setupUI()
        setupRecyclerView()
        setupObservers()
    }
    
    private fun setupUI() {
        with(binding) {
            // 맥도날드 로고 설정
            Glide.with(this@OrderConfirmActivity)
                .load("https://www.mcdonalds.co.kr/kor/images/common/logo.png")
                .into(mcdonaldsLogo)
            
            // 뒤로가기 버튼
            btnBack.setOnClickListener {
                finish()
            }
            
            // 체크아웃 버튼
            btnCheckout.setOnClickListener {
                viewModel.checkout()
                // 결제 완료 후 메인 화면으로 이동
                val intent = Intent(this@OrderConfirmActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }
    }
    
    private fun setupRecyclerView() {
        orderConfirmAdapter = OrderConfirmRecyclerAdapter(arrayListOf()) { updatedItems ->
            // 어댑터에서 수량 변경 시 ViewModel에 알림
            // 실제 구현에서는 개별 아이템 업데이트를 위한 콜백이 필요
        }
        
        binding.orderRecyclerView.apply {
            adapter = orderConfirmAdapter
            layoutManager = LinearLayoutManager(this@OrderConfirmActivity)
        }
    }
    
    private fun setupObservers() {
        // 주문 아이템 목록 관찰
        viewModel.orderItems.observe(this) { orderItems ->
            orderConfirmAdapter.updateItems(orderItems)
        }
        
        // 총 가격 관찰
        viewModel.totalPrice.observe(this) { totalPrice ->
            binding.totalPrice.text = "${totalPrice}원"
        }
    }
} 