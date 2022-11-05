package com.burhanyaprak.shoppingapp.feature_product.domain.repository

import com.burhanyaprak.shoppingapp.feature_product.domain.model.Product
import com.burhanyaprak.shoppingapp.core.util.DataState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<DataState<List<Product>>>
}