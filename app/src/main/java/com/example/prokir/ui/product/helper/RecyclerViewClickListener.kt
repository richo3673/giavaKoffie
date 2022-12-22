package com.example.prokir.ui.product.helper

import android.view.View
import com.example.prokir.database.Product

interface RecyclerViewClickListener {
    fun onItemClicked(view: View, product: Product)
}