package com.example.prokir.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customer (
    @ColumnInfo(name = "nama") val nama: String?,
    @ColumnInfo(name = "alamat") val alamat: String?,
    @ColumnInfo(name = "noTelp") val noTelp: String?
){
    @PrimaryKey(autoGenerate = true)
    var cust_id: Int? = null
}