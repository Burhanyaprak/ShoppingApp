package com.burhanyaprak.shoppingapp.feature_product.domain.repository

import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal
import com.burhanyaprak.shoppingapp.core.util.DataState
import kotlinx.coroutines.flow.Flow

interface ProductLocalRepository {
    suspend fun getProducts(): Flow<DataState<List<ProductLocal>>>
    suspend fun addProduct(productLocal: ProductLocal)
    suspend fun clearBasket()
}