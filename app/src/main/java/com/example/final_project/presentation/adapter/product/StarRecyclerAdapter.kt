package com.example.final_project.presentation.adapter.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.StarLayoutBinding

class StarRecyclerAdapter(private val stars: Int) :
    RecyclerView.Adapter<StarRecyclerAdapter.StarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return StarViewHolder(StarLayoutBinding.inflate(inflate, parent, false))
    }

    override fun getItemCount(): Int {
        return stars
    }

    override fun onBindViewHolder(holder: StarViewHolder, position: Int) {
    }

    inner class StarViewHolder(private val binding: StarLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}
}