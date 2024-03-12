package com.praveenpayasi.headlinehub.ui.news

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import com.praveenpayasi.headlinehub.databinding.ItemTopHeadlineBinding

class NewsListAdapter (private val articleList: ArrayList<TopHeadlineEntity>
): RecyclerView.Adapter<NewsListAdapter.HeadlineViewHolder>() {

    class HeadlineViewHolder(private val binding: ItemTopHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: TopHeadlineEntity){
            binding.txtTitle.text=article.title
            binding.txtDescription.text=article.description
            binding.txtSource.text=article.source.name
            Glide.with(binding.imgBanner.context)
                .load(article.imageUrl)
                .into(binding.imgBanner)
            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(article.url))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        return HeadlineViewHolder(
            ItemTopHeadlineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) =
        holder.bind(articleList[position])


    fun addArticles(list: List<TopHeadlineEntity>) {
        articleList.addAll(list)
    }

}