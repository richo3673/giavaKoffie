package com.example.prokir.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = Order::class,
        childColumns = ["order_id"],
        parentColumns = ["order_id"],
        onDelete = CASCADE
    ),
    ForeignKey(
        entity = Product::class,
        childColumns = ["product_id"],
        parentColumns = ["product_id"],
        onDelete = CASCADE
    )
])
data class OrderItems (
    @ColumnInfo(name = "order_id") val order_id: Int?,
    @ColumnInfo(name = "product_id") val product_id: Int?,
    @ColumnInfo(name = "kuantitas") val kuantitas: Int?,
    @ColumnInfo(name = "totalHarga") val totalHarga: Int?,
){
    @PrimaryKey(autoGenerate = true)
    var orderItems_id: Int? = null
}