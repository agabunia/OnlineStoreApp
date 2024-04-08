package com.example.final_project.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.viewpager2.widget.ViewPager2
import com.example.final_project.databinding.BannerImageLayoutBinding
import com.example.final_project.databinding.CategoryWrapperLayoutBinding
import com.example.final_project.databinding.FragmentHomeCategoryWrapperBinding
import com.example.final_project.databinding.FragmentHomeViewPagerBinding
import com.example.final_project.databinding.ProductImageLayoutBinding
import com.example.final_project.presentation.adapter.common_product_adapter.ProductRecyclerAdapter
import com.example.final_project.presentation.adapter.product.ImageSlideViewPagerAdapter
import com.example.final_project.presentation.extention.loadImage
import com.example.final_project.presentation.model.common_product_list.ProductCommonDetailed
import com.example.final_project.presentation.model.home.HomeMainModel
import com.example.final_project.presentation.model.home.HomeWrapperModel

class HomeMainRecyclerAdapter(private val list: List<HomeWrapperModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val BANNER_VIEW_TYPE = 1
        private const val PROMOTION_IMAGES_VIEW_TYPE = 2
        private const val CATEGORY_WRAPPER_VIEW_TYPE = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return when (viewType) {
            BANNER_VIEW_TYPE -> {
                val binding = BannerImageLayoutBinding.inflate(inflate, parent, false)
                BannerViewHolder(binding)
            }

            PROMOTION_IMAGES_VIEW_TYPE -> {
                val binding = FragmentHomeViewPagerBinding.inflate(inflate, parent, false)
                PromotionImagesViewHolder(binding)
            }

            CATEGORY_WRAPPER_VIEW_TYPE -> {
                val binding = FragmentHomeCategoryWrapperBinding.inflate(inflate, parent, false)
                WrapperViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is HomeWrapperModel.BannerImage -> BANNER_VIEW_TYPE
            is HomeWrapperModel.PromotionImages -> PROMOTION_IMAGES_VIEW_TYPE
            is HomeWrapperModel.CategoryWrapperList -> CATEGORY_WRAPPER_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = list[position]) {
            is HomeWrapperModel.BannerImage -> (holder as BannerViewHolder).bind(item)
            is HomeWrapperModel.PromotionImages -> (holder as PromotionImagesViewHolder).bind(item)
            is HomeWrapperModel.CategoryWrapperList -> (holder as WrapperViewHolder).bind(item.categoryWrapperList)
        }
    }

    inner class BannerViewHolder(private val binding: BannerImageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: HomeWrapperModel.BannerImage) {
            binding.ivImages.loadImage(banner.bannerImage)
        }
    }

    inner class PromotionImagesViewHolder(private val binding: FragmentHomeViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var imageSlideViewPagerAdapter: ImageSlideViewPagerAdapter
        fun bind(images: HomeWrapperModel.PromotionImages) {
            setViewPagerAdapter(images.promotionImages)
        }

        private fun setViewPagerAdapter(images: List<String>) {
            imageSlideViewPagerAdapter = ImageSlideViewPagerAdapter(images)
            binding.apply {
                viewPager.adapter = imageSlideViewPagerAdapter
                viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
        }
    }

    var onWrapperItemClick: ((Int) -> Unit)? = null
    var onWrapperSaveProductClick: ((ProductCommonDetailed) -> Unit)? = null

    inner class WrapperViewHolder(private val binding: FragmentHomeCategoryWrapperBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val categoryWrapperAdapter = CategoryWrapperRecyclerAdapter()

        init {
            binding.rvCategoryWrapper.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = categoryWrapperAdapter
            }
        }

        fun bind(categoryWrapper: List<HomeMainModel.CategoryProductModel>) {

            binding.apply {
                categoryWrapperAdapter.submitList(categoryWrapper)
                setListeners()
            }
        }

        private fun setListeners() {
            binding.apply {
                categoryWrapperAdapter.onWrapperItemClick = {
                    onWrapperItemClick?.invoke(it)
                }
                categoryWrapperAdapter.onWrapperSaveProductClick = {
                    onWrapperSaveProductClick?.invoke(it)
                }
            }
        }

    }
}
