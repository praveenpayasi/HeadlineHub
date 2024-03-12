package com.praveenpayasi.headlinehub.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.praveenpayasi.headlinehub.databinding.ActivityMainBinding
import com.praveenpayasi.headlinehub.ui.offline.OfflineTopHeadlineActivity
import com.praveenpayasi.headlinehub.ui.topheadline.TopHeadlineActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTopHeadlines.setOnClickListener{
            startActivity(Intent(TopHeadlineActivity.getStartIntent(this@MainActivity)))
        }

        binding.btnOfflineTopHeadlines.setOnClickListener {
            startActivity(Intent(OfflineTopHeadlineActivity.getStartIntent(this@MainActivity)))
        }

    }

}