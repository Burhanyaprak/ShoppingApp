package com.burhanyaprak.shoppingapp.feature_search.data.repository

import com.burhanyaprak.shoppingapp.core.util.DataState
import com.burhanyaprak.shoppingapp.data.remote.ProductApiService
import com.burhanyaprak.shoppingapp.feature_search.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class CategoryRepositoryImpl constructor(private val productApiService: ProductApiService) :
    CategoryRepository {
    override suspend fun getCategories(): Flow<DataState<List<String>>> = flow {
        emit(DataState.Loading())
        try {
            val response = productApiService.getCategories()
            emit(DataState.Success(response))
        } catch (e: IOException) {
            emit(DataState.Error(message = "Connection problem"))
        } catch (e: HttpException) {
            emit(DataState.Error(message = "There is problem! Try Again!"))
        }
    }
}