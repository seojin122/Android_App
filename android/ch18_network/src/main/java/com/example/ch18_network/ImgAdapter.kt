package com.example.ch18_network

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ch18_network.databinding.ItemImgMainBinding

class ImgViewHolder(val binding: ItemImgMainBinding): RecyclerView.ViewHolder(binding.root)
class ImgAdapter(val datas:MutableList<ItemModel>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImgViewHolder(ItemImgMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ImgViewHolder).binding
        val model = datas!![position]

        binding.itemTitle.text = model.title
        binding.itemDesc.text = model.description
        binding.itemTime.text = "${model.author} At ${model.publishedAt}"

        Glide.with(holder.binding.itemImage)
            .load(model.urlToImage)
            .into(binding.itemImage)
    }
}