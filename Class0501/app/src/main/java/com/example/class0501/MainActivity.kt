package com.example.class0501

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.class0501.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var b : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.baseLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val mInflater = menuInflater
        mInflater.inflate(R.menu.menu1, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.itemRed -> {
                b.baseLayout.setBackgroundColor(Color.RED)
            }
            R.id.itemGreen -> {
                b.baseLayout.setBackgroundColor(Color.GREEN)
            }
            R.id.itemBlue -> {
                b.baseLayout.setBackgroundColor(Color.BLUE)
            }
            R.id.subRotate -> {
                b.button1.rotation = 45f
            }
            R.id.subSize -> {
                b.button1.scaleX = 2f
            }
        }
        return false
    }
}