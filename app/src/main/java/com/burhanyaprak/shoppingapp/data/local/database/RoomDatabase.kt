package com.burhanyaprak.shoppingapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.burhanyaprak.shoppingapp.data.local.dao.ProductDao
import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal

@Database(entities = [ProductLocal::class], version = 1, exportSchema = true)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase? = null

        fun getDatabase(context: Context): RoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabase::class.java,
                    "product_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}