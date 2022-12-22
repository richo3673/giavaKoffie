package com.example.prokir.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.prokir.R
import com.example.prokir.database.AppDatabase
import com.example.prokir.database.Product
import com.example.prokir.databinding.FragmentDetailProductBinding
import com.example.prokir.ui.product.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

class FragmentProductDetail : Fragment(R.layout.fragment_detail_product), View.OnClickListener {
    private lateinit var binding: FragmentDetailProductBinding
    private var viewModel: ProductViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        var dataBaseInstance = AppDatabase.getInstance(requireContext())
        viewModel?.setInstanceOfDB(dataBaseInstance)
        val product_id = arguments?.getInt("product_id")
        binding.floatingback.setOnClickListener(this)
        if (product_id != null) {
            showDetail(product_id)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.floatingback -> {
                back()
            }
            R.id.updateButton -> {
                val fragmentUpdate = FragmentUpdateProduct()
                val bundle = Bundle()
                val product_id = arguments?.getInt("product_id")
                if (product_id != null) {
                    bundle.putInt("id", product_id)
                }
                fragmentUpdate.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_frag, fragmentUpdate)
                    commit()
                }
            }
        }
    }

    private fun showDetail(product_id: Int) {
        viewModel?.getProductByID(product_id)
        viewModel?.product?.observe(viewLifecycleOwner, Observer {
            handleData(it)
        })
        binding.updateButton.setOnClickListener(this)
        binding.deleteButton.setOnClickListener{
            viewModel?.product?.observe(viewLifecycleOwner, Observer {
                deleteData(it)
            })
        }
    }

    private fun deleteData(product: Product) {
        viewModel?.deleteProduct(product)
        back()
        Toast.makeText(requireContext(), "Product deleted succesfully", Toast.LENGTH_SHORT).show()
    }

    private fun handleData(product: Product?) {
        if (product != null) {
            binding.detailImage.setImageBitmap(product.imagePath)
            binding.detailNamaProduk.setText(product.namaProduk.toString())
            binding.detailPrice.setText(product.harga.toString())
            binding.detailStok.setText(product.stok.toString())
            binding.detailDescription.setText(product.description.toString())

        }
    }

    private fun back(){
        val fragmentProduct = FragmentProduct()
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_frag, fragmentProduct)
            commit()
        }
    }
}