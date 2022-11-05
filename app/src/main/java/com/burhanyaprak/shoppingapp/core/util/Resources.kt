package com.burhanyaprak.shoppingapp.core.util

import com.burhanyaprak.shoppingapp.feature_product.domain.model.Product
import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal

data class ProductsState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ProductsStateLocal(
    val products: List<ProductLocal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)