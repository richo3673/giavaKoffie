package com.example.prokir.ui.transaction.helper


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prokir.R
import com.example.prokir.database.AppDao
import com.example.prokir.database.AppDatabase
import com.example.prokir.database.Order
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TransactionAdapter(private val dataSet: List<Order>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    private lateinit var dao: AppDao
    private lateinit var namaProduk : String

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
//        context
        dao = AppDatabase.getInstance(viewGroup.context).getDao()
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_main, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply{
            GlobalScope.launch {
                findViewById<TextView>(R.id.nama).text = dataSet[position] .cust_id?.let {
                    dao.searchCustomerById(
                        it
                    ).nama
                }
                val tanggalFix = dataSet[position].tanggalPembelian?.split(" ")
                findViewById<TextView>(R.id.jam).text = tanggalFix?.get(0)
                findViewById<TextView>(R.id.duit).text = dataSet[position].subtotal.toString()
                val namaProduk =
                    dataSet[position].order_id?.let { dao.searchOrderItemsById(it) }?.get(0)?.product_id?.let {
                        dao.searchProductById(it)
                            .namaProduk.toString()
                    }
                val jmlp = dataSet[position].order_id?.let { dao.searchOrderItemsById(it) }?.get(0)?.kuantitas
//                findViewById<TextView>(R.id.jmlProduk).text = "Produk "+dataSet[position].order_id?.let {
//                    dao.searchOrderItemsById(
//                        it
//                    ).size.toString()
//                }
                (context as Activity).runOnUiThread {
                    findViewById<TextView>(R.id.jmlProduk).text = "$namaProduk (x$jmlp)"

                }
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}