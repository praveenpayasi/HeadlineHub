package com.praveenpayasi.headlinehub.ui.topheadline

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import com.praveenpayasi.headlinehub.databinding.ItemTopHeadlineBinding

class TopHeadlineAdapter (private val topHeadlineEntityList: ArrayList<TopHeadlineEntity>
): RecyclerView.Adapter<TopHeadlineAdapter.HeadlineViewHolder>() {

    class HeadlineViewHolder(private val binding: ItemTopHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(topHeadlineEntity: TopHeadlineEntity){
            binding.txtTitle.text=topHeadlineEntity.title
            binding.txtDescription.text=topHeadlineEntity.description
            binding.txtSource.text=topHeadlineEntity.source.name
            Glide.with(binding.imgBanner.context)
                .load(topHeadlineEntity.imageUrl)
                .into(binding.imgBanner)
            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(topHeadlineEntity.url))
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

    override fun getItemCount(): Int = topHeadlineEntityList.size

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) =
        holder.bind(topHeadlineEntityList[position])


    fun addArticles(list: List<TopHeadlineEntity>) {
        topHeadlineEntityList.addAll(list)
    }

}