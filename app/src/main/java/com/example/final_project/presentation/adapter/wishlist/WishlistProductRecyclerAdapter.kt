package com.example.final_project.presentation.adapter.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.WishlistProductLayoutBinding
import com.example.final_project.presentation.extention.loadImage
import com.example.final_project.presentation.model.wishlist.WishlistProduct

class WishlistProductRecyclerAdapter :
    ListAdapter<WishlistProduct, WishlistProductRecyclerAdapter.WishlistProductViewHolder>(
        ProductDiffUtil()
    ) {

    class ProductDiffUtil : DiffUtil.ItemCallback<WishlistProduct>() {
        override fun areItemsTheSame(oldItem: WishlistProduct, newItem: WishlistProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WishlistProduct,
            newItem: WishlistProduct
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistProductViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return WishlistProductViewHolder(
            WishlistProductLayoutBinding.inflate(
                inflate,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WishlistProductViewHolder, position: Int) {
        holder.bind()
    }

    var onMinusClick: ((Int) -> Unit)? = null
    var onPlusClick: ((Int) -> Unit)? = null
    var onDeleteClick: ((WishlistProduct) -> Unit)? = null

    inner class WishlistProductViewHolder(private val binding: WishlistProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var product: WishlistProduct

        fun bind() {
            product = currentList[adapterPosition]

            binding.apply {
                sivProductImage.loadImage(product.thumbnail)
                tvProductTitle.text = product.title
                tvProductDescription.text = product.description
                tvProductPrice.text = product.price.toString()
                tvTotalProductPrice.text = product.sumPrice.toString()
                tvQuantity.text = product.quantity.toString()
                btnMinus.setOnClickListener {
                    onMinusClick?.invoke(product.id)
                }
                btnPlus.setOnClickListener {
                    onPlusClick?.invoke(product.id)
                }
                btnDelete.setOnClickListener {
                    onDeleteClick?.invoke(product)
                }
            }
        }
    }

}