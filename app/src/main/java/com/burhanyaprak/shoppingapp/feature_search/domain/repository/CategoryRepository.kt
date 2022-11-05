package com.burhanyaprak.shoppingapp.feature_search.domain.repository

import com.burhanyaprak.shoppingapp.core.util.DataState
import kotlinx.coroutines.flow.Flow


interface CategoryRepository {
    suspend fun getCategories(): Flow<DataState<List<String>>>
}