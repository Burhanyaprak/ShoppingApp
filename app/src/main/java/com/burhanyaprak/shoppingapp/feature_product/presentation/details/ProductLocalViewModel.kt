package com.burhanyaprak.shoppingapp.feature_product.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal
import com.burhanyaprak.shoppingapp.feature_product.domain.use_case.GetProductLocalUseCase
import com.burhanyaprak.shoppingapp.core.util.DataState
import com.burhanyaprak.shoppingapp.core.util.ProductsStateLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductLocalViewModel @Inject constructor(
    private val getProductLocalUseCase: GetProductLocalUseCase
) :
    ViewModel() {
    private val _productsState = MutableStateFlow(ProductsStateLocal())
    val productsState get() = _productsState.asStateFlow()

    fun getProducts() {
        viewModelScope.launch {
            getProductLocalUseCase().collectLatest { result ->
                when (result) {
                    is DataState.Success -> {
                        _productsState.value = productsState.value.copy(
                            products = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is DataState.Loading -> {
                        _productsState.value = productsState.value.copy(
                            isLoading = true
                        )
                    }
                    is DataState.Error -> {
                        _productsState.value = productsState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    suspend fun addProduct(productLocal: ProductLocal) {
        getProductLocalUseCase(productLocal)
    }

    suspend fun clearBasket() {
        getProductLocalUseCase.clearBasket()
    }
}