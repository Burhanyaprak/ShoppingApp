package com.burhanyaprak.shoppingapp.feature_product.domain.use_case

import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal
import com.burhanyaprak.shoppingapp.feature_product.domain.repository.ProductLocalRepository
import com.burhanyaprak.shoppingapp.core.util.DataState
import kotlinx.coroutines.flow.Flow

class GetProductLocalUseCase(
    private val productLocalRepository: ProductLocalRepository
) {
    suspend operator fun invoke(): Flow<DataState<List<ProductLocal>>> {
        return productLocalRepository.getProducts()
    }

    suspend operator fun invoke(productLocal: ProductLocal) {
        return productLocalRepository.addProduct(productLocal)
    }

    suspend fun clearBasket() {
        return productLocalRepository.clearBasket()
    }
}