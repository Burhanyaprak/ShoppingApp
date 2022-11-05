package com.burhanyaprak.shoppingapp.feature_product.data.repository

import com.burhanyaprak.shoppingapp.data.remote.ProductApiService
import com.burhanyaprak.shoppingapp.feature_product.domain.model.Product
import com.burhanyaprak.shoppingapp.feature_product.domain.repository.ProductRepository
import com.burhanyaprak.shoppingapp.core.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductRepositoryImpl constructor(private val productApiService: ProductApiService) :
    ProductRepository {
    override suspend fun getProducts(): Flow<DataState<List<Product>>> = flow {
        emit(DataState.Loading())
        try {
            val response = productApiService.getProducts()
            emit(DataState.Success(response))
        } catch (e: IOException) {
            emit(DataState.Error(message = "Connection problem"))
        } catch (e: HttpException) {
            emit(DataState.Error(message = "There is problem! Try Again!"))
        }
    }
}