package com.example.final_project.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.CategoryWrapperLayoutBinding
import com.example.final_project.presentation.adapter.common_product_adapter.ProductRecyclerAdapter
import com.example.final_project.presentation.model.common_product_list.ProductCommonDetailed
import com.example.final_project.presentation.model.home.HomeMainModel

class CategoryWrapperRecyclerAdapter :
    ListAdapter<HomeMainModel.CategoryProductModel, CategoryWrapperRecyclerAdapter.CategoryWrapperViewHolder>(
        CategoryDiffUtil()
    ) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryWrapperViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return CategoryWrapperViewHolder(CategoryWrapperLayoutBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryWrapperViewHolder, position: Int) {
        holder.bind()
    }

    var onWrapperItemClick: ((Int) -> Unit)? = null
    var onWrapperSaveProductClick: ((ProductCommonDetailed) -> Unit)? = null

    inner class CategoryWrapperViewHolder(private val binding: CategoryWrapperLayoutBinding) :
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
                productRecyclerAdapter.submitList(category.productList)
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
