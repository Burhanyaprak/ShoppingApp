package com.burhanyaprak.shoppingapp.di

import android.content.Context
import androidx.room.Room
import com.burhanyaprak.shoppingapp.data.remote.ProductApiService
import com.burhanyaprak.shoppingapp.data.local.dao.ProductDao
import com.burhanyaprak.shoppingapp.data.local.database.RoomDatabase
import com.burhanyaprak.shoppingapp.feature_product.domain.repository.ProductLocalRepository
import com.burhanyaprak.shoppingapp.feature_product.data.repository.ProductLocalRepositoryImpl
import com.burhanyaprak.shoppingapp.feature_product.domain.repository.ProductRepository
import com.burhanyaprak.shoppingapp.feature_product.data.repository.ProductRepositoryImpl
import com.burhanyaprak.shoppingapp.feature_product.domain.use_case.GetProductLocalUseCase
import com.burhanyaprak.shoppingapp.feature_product.domain.use_case.GetProductsUseCase
import com.burhanyaprak.shoppingapp.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {

    @Provides
    @Singleton
    fun provideProductsApiService(): ProductApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }

    @Provides
    fun provideProductRepository(
        productsApiService: ProductApiService
    ): ProductRepository {
        return ProductRepositoryImpl(
            productsApiService
        )
    }

    @Provides
    fun provideProductLocalRepository(
        productDao: ProductDao
    ): ProductLocalRepository {
        return ProductLocalRepositoryImpl(
            productDao
        )
    }

    @Provides
    @Singleton
    fun provideGetProductsUseCase(productsRepository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(productsRepository)
    }

    @Provides
    @Singleton
    fun provideGetProductsLocalUseCase(productLocalRepository: ProductLocalRepository): GetProductLocalUseCase {
        return GetProductLocalUseCase(productLocalRepository)
    }

    @InstallIn(SingletonComponent::class)
    @Module
    class DatabaseModule {
        @Provides
        fun provideChannelDao(appDatabase: RoomDatabase): ProductDao {
            return appDatabase.productDao()
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RoomDatabase {
        return Room.databaseBuilder(
            appContext,
            RoomDatabase::class.java,
            "product_database"
        ).build()
    }
}