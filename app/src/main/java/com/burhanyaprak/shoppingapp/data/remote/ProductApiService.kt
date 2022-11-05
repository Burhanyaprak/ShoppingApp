package com.burhanyaprak.shoppingapp.data.remote

import com.burhanyaprak.shoppingapp.feature_product.domain.model.Product
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/categories")
    suspend fun getCategories(): List<String>
}