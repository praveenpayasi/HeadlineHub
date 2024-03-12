package com.praveenpayasi.headlinehub.ui.sources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.praveenpayasi.headlinehub.data.local.entity.NewsSources
import com.praveenpayasi.headlinehub.databinding.ItemNewsSourcesBinding
import com.praveenpayasi.headlinehub.ui.utils.ItemClickListener

class NewsSourceAdapter(private val sourceList: ArrayList<NewsSources>):
    RecyclerView.Adapter<NewsSourceAdapter.SourceViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<Any>

    class SourceViewHolder(private val binding: ItemNewsSourcesBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(source: NewsSources, itemClickListener: ItemClickListener<Any>){
            binding.btnSource.text = source.name

            binding.btnSource.setOnClickListener {
                itemClickListener(bindingAdapterPosition, source)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        return SourceViewHolder(ItemNewsSourcesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = sourceList.size

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        holder.bind(sourceList[position], itemClickListener)
    }

    fun addSources(list: List<NewsSources>) {
        sourceList.addAll(list)
    }

}