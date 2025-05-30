package com.example.kioskapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kioskapp.adapter.ToppingRecyclerAdapter
import com.example.kioskapp.databinding.BottomSheetMenuDetailBinding
import com.example.kioskapp.model.MenuItem
import com.example.kioskapp.model.ToppingItem
import com.example.kioskapp.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuDetailBottomSheet(private val onOrderConfirmListener: OnOrderConfirmListener) : BottomSheetDialogFragment() {
    private var _binding: BottomSheetMenuDetailBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var toppingAdapter: ToppingRecyclerAdapter
    private var quantity = 1
    private var isCustomizeMode = false

    companion object {
        private const val ARG_MENU_ITEM = "menu_item"

        fun newInstance(menuItem: MenuItem, onOrderConfirmListener: OnOrderConfirmListener): MenuDetailBottomSheet {
            val fragment = MenuDetailBottomSheet(onOrderConfirmListener)
            val args = Bundle()
            args.putString("name", menuItem.name)
            args.putInt("price", menuItem.price)
            args.putString("imageUrl", menuItem.imageUrl)
            fragment.arguments = args
            return fragment
        }

        var TAG = MenuDetailBottomSheet::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetMenuDetailBinding.inflate(inflater, container, false)
        
        // 데이터 설정
        val name = arguments?.getString("name") ?: ""
        val price = arguments?.getInt("price") ?: 0
        val imageUrl = arguments?.getString("imageUrl") ?: ""
        
        setupUI(name, price, imageUrl)
        setupListeners()
        setupToppingRecyclerView()
        
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun setupUI(name: String, price: Int, imageUrl: String) {
        with(binding) {
            menuName.text = name
            menuDescription.text = "540 Cal" // 예시 값. 실제로는 MenuItem에서 가져와야 합니다.
            menuPrice.text = "$price 원"
            orderQuantity.text = quantity.toString()
            
            Glide.with(requireContext())
                .load(imageUrl)
                .into(menuImage)
        }
    }
    
    private fun setupListeners() {
        with(binding) {
            // 닫기 버튼
            btnClose.setOnClickListener {
                dismiss()
            }
            
            // Apply 버튼
            btnApply.setOnClickListener {
                Log.d(TAG, "Apply 버튼 클릭됨")
                // 상태 ID를 직접 지정하여 transitionToState() 사용
                motionLayout.transitionToState(R.id.default_state)
                isCustomizeMode = false
            }
            
            // 수량 증가 버튼 - 자동 커스터마이즈 모드 전환 제거
            btnIncrease.setOnClickListener {
                quantity++
                orderQuantity.text = quantity.toString()
            }
            
            // 수량 감소
            btnDecrease.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    orderQuantity.text = quantity.toString()
                }
            }

            btnDone.setOnClickListener {
                val toppings = toppingAdapter.getToppings()
                onOrderConfirmListener.onOrderConfirm(quantity, toppings)
                dismiss()
            }
        }
    }
    
    private fun setupToppingRecyclerView() {
        toppingAdapter = ToppingRecyclerAdapter { toppingItem ->
            // 토핑 선택 시 처리
        }
        
        binding.toppingRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = toppingAdapter
        }
        
        // ViewModel에서 토핑 데이터 가져오기
        val toppings = viewModel.getToppings()
        toppingAdapter.setItems(toppings)
    }
} 

interface OnOrderConfirmListener {
    fun onOrderConfirm(quantity: Int, toppings: List<ToppingItem>)
}