package com.example.prokir.database

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    val product_id: Int?,
    @ColumnInfo(name = "namaProduk") val namaProduk: String?,
    @ColumnInfo(name = "harga") val harga: Int?,
    @ColumnInfo(name = "stok") val stok: Int?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "imagePath") val imagePath: Bitmap?

)