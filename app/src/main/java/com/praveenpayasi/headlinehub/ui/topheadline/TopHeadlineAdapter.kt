package com.praveenpayasi.headlinehub.ui.topheadline

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.praveenpayasi.headlinehub.data.model.topheadlines.Article
import com.praveenpayasi.headlinehub.databinding.ItemTopHeadlineBinding

class TopHeadlineAdapter (private val articleList: ArrayList<Article>
): RecyclerView.Adapter<TopHeadlineAdapter.HeadlineViewHolder>() {

    class HeadlineViewHolder(private val binding: ItemTopHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article){
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
        return HeadlineViewHolder(ItemTopHeadlineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) =
        holder.bind(articleList[position])


    fun addArticles(list: List<Article>) {
        articleList.addAll(list)
    }

}