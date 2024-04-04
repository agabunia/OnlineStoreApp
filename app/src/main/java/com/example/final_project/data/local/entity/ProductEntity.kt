package com.example.final_project.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "quantity") var quantity: Int = 1,
    @ColumnInfo(name = "sum_price") var sumPrice: Int = price * quantity,
    @ColumnInfo(name = "thumbnail") val thumbnail: String
)
