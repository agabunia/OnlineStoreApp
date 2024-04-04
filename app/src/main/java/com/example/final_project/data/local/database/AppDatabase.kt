package com.example.final_project.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.final_project.data.local.dao.ProductDao
import com.example.final_project.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}