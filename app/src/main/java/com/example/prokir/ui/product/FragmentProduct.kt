package com.example.prokir.ui.product

import com.example.prokir.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.prokir.database.AppDatabase
import com.example.prokir.database.Product
import androidx.lifecycle.Observer
import com.example.prokir.databinding.FragmentProductBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.prokir.ui.product.helper.ProductDataAdapter
import com.example.prokir.ui.product.helper.RecyclerViewClickListener
import com.example.prokir.ui.product.viewmodel.ProductViewModel


class FragmentProduct : Fragment(R.layout.fragment_product), View.OnClickListener,
    RecyclerViewClickListener {
    private lateinit var binding: FragmentProductBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductDataAdapter
    private var viewModel: ProductViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        val dataBaseInstance = AppDatabase.getInstance(requireContext())
        viewModel?.setInstanceOfDB(dataBaseInstance)
        recyclerView = binding.product1
        initViews()
        setListeners()
        observerViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener(this)
        binding.searchSubmit.setOnClickListener(this)
    }

    private fun initViews() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager
        adapter = ProductDataAdapter()
        recyclerView.adapter = adapter
    }

    private fun setListeners() {
        viewModel?.getProductData(binding.search.editText?.text.toString())
        adapter.listener = this@FragmentProduct
    }

    private fun observerViewModel() {
        viewModel?.productList?.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                handleData(it)
            } else {
                handleZeroCase()
            }
        })

    }

    private fun handleData(data: List<Product>) {
        recyclerView.visibility = View.VISIBLE
        adapter.setData(data)
    }

    private fun handleZeroCase() {
        recyclerView.visibility = View.GONE
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.addButton -> {
                val fragAddProduct = FragmentAddProduct()
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_frag, fragAddProduct)
                    commit()
                }
            }
            R.id.search_submit -> {
                setListeners()
            }
        }
    }

    override fun onItemClicked(view: View, product: Product) {
        val fragDetail = FragmentProductDetail()
        val bundle = Bundle()
        product.product_id?.let { bundle.putInt("product_id", it) }
        fragDetail.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_frag, fragDetail)
            commit()
        }
    }
}