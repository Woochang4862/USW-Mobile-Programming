package com.example.movielistapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.movielistapp.databinding.ItemMovieBinding

class MovieAdapter(
    private val onPosterClickListener: OnItemClickListener
) : BaseAdapter() {

    var items: ArrayList<Movie> = arrayListOf()

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Movie {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return getItem(p0).id
    }

    override fun getView(p0: Int, p1: View?, parent: ViewGroup?): View {
        val layoutInflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        val view = binding.root
        val movie = getItem(p0)
        with(binding) {
            title.text = movie.title
            rating.text = movie.rating
            genre.text = movie.genre
            year.text = movie.year

            Glide
                .with(parent.context)
                .load(movie.posterUrl)
                .centerCrop()
                .into(poster)

            poster.setOnClickListener {
                onPosterClickListener.onItemClick(p1, p0)
            }
        }
        return view
    }

}
