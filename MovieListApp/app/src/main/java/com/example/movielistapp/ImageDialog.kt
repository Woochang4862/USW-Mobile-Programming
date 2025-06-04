package com.example.movielistapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.movielistapp.databinding.DialogImageBinding

class ImageDialog(
    private val title:String,
    private val imageUrl: String
) : DialogFragment() {

    private lateinit var binding: DialogImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogImageBinding.inflate(inflater, container, false)
        binding.title.text = title
        if (context != null) {
            Glide.with(requireContext())
                .load(imageUrl)
                .centerCrop()
                .into(binding.poster)
        }
        return binding.root
    }
}