package com.example.final_project.presentation.adapter.common_product_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.databinding.ProductLayoutBinding
import com.example.final_project.presentation.extention.loadImage
import com.example.final_project.presentation.model.common_product_list.ProductCommonDetailed
import com.example.final_project.presentation.model.common_product_list.Products
import com.example.final_project.presentation.model.product.ProductDetailed

class ProductRecyclerAdapter :
    ListAdapter<ProductCommonDetailed, ProductRecyclerAdapter.ProductViewHolder>(ProductDiffUtil()) {

    class ProductDiffUtil : DiffUtil.ItemCallback<ProductCommonDetailed>() {
        override fun areItemsTheSame(
            oldItem: ProductCommonDetailed,
            newItem: ProductCommonDetailed
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductCommonDetailed,
            newItem: ProductCommonDetailed
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ProductViewHolder(ProductLayoutBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind()
    }

    var onItemClick: ((Int) -> Unit)? = null
    var saveProductClick: ((ProductCommonDetailed) -> Unit)? = null

    inner class ProductViewHolder(private val binding: ProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var product: ProductCommonDetailed

        fun bind() {
            product = currentList[adapterPosition]

            binding.apply {
                sivProductImage.loadImage(product.thumbnail)
                tvProductTitle.text = product.title
                tvProductPrice.text = product.price.toString()
                setListener()
            }
        }

        private fun setListener() {
            binding.apply {
                layoutProduct.setOnClickListener {
                    onItemClick?.invoke(product.id)
                }
                btnAdd.setOnClickListener {
                    if (product.isAdded) {
                        btnAdd.setImageResource(R.drawable.add_button_icon)
                    } else {
                        btnAdd.setImageResource(R.drawable.added_button_icon)
                    }
                    product.isAdded = !product.isAdded
                    saveProductClick?.invoke(product)
                }
            }
        }
    }

}