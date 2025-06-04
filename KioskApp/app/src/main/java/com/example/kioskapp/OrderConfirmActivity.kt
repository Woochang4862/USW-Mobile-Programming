package com.example.kioskapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
    private var orderItems: ArrayList<OrderItem> = arrayListOf()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrderConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Intent에서 주문 목록 받기
        orderItems = intent.getParcelableArrayListExtra<OrderItem>("orderItems") ?: arrayListOf()
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
                // 결제 화면으로 이동
                val intent = Intent(this@OrderConfirmActivity, PaymentActivity::class.java)
                intent.putExtra("TOTAL_AMOUNT", viewModel.totalPrice.value ?: 0)
                intent.putParcelableArrayListExtra("ORDER_ITEMS", orderItems)
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