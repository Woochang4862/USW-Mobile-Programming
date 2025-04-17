package com.example.class0417

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.class0417.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.goButton.setOnClickListener {
            val url = binding.urlEditText.text.toString()
            binding.webView.loadUrl(url)
        }

        binding.previousButton.setOnClickListener {
            binding.webView.goBack()
        }

        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.settings.apply {
            javaScriptEnabled= true // 자바스크립트 사용여부
            setSupportMultipleWindows(true) // 새창 띄우기 허용여부
            javaScriptCanOpenWindowsAutomatically= true // 자바스크립트가 window.open()을 사용할 수 있도록 설정
            loadWithOverviewMode= true // html의 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
            useWideViewPort= true // 화면 사이즈 맞추기 허용여부
            setSupportZoom(false) // 화면 줌 허용여부
            domStorageEnabled= true // DOM(html 인식) 저장소 허용여부

            // 파일 허용
            allowContentAccess= true
            allowFileAccess= true
            mixedContentMode= WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            loadsImagesAutomatically= true
        }
    }
}

class MyWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url ?: "")
        return super.shouldOverrideUrlLoading(view, url)
    }
}