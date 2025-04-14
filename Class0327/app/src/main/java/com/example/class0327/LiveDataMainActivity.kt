package com.example.class0327

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData

class LiveDataMainActivity : AppCompatActivity() {

    // 뷰를 담을 변수 선언
    private lateinit var startCheckBox: CheckBox
    private lateinit var selectContainer: LinearLayout
    private lateinit var animalImageView: ImageView
    private lateinit var radioGroup: RadioGroup

    // RadioButton 아이디에 따라서 표시할 Drawble id 매핑
    private val drawables = mapOf(
        R.id.dog_button to R.drawable.dog,
        R.id.cat_button to R.drawable.cat,
        R.id.rabbit_button to R.drawable.rabbit
    )

    // 사용할 데이터 선언
    private val visibility: MutableLiveData<Boolean> = MutableLiveData(false)
    private val displayImage: MutableLiveData<Int?> = MutableLiveData(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = "애완동물 사진 보기"

        initialize()
    }

    private fun initialize() {
        // 뷰 바인딩
        startCheckBox = findViewById(R.id.start_check_box)
        selectContainer = findViewById(R.id.select_container)
        animalImageView = findViewById(R.id.animal_image_view)
        radioGroup = findViewById(R.id.radio_group)

        // 리스너 등록
        startCheckBox.setOnCheckedChangeListener { _, isChecked ->
            visibility.value = isChecked
        }
        radioGroup.setOnCheckedChangeListener { _, id ->
            displayImage.value = drawables[id]
        }

        // observer 등록
        visibility.observe(this) {
            selectContainer.visibility = if (it) View.VISIBLE else View.GONE
        }
        displayImage.observe(this) {
            if (it != null)
                animalImageView.setImageResource(it)
            else
                Toast.makeText(this, "애완동물을 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}