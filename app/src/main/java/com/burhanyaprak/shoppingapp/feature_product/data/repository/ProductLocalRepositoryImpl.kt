package com.burhanyaprak.shoppingapp.feature_product.data.repository

import com.burhanyaprak.shoppingapp.data.local.dao.ProductDao
import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal
import com.burhanyaprak.shoppingapp.feature_product.domain.repository.ProductLocalRepository
import com.burhanyaprak.shoppingapp.core.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductLocalRepositoryImpl constructor(private val productDao: ProductDao) :
    ProductLocalRepository {
    override suspend fun getProducts(): Flow<DataState<List<ProductLocal>>> = flow {
        emit(DataState.Loading())
        try {
            val response = productDao.getCartProducts()
            //emit(DataState.Success(response.map { it }))
            emit(DataState.Success(response))
        } catch (e: IOException) {
            emit(DataState.Error(message = "Connection problem"))
        } catch (e: HttpException) {
            emit(DataState.Error(message = "There is problem! Try Again!"))
        }
    }

    override suspend fun addProduct(productLocal: ProductLocal) {
        productDao.addProduct(productLocal)
    }

    override suspend fun clearBasket() {
        productDao.clearBasket()
    }

}