package com.example.kioskapp.repository

import android.content.Context
import android.util.Log
import com.example.kioskapp.database.KioskDatabase
import com.example.kioskapp.database.converters.toCategoryEntity
import com.example.kioskapp.database.converters.toCategoryItem
import com.example.kioskapp.database.converters.toMenuEntity
import com.example.kioskapp.database.converters.toMenuItem
import com.example.kioskapp.database.converters.toToppingEntity
import com.example.kioskapp.database.converters.toToppingItem
import com.example.kioskapp.database.utils.JsonDataLoader
import com.example.kioskapp.model.CategoryItem
import com.example.kioskapp.model.MenuItem
import com.example.kioskapp.model.ToppingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuRepository(private val context: Context) {

    private val database = KioskDatabase.getDatabase(context)
    private val categoryDao = database.categoryDao()
    private val menuDao = database.menuDao()
    private val toppingDao = database.toppingDao()

    init {
        // 앱 시작 시 초기 데이터 삽입
        CoroutineScope(Dispatchers.IO).launch {
            initializeData()
        }
    }

    private suspend fun initializeData() {
        // 기존 데이터가 있는지 확인
        val existingCategories = categoryDao.getAllCategories()
        if (existingCategories.isEmpty()) {
            insertInitialDataFromJson()
        }
    }

    private suspend fun insertInitialDataFromJson() {
        try {
            val initialData = JsonDataLoader.loadInitialDataFromAssets(context)
            
            if (initialData == null) {
                Log.e("MenuRepository", "Failed to load initial data from JSON")
                return
            }

            Log.d("MenuRepository", "Loading initial data from JSON...")

            // 카테고리, 메뉴, 토핑 데이터를 저장할 리스트들
            val categoryEntities = mutableListOf<com.example.kioskapp.database.entities.CategoryEntity>()
            val menuEntities = mutableListOf<com.example.kioskapp.database.entities.MenuEntity>()
            val toppingEntities = mutableListOf<com.example.kioskapp.database.entities.ToppingEntity>()

            // JSON 데이터를 Entity로 변환
            initialData.categories.forEach { categoryJson ->
                // 카테고리 엔티티 추가
                categoryEntities.add(categoryJson.toCategoryEntity())

                // 해당 카테고리의 메뉴 아이템들 처리
                categoryJson.menuItems.forEach { menuJson ->
                    // 메뉴 엔티티 추가 (categoryId 설정)
                    menuEntities.add(menuJson.toMenuEntity(categoryJson.id))

                    // 해당 메뉴의 토핑들 처리
                    menuJson.toppings.forEach { toppingJson ->
                        // 토핑 엔티티 추가 (menuId 설정)
                        toppingEntities.add(toppingJson.toToppingEntity(menuJson.id))
                    }
                }
            }

            // 데이터베이스에 삽입
            categoryDao.insertAll(categoryEntities)
            menuDao.insertAll(menuEntities)
            toppingDao.insertAll(toppingEntities)

            Log.d("MenuRepository", "Successfully inserted ${categoryEntities.size} categories, " +
                    "${menuEntities.size} menu items, ${toppingEntities.size} toppings")

        } catch (e: Exception) {
            Log.e("MenuRepository", "Error loading initial data from JSON", e)
        }
    }

    suspend fun getCategories(): List<CategoryItem> {
        return categoryDao.getAllCategories().map { it.toCategoryItem() }
    }

    suspend fun getMenuItems(category: CategoryItem): List<MenuItem> {
        return menuDao.getMenuItemsByCategory(category.id).map { it.toMenuItem() }
    }

    suspend fun getToppings(): List<ToppingItem> {
        return toppingDao.getAllToppings().map { it.toToppingItem() }
    }

    suspend fun getToppingsByMenu(menu: MenuItem): List<ToppingItem> {
        return toppingDao.getToppingsByMenu(menu.id).map { it.toToppingItem() }
    }
} 