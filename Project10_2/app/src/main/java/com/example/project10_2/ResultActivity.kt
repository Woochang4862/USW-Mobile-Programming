package com.example.project10_2

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project10_2.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private lateinit var voteCount: IntArray
    private lateinit var imageNames: Array<String>
    private val images: Array<Int> = arrayOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6,
        R.drawable.image7,
        R.drawable.image8,
        R.drawable.image9,
    )

    private lateinit var adapter: RatingRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        voteCount = intent.getIntArrayExtra("voteCount") ?: IntArray(9)
        imageNames = intent.getStringArrayExtra("imageNames") ?: arrayOf()

        val results = arrayListOf<Pair<String, Float>>()
        for (i in voteCount.indices) {
            results.add(Pair(imageNames[i], voteCount[i].toFloat()))
        }

        val maxVoteIndex = voteCount.indices.maxBy { voteCount[it] }

        binding.imageName.text = imageNames[maxVoteIndex]
        binding.mostPopularImage.setImageDrawable(getResources().getDrawable(images[maxVoteIndex]))

        binding.returnButton.setOnClickListener {
            finish()
        }

        adapter = RatingRecyclerAdapter()
        adapter.results = results
        binding.voteResult.adapter = adapter
        binding.voteResult.layoutManager = LinearLayoutManager(this)
    }
}