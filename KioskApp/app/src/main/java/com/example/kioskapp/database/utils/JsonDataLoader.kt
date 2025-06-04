package com.example.kioskapp.database.utils

import android.content.Context
import com.example.kioskapp.database.model.InitialDataJson
import com.google.gson.Gson
import java.io.IOException

object JsonDataLoader {
    
    fun loadInitialDataFromAssets(context: Context, fileName: String = "initial_data.json"): InitialDataJson? {
        return try {
            val jsonString = loadJsonFromAssets(context, fileName)
            val gson = Gson()
            gson.fromJson(jsonString, InitialDataJson::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    private fun loadJsonFromAssets(context: Context, fileName: String): String {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
            ""
        }
    }
} 