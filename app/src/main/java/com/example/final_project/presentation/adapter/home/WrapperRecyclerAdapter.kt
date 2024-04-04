package com.example.final_project.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.CategoryWrapperLayoutBinding
import com.example.final_project.presentation.adapter.common_product_adapter.ProductRecyclerAdapter
import com.example.final_project.presentation.model.common_product_list.Products
import com.example.final_project.presentation.model.home.CategoryWrapperList
import com.example.final_project.presentation.model.home.HomeMainModel

class WrapperRecyclerAdapter :
    ListAdapter<HomeMainModel.CategoryProductModel, WrapperRecyclerAdapter.WrapperViewHolder>(CategoryDiffUtil()) {

    class CategoryDiffUtil : DiffUtil.ItemCallback<HomeMainModel.CategoryProductModel>() {
        override fun areItemsTheSame(
            oldItem: HomeMainModel.CategoryProductModel,
            newItem: HomeMainModel.CategoryProductModel
        ): Boolean {
            return oldItem.categoryName == newItem.categoryName
        }

        override fun areContentsTheSame(
            oldItem: HomeMainModel.CategoryProductModel,
            newItem: HomeMainModel.CategoryProductModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrapperViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return WrapperViewHolder(CategoryWrapperLayoutBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: WrapperViewHolder, position: Int) {
        holder.bind()
    }

    var onWrapperItemClick: ((Int) -> Unit)? = null
    var onWrapperSaveProductClick: ((Products.ProductDetailed) -> Unit)? = null

    inner class WrapperViewHolder(private val binding: CategoryWrapperLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var category: HomeMainModel.CategoryProductModel
        private val productRecyclerAdapter = ProductRecyclerAdapter()

        init {
            binding.rvProductList.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = productRecyclerAdapter
            }
        }

        fun bind() {
            category = currentList[adapterPosition]

            binding.apply {
                tvCategoryTitle.text = category.categoryName
                productRecyclerAdapter.submitList(category.productList.products)
                setListeners()
            }
        }

        private fun setListeners() {
            binding.apply {
                productRecyclerAdapter.onItemClick = {
                    onWrapperItemClick?.invoke(it)
                }
                productRecyclerAdapter.saveProductClick = {
                    onWrapperSaveProductClick?.invoke(it)
                }
            }
        }

    }
}