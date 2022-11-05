package com.burhanyaprak.shoppingapp.feature_product.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burhanyaprak.shoppingapp.feature_product.domain.use_case.GetProductsUseCase
import com.burhanyaprak.shoppingapp.core.util.DataState
import com.burhanyaprak.shoppingapp.core.util.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) :
    ViewModel() {
    private val _productsState = MutableStateFlow(ProductsState())
    val productsState get() = _productsState.asStateFlow()

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase().collectLatest { result ->
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
}