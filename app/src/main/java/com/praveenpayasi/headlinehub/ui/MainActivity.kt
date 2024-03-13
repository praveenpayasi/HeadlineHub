package com.praveenpayasi.headlinehub.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.praveenpayasi.headlinehub.databinding.ActivityMainBinding
import com.praveenpayasi.headlinehub.ui.country.CountryListActivity
import com.praveenpayasi.headlinehub.ui.language.LanguageListActivity
import com.praveenpayasi.headlinehub.ui.offline.OfflineTopHeadlineActivity
import com.praveenpayasi.headlinehub.ui.pagination.TopHeadlinePaginationActivity
import com.praveenpayasi.headlinehub.ui.search.SearchActivity
import com.praveenpayasi.headlinehub.ui.sources.NewsSourcesActivity
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

        binding.btnPaginationTopHeadlines.setOnClickListener {
            startActivity(Intent(TopHeadlinePaginationActivity.getStartIntent(this@MainActivity)))
        }

        binding.btnNewsSources.setOnClickListener {
            startActivity(Intent(NewsSourcesActivity.getStartIntent(this@MainActivity)))
        }

        binding.btnCoutriess.setOnClickListener {
            startActivity(Intent(CountryListActivity.getStartIntent(this@MainActivity)))
        }

        binding.btnLanguages.setOnClickListener {
            startActivity(Intent(LanguageListActivity.getStartIntent(this@MainActivity)))
        }

        binding.btnSearch.setOnClickListener {
            startActivity(Intent(SearchActivity.getStartIntent(this@MainActivity)))
        }

    }

}