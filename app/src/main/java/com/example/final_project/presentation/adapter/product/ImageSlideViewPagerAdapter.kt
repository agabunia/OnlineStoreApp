package com.example.final_project.presentation.adapter.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.ProductImageLayoutBinding
import com.example.final_project.presentation.extention.loadImage

class ImageSlideViewPagerAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ImageSlideViewPagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ImageViewHolder(ProductImageLayoutBinding.inflate(inflate, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    inner class ImageViewHolder(private val binding: ProductImageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.ivImages.loadImage(url = url)
        }
    }
}