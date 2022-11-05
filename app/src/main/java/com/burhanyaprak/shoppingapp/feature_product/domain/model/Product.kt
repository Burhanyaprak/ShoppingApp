package com.burhanyaprak.shoppingapp.feature_product.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.burhanyaprak.shoppingapp.feature_product.domain.model.Rating
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
) : Parcelable

@Parcelize
@Entity(tableName = "product_table")
data class ProductLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val image: String,
    val price: Double,
    var quantity: Int
) : Parcelable