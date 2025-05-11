package com.example.project8_2

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import com.example.project8_2.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var imageIndex : MutableLiveData<Int> = MutableLiveData(0)
    private var imageFiles : Array<File>? = null

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
        title = "간단 이미지 뷰어"
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), Context.MODE_PRIVATE)

        imageFiles = File(Environment.getExternalStorageDirectory().absolutePath + "/Pictures").listFiles()

        binding.myPictureView.imagePath = imageFiles!![imageIndex.value!!].toString()
        imageIndex.observe(this) {
            binding.myPictureView.imagePath = imageFiles!![it].toString()
            binding.myPictureView.invalidate()
        }

        binding.nextButton.setOnClickListener {
            if (imageIndex.value!! < imageFiles!!.size-1) {
                imageIndex.postValue(imageIndex.value!! + 1)
            } else {
                Toast.makeText(this, "마지막 그림입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.previousButton.setOnClickListener {
            if (imageIndex.value!! > 0) {
                imageIndex.postValue(imageIndex.value!! - 1)
            } else {
                Toast.makeText(this, "첫 그림입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        val TAG = MainActivity.javaClass.simpleName
    }
}