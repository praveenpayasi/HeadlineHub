package com.praveenpayasi.headlinehub.ui.pagination

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.praveenpayasi.headlinehub.HeadlineHubApplication
import com.praveenpayasi.headlinehub.data.model.topheadlines.ApiTopHeadlines
import com.praveenpayasi.headlinehub.databinding.ActivityTopHeadlinePaginationBinding
import com.praveenpayasi.headlinehub.di.component.DaggerActivityComponent
import com.praveenpayasi.headlinehub.di.module.ActivityModule
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlinePaginationActivity : AppCompatActivity() {

    @Inject
    lateinit var paginationTopHeadlineViewModel: TopHeadlinePaginationViewModel

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlinePaginationAdapter

    private lateinit var binding: ActivityTopHeadlinePaginationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlinePaginationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = topHeadlineAdapter
        }
        topHeadlineAdapter.itemClickListener = { _, data ->
            val article = data as ApiTopHeadlines
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this@TopHeadlinePaginationActivity, Uri.parse(article.url))
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                paginationTopHeadlineViewModel.topHeadlineUiState.collect { pagingData ->
                    binding.progressBar.visibility = View.GONE
                    binding.includeLayout.errorLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    topHeadlineAdapter.submitData(pagingData)
                }
            }
        }

    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as HeadlineHubApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build(). inject(this)
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, TopHeadlinePaginationActivity::class.java)
        }
    }
}