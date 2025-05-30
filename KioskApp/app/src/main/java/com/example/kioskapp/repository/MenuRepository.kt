package com.example.kioskapp.repository

import CategoryItem
import com.example.kioskapp.model.MenuItem
import com.example.kioskapp.model.ToppingItem

class MenuRepository {
    
    fun getCategories(): List<CategoryItem> {
        return listOf(
            CategoryItem(
                "버거",
                imageUrl = "https://www.mcdonalds.co.kr/upload/product/pcfile/1583727855319.png"
            ),
            CategoryItem(
                "맥런치",
                imageUrl = "https://www.mcdonalds.co.kr/upload/product/pcfile/1723562078946.png"
            ),
            CategoryItem(
                "맥모닝",
                imageUrl = "https://www.mcdonalds.co.kr/uploadFolder/product/prov_201901290308474630.png"
            ),
            CategoryItem(
                "해피스낵",
                imageUrl = "https://www.mcdonalds.co.kr/upload/product/pcfile/1715674478138.png"
            ),
            CategoryItem(
                "사이드&디저트",
                imageUrl = "https://www.mcdonalds.co.kr/upload/product/pcfile/1745400936851.png"
            ),
            CategoryItem(
                "맥카페&음료",
                imageUrl = "https://www.mcdonalds.co.kr/upload/product/pcfile/1677678794782.png"
            ),
            CategoryItem(
                "해피밀",
                imageUrl = "https://www.mcdonalds.co.kr/kor/images/common/logo.png"
            )
        )
    }
    
    fun getMenuItems(category: CategoryItem): List<MenuItem> {
        // 실제 앱에서는 API 호출이나 데이터베이스에서 가져올 데이터
        return (1..9).map { i ->
            MenuItem(
                id = i.toLong(),
                name = "${category.name} 메뉴 $i",
                price = (3000..8000).random(),
                imageUrl = "https://m.mcdonalds.co.kr/upload/product/pcimg/1583727841698.png"
            )
        }
    }
    
    fun getToppings(): List<ToppingItem> {
        return listOf(
            ToppingItem(1, "Big Mac Bun", 45, "https://m.mcdonalds.co.kr/upload/product/pcimg/1583727841698.png"),
            ToppingItem(2, "100% Beef Patty", 150, "https://m.mcdonalds.co.kr/upload/product/pcimg/1583727868179.png"),
            ToppingItem(3, "Shredded Lettuce", 5, "https://m.mcdonalds.co.kr/upload/product/pcimg/1583728230183.png"),
            ToppingItem(4, "Big Mac Sauce", 60, "https://m.mcdonalds.co.kr/upload/product/pcimg/1583727896286.png"),
            ToppingItem(5, "Cheese", 50, "https://m.mcdonalds.co.kr/upload/product/pcimg/1583727961767.png"),
            ToppingItem(6, "Pickle Slices", 0, "https://m.mcdonalds.co.kr/upload/product/pcimg/1583728189301.png")
        )
    }
} 