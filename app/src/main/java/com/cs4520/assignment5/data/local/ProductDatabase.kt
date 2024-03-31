package com.cs4520.assignment5.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cs4520.assignment5.data.models.ProductDto

@Database(entities = [ProductDto::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao() : ProductDao

    companion object {
        private const val DB_NAME = "product_table"

        @Volatile
        private var INST: ProductDatabase? = null

        //similar to product client getInstance
        fun getInstance(context: Context): ProductDatabase {
            synchronized(this) {
                var instance = INST

                if (instance == null) {
                    instance = Room.databaseBuilder(context, ProductDatabase::class.java, DB_NAME)
                        .build()
                    INST = instance
                }
                return instance
            }
        }
    }
}