package com.burhanyaprak.shoppingapp.data.local.dao

import androidx.room.*
import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    suspend fun getCartProducts(): List<ProductLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: ProductLocal)

    @Query("DELETE FROM product_table")
    suspend fun clearBasket()
}