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
    private lateinit var menuItem: MenuItem

    companion object {
        private const val ARG_MENU_ITEM = "menu_item"

        fun newInstance(menuItem: MenuItem, onOrderConfirmListener: OnOrderConfirmListener): MenuDetailBottomSheet {
            val fragment = MenuDetailBottomSheet(onOrderConfirmListener)
            val args = Bundle()
            args.putParcelable(ARG_MENU_ITEM, menuItem)
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
        
        // MenuItem 가져오기
        menuItem = arguments?.getParcelable(ARG_MENU_ITEM) ?: throw IllegalArgumentException("MenuItem is required")
        
        setupUI()
        setupListeners()
        setupToppingRecyclerView()
        
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun setupUI() {
        with(binding) {
            menuName.text = menuItem.name
            menuDescription.text = "540 Cal" // 예시 값. 실제로는 MenuItem에서 가져와야 합니다.
            menuPrice.text = "${menuItem.price} 원"
            orderQuantity.text = quantity.toString()
            
            Glide.with(requireContext())
                .load(menuItem.imageUrl)
                .into(menuImage)
        }
    }
    
    private fun setupListeners() {
        with(binding) {
            // 닫기 버튼
            btnClose.setOnClickListener {
                dismiss()
            }
            
            // 뒤로가기 버튼 (MotionLayout에서 자동으로 처리되지만 명시적으로 추가)
            btnBack.setOnClickListener {
                // MotionLayout이 default_state로 전환하도록 함
                motionLayout.transitionToStart()
            }
            
            // Apply 버튼 (MotionLayout에서 자동으로 처리되지만 명시적으로 추가)
            btnApply.setOnClickListener {
                Log.d(TAG, "Apply 버튼 클릭됨")
                motionLayout.transitionToStart()
            }
            
            // 수량 증가 버튼
            btnIncrease.setOnClickListener {
                quantity++
                orderQuantity.text = quantity.toString()
                updateTotalPrice()
            }
            
            // 수량 감소
            btnDecrease.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    orderQuantity.text = quantity.toString()
                    updateTotalPrice()
                }
            }

            btnDone.setOnClickListener {
                val toppings = toppingAdapter.getToppings()
                onOrderConfirmListener.onOrderConfirm(quantity, toppings)
                dismiss()
            }
            
            // MotionLayout 상태 변화 리스너
            motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}
                
                override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}
                
                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    // 커스터마이즈 모드로 전환 완료 시 토핑 데이터 로드
                    if (currentId == R.id.customize_state) {
                        loadToppingsData()
                    }
                }
                
                override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}
            })
        }
    }
    
    private fun setupToppingRecyclerView() {
        toppingAdapter = ToppingRecyclerAdapter { toppingItem ->
            // 토핑 선택 시 가격 업데이트
            updateTotalPrice()
        }
        
        binding.toppingRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = toppingAdapter
        }
    }
    
    private fun loadToppingsData() {
        // 특정 메뉴의 토핑 데이터 가져오기 (커스터마이즈 모드에서만 로드)
        viewModel.getToppingsForMenu(menuItem) { toppings ->
            toppingAdapter.setItems(toppings)
        }
    }
    
    // 토핑 가격을 포함한 총 가격 업데이트
    private fun updateTotalPrice() {
        val selectedToppings = toppingAdapter.getToppings()
        val totalPrice = viewModel.calculateTotalPrice(menuItem, selectedToppings) * quantity
        binding.menuPrice.text = "$totalPrice 원"
    }
} 

interface OnOrderConfirmListener {
    fun onOrderConfirm(quantity: Int, toppings: List<ToppingItem>)
}