package com.example.prokir.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prokir.database.AppDao
import com.example.prokir.database.AppDatabase
import com.example.prokir.database.Product
import com.example.prokir.databinding.FragmentProductsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.prokir.Communicator
import com.example.prokir.FragmentOrder
import com.example.prokir.R
import com.example.prokir.ui.transaction.helper.ProductsAdapter

class FragmentProducts : Fragment(R.layout.fragment_products), ProductsAdapter.OnItemClickListener
{
    private lateinit var dao: AppDao
    private lateinit var binding: FragmentProductsBinding
    private lateinit var recyclerView: RecyclerView
    private  lateinit var adapter: ProductsAdapter
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductsBinding.inflate(layoutInflater)
        communicator = activity as Communicator
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao = AppDatabase.getInstance(requireContext()).getDao()
        recyclerView = view.findViewById(R.id.products)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

            GlobalScope.launch {
                binding.products.layoutManager = LinearLayoutManager(parentFragment?.context)
                adapter = ProductsAdapter(dao.getAllProducts2(),this@FragmentProducts)
                activity?.runOnUiThread {
                    recyclerView.adapter = adapter
                }
        }

        view.findViewById<ImageView>(R.id.backbutton).setOnClickListener {
            val fragThree = FragmentOrder()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.main_frag, fragThree)
                commit()
            }
        }

    }

    override fun onItemClick(position: Int) {
        var clickedProduct: Product? = null
        GlobalScope.launch {
            clickedProduct = dao.getAllProducts2()[position]
            clickedProduct?.product_id?.let { communicator.passData(it) }
        }
        adapter.notifyItemChanged(position)
    }
}