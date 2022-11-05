package com.burhanyaprak.shoppingapp.feature_product.domain.use_case

import com.burhanyaprak.shoppingapp.feature_product.domain.model.Product
import com.burhanyaprak.shoppingapp.feature_product.domain.repository.ProductRepository
import com.burhanyaprak.shoppingapp.core.util.DataState
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val productsRepository: ProductRepository
) {
    suspend operator fun invoke(): Flow<DataState<List<Product>>> {
        return productsRepository.getProducts()
    }
}