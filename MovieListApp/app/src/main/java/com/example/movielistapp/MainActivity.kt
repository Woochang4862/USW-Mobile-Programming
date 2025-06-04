package com.example.movielistapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movielistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private val movies = arrayListOf<Movie>(
        Movie(1, "미션임파서블", "9.01", "DRAMA", "1992", "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000089/89629/89629_320.jpg"),
        Movie(2, "하이파이브", "9.01", "DRAMA", "1992", "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000089/89671/89671_320.jpg"),
        Movie(3, "소주전쟁", "9.01", "DRAMA", "1992", "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000089/89664/89664_320.jpg"),
        Movie(4, "드래곤 길들이기", "9.01", "DRAMA", "1992", "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000089/89315/89315_320.jpg"),
        Movie(5, "씨너스", "9.01", "DRAMA", "1992", "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000089/89679/89679_320.jpg"),
        Movie(6, "미야자키 하야오", "9.01", "DRAMA", "1992", "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000089/89660/89660_320.jpg"),
        Movie(7, "메이플스토리", "9.01", "DRAMA", "1992", "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000089/89686/89686_320.jpg"),
        Movie(8, "릴로&스티치", "9.01", "DRAMA", "1992", "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000089/89628/89628_320.jpg"),
        Movie(9, "나를 모르는 그녀의 세계에서", "9.01", "DRAMA", "1992", "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000089/89600/89600_320.jpg"),
        Movie(10, "야당", "9.01", "DRAMA", "1992", "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000089/89454/89454_320.jpg"),
    )
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

        with(binding) {
            adapter = MovieAdapter(object : OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val movie = adapter.items[position]
                    val dialog = ImageDialog(movie.title, movie.posterUrl)
                    dialog.show(supportFragmentManager, "ImageDialog")
                }
            })
            movieList.adapter = adapter
            adapter.items = movies
            adapter.notifyDataSetChanged()
        }
    }
}