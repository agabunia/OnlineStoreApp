package com.example.final_project.di

import android.content.Context
import androidx.room.Room
import com.example.final_project.data.common.HandleResponse
import com.example.final_project.data.local.dao.ProductDao
import com.example.final_project.data.local.database.AppDatabase
import com.example.final_project.data.remote.service.home.CategoryListService
import com.example.final_project.data.remote.service.home.HomeModelService
import com.example.final_project.data.remote.service.home.ProductByCategoryService
import com.example.final_project.data.remote.service.product.ProductDetailedService
import com.example.final_project.data.remote.service.search.ProductSearchService
import com.example.final_project.data.remote.service.search.ProductService
import com.google.firebase.auth.FirebaseAuth
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    private const val BASE_URL = "https://run.mocky.io/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authTokenFlow: Flow<String?>, loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()

        clientBuilder
            .addInterceptor { chain ->
                val authToken = runBlocking { authTokenFlow.first() }
                val newRequest = if (!authToken.isNullOrBlank()) {
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $authToken")
                        .addHeader("accept", "application/json")
                        .build()
                } else {
                    chain.request()
                }
                chain.proceed(newRequest)
            }
        clientBuilder.addInterceptor(loggingInterceptor)
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }

    @Singleton
    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Singleton
    @Provides
    fun provideProductSearchService(retrofit: Retrofit): ProductSearchService {
        return retrofit.create(ProductSearchService::class.java)
    }

    @Singleton
    @Provides
    fun provideProductDetailedService(retrofit: Retrofit): ProductDetailedService {
        return retrofit.create(ProductDetailedService::class.java)
    }

    @Singleton
    @Provides
    fun provideProductByCategoryService(retrofit: Retrofit): ProductByCategoryService {
        return retrofit.create(ProductByCategoryService::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoryListService(retrofit: Retrofit): CategoryListService {
        return retrofit.create(CategoryListService::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeModelService(retrofit: Retrofit): HomeModelService {
        return retrofit.create(HomeModelService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "products_database"
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }

}
