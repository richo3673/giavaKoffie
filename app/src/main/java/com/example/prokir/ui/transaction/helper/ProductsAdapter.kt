package com.example.prokir.ui.transaction.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prokir.R
import com.example.prokir.database.AppDao
import com.example.prokir.database.AppDatabase
import com.example.prokir.database.Product

class ProductsAdapter(private val dataSet: List<Product>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    private lateinit var dao: AppDao

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
//        context
        dao = AppDatabase.getInstance(viewGroup.context).getDao()
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.dropdown_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply{

            findViewById<TextView>(R.id.productname).text = dataSet[position].namaProduk
            findViewById<TextView>(R.id.productPrice).text = dataSet[position].harga.toString()
            findViewById<TextView>(R.id.idnumber).text = dataSet[position].product_id.toString()

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val card: LinearLayout  = view.findViewById(R.id.card)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}