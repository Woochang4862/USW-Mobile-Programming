package com.example.kioskapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kioskapp.adapter.CategoryRecyclerAdapter
import com.example.kioskapp.adapter.MenuRecyclerAdapter
import com.example.kioskapp.adapter.OrderRecyclerAdapter
import com.example.kioskapp.adapter.listener.OnItemClickListener
import com.example.kioskapp.databinding.ActivityMainBinding
import com.example.kioskapp.model.ToppingItem
import com.example.kioskapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var categoryRecyclerAdapter: CategoryRecyclerAdapter
    private lateinit var menuRecyclerAdapter: MenuRecyclerAdapter
    private lateinit var shoppingCartRecyclerAdapter: OrderRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        with(binding) {
            Glide.with(applicationContext)
                .load("https://www.mcdonalds.co.kr/kor/images/common/logo.png")
                .into(binding.mcdonaldsLogo)

            // Category RecyclerView 설정
            categoryRecyclerAdapter = CategoryRecyclerAdapter(object : OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val category = categoryRecyclerAdapter.getItem(position)
                    viewModel.selectCategory(category)
                }
            })
            categoryList.adapter = categoryRecyclerAdapter
            categoryList.layoutManager = GridLayoutManager(applicationContext, 2)

            // Menu RecyclerView 설정
            menuRecyclerAdapter = MenuRecyclerAdapter(object : OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val menuItem = menuRecyclerAdapter.getItem(position)
                    val bottomSheet = MenuDetailBottomSheet.newInstance(menuItem, object : OnOrderConfirmListener {
                        override fun onOrderConfirm(quantity: Int, toppings: List<ToppingItem>) {
                            viewModel.addOrderItem(menuItem, quantity, toppings)
                        }
                    })
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                }
            })
            menuList.adapter = menuRecyclerAdapter
            menuList.layoutManager = GridLayoutManager(applicationContext, 3)

            // Shopping Cart RecyclerView 설정
            shoppingCartRecyclerAdapter = OrderRecyclerAdapter { items ->
                // 총 가격은 ViewModel에서 관리되므로 여기서는 빈 람다
            }
            shoppingCart.adapter = shoppingCartRecyclerAdapter
            shoppingCart.layoutManager = LinearLayoutManager(applicationContext)

            // 주문 완료 버튼
            btnDone.setOnClickListener {
                val intent = Intent(this@MainActivity, OrderConfirmActivity::class.java)
                intent.putParcelableArrayListExtra("orderItems", viewModel.getOrderItemsForIntent())
                startActivity(intent)
            }
        }
    }

    private fun setupObservers() {
        // 카테고리 목록 관찰
        viewModel.categories.observe(this) { categories ->
            categoryRecyclerAdapter.clear()
            categories.forEach { categoryRecyclerAdapter.add(it) }
        }

        // 선택된 카테고리 관찰
        viewModel.selectedCategory.observe(this) { category ->
            binding.menuHeader.text = category.name
        }

        // 메뉴 아이템 목록 관찰
        viewModel.menuItems.observe(this) { menuItems ->
            menuRecyclerAdapter.clear()
            menuItems.forEach { menuRecyclerAdapter.add(it) }
        }

        // 주문 아이템 목록 관찰
        viewModel.orderItems.observe(this) { orderItems ->
            shoppingCartRecyclerAdapter.clear()
            orderItems.forEach { shoppingCartRecyclerAdapter.addItem(it) }
        }

        // 총 가격 관찰
        viewModel.totalPrice.observe(this) { totalPrice ->
            binding.totalPrice.text = "$totalPrice 원"
        }
    }
}