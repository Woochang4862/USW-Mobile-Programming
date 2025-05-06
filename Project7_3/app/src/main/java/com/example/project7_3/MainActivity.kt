package com.example.project7_3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import com.example.project7_3.databinding.ActivityMainBinding
import com.example.project7_3.databinding.DialogInputBinding
import com.example.project7_3.databinding.ToastViewBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var username : MutableLiveData<String> = MutableLiveData()
    private var email : MutableLiveData<String> = MutableLiveData()

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

        username.observe(this) { value ->
            binding.username.text = value
        }

        email.observe(this) { value ->
            binding.email.text = value
        }

        binding.dialogButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogBinding = DialogInputBinding.inflate(layoutInflater)
            builder
                .setIcon(R.drawable.ic_groups_24dp)
                .setTitle("사용자 정보 입력")
                .setView(dialogBinding.root)
                .setPositiveButton("확인") { _,_ ->
                    username.value = dialogBinding.usernameEdit.text.toString()
                    email.value = dialogBinding.emailEdit.text.toString()
                }
                .setNegativeButton("취소") { _,_ ->
                    val toast = Toast.makeText(this, null, Toast.LENGTH_SHORT)
                    val toastBinding = ToastViewBinding.inflate(layoutInflater)
                    toast.view = toastBinding.root
                    toast.show()
                }
            builder.show()
        }
    }
}