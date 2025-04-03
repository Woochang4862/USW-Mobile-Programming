package com.example.class0327

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // 뷰를 담을 변수 선언
    private lateinit var startCheckBox: CheckBox
    private lateinit var selectContainer: LinearLayout
    private lateinit var doneButton: Button
    private lateinit var animalImageView: ImageView
    private lateinit var radioGroup: RadioGroup

    // RadioButton 아이디에 따라서 표시할 Drawble id 매핑
    private val drawables = mapOf(
        R.id.dog_button to R.drawable.dog,
        R.id.cat_button to R.drawable.cat,
        R.id.rabbit_button to R.drawable.rabbit
    )

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
        doneButton = findViewById(R.id.done_button)
        animalImageView = findViewById(R.id.animal_image_view)
        radioGroup = findViewById(R.id.radio_group)

        // 리스너 등록
        startCheckBox.setOnCheckedChangeListener { _, isChecked ->
            selectContainer.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        doneButton.setOnClickListener {
            drawables[radioGroup.checkedRadioButtonId]?.let {
                // drawables[radioGroup.checkedRadioButtonId] 가 null 이 아닐 때 실행
                animalImageView.setImageResource(it)
            } ?: let {
                // drawables[radioGroup.checkedRadioButtonId] 가 null 일 때 실행
                Toast.makeText(this, "애완동물을 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}