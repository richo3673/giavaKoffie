package com.example.prokir.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = Customer::class,
        childColumns = ["cust_id"],
        parentColumns = ["cust_id"],
        onDelete = CASCADE
    )
])
data class Order (
    @ColumnInfo(name = "cust_id") val cust_id: Int?,
    @ColumnInfo(name = "tanggalPembelian") val tanggalPembelian: String?,
    @ColumnInfo(name = "ongkir") val ongkir: Int?,
    @ColumnInfo(name = "subtotal") val subtotal: Int?
){
    @PrimaryKey(autoGenerate = true)
    var order_id: Int? = null
}