package com.example.prokir.ui.product.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prokir.R
import com.example.prokir.database.Product

class ProductDataAdapter : RecyclerView.Adapter<ProductDataAdapter.DataViewHolder>() {
    private var productDataList = mutableListOf<Product>()
    var listener: RecyclerViewClickListener? = null

    fun setData(list: List<Product>) {
        productDataList.clear()
        productDataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        ) {
        }
    }

    override fun getItemCount(): Int {
        return productDataList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.setData(productDataList[position])
        holder.itemView.setOnClickListener {
            listener?.onItemClicked(it, productDataList[position])
        }
    }

    inner class DataViewHolder(
        itemView: View, val onDeleteClick: (Product) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun setData(product: Product) {

            itemView.apply {
                findViewById<TextView>(R.id.nama).text = product.namaProduk
                findViewById<TextView>(R.id.harga_produk).text = product.harga.toString()
                findViewById<TextView>(R.id.stok_produk).text = product.stok.toString()
                findViewById<ImageView>(R.id.imgCard).setImageBitmap(product.imagePath)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

}