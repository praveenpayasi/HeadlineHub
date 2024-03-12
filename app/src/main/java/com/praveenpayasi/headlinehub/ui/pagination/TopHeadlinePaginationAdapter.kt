package com.praveenpayasi.headlinehub.ui.pagination


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.praveenpayasi.headlinehub.data.model.topheadlines.ApiTopHeadlines
import com.praveenpayasi.headlinehub.databinding.ItemTopHeadlineBinding
import com.praveenpayasi.headlinehub.ui.utils.ItemClickListener

class TopHeadlinePaginationAdapter :
    PagingDataAdapter<ApiTopHeadlines, TopHeadlinePaginationAdapter.DataViewHolder>(UIMODEL_COMPARATOR) {

    lateinit var itemClickListener: ItemClickListener<Any>

    class DataViewHolder(private val binding: ItemTopHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ApiTopHeadlines, itemClickListener: ItemClickListener<Any>) {
            binding.txtTitle.text = article.title
            binding.txtDescription.text = article.description
            binding.txtSource.text = article.apiSource.name

            Glide.with(binding.imgBanner.context).load(article.imageUrl)
                .into(binding.imgBanner)

            itemView.setOnClickListener {
                itemClickListener(bindingAdapterPosition, article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        ItemTopHeadlineBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val article = getItem(position)
        article?.let {
            holder.bind(it, itemClickListener)
        }

    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<ApiTopHeadlines>() {
            override fun areItemsTheSame(oldItem: ApiTopHeadlines, newItem: ApiTopHeadlines): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: ApiTopHeadlines, newItem: ApiTopHeadlines): Boolean {
                return oldItem == newItem
            }

        }
    }

}