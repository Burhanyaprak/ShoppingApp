package com.burhanyaprak.shoppingapp.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burhanyaprak.shoppingapp.core.util.CategoriesState
import com.burhanyaprak.shoppingapp.core.util.DataState
import com.burhanyaprak.shoppingapp.feature_search.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) :
    ViewModel() {
    private val _productsState = MutableStateFlow(CategoriesState())
    val productsState get() = _productsState.asStateFlow()

    fun getCategories() {
        viewModelScope.launch {
            categoryRepository.getCategories().collectLatest { result ->
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