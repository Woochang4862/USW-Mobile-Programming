package com.example.project_11

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_11.databinding.ActivityMainBinding
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : MemoRecyclerAdapter
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

        with(binding){
            adapter = MemoRecyclerAdapter(object : OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    Toast.makeText(
                        this@MainActivity,
                        "item $position",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            memoList.adapter = adapter
            memoList.layoutManager = LinearLayoutManager(this@MainActivity)
        }



        for (i in 1..30){
            val timestamp = DateTimeFormatter
                .ofPattern("yyyy/MM/dd HH:mm:ss")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
            adapter.items.add(Memo(i, "제목$i", timestamp))
        }

        adapter.notifyDataSetChanged()
    }
}