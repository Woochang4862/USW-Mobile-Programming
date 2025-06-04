package com.example.project10_2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project10_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var images: ArrayList<ImageView>
    private val voteCount: IntArray = IntArray(9)
    private val imageNames = arrayOf("그림1", "그림2", "그림3", "그림4", "그림5", "그림6", "그림7", "그림8", "그림9")

    private var toast: Toast? = null

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

        images = arrayListOf(
            binding.image1,
            binding.image2,
            binding.image3,
            binding.image4,
            binding.image5,
            binding.image6,
            binding.image7,
            binding.image8,
            binding.image9,
        )

        for (i in 0..8) {
            images[i].setOnClickListener{
                voteCount[i]++
                toast?.cancel()
                toast = Toast.makeText(this, "${imageNames[i]} : 총 ${voteCount[i]}표", Toast.LENGTH_SHORT)
                toast!!.show()
            }
        }

        binding.end.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("voteCount", voteCount)
            intent.putExtra("imageNames", imageNames)
            startActivity(intent)
        }
    }
}