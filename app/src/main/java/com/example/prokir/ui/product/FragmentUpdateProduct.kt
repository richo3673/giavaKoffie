package com.example.prokir.ui.product

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.prokir.R
import com.example.prokir.database.AppDao
import com.example.prokir.database.AppDatabase
import com.example.prokir.database.Product
import com.example.prokir.databinding.FragmentAddProductBinding
import com.example.prokir.ui.product.viewmodel.ProductViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentUpdateProduct : Fragment(R.layout.fragment_add_product), View.OnClickListener {
    private lateinit var dao: AppDao
    private lateinit var binding: FragmentAddProductBinding
    private var viewModel: ProductViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        var dataBaseInstance = AppDatabase.getInstance(requireContext())
        viewModel?.setInstanceOfDB(dataBaseInstance)

        binding.title.setText("Update Product")
        binding.save.setText("UPDATE")
        binding.backbutton.setOnClickListener(this)
        binding.addimage.setOnClickListener(this)
        val product_id = arguments?.getInt("id")
        if (product_id != null) {
            viewModel?.getProductByID(product_id)
            viewModel?.product?.observe(viewLifecycleOwner, Observer {
                handleData(it)
            })
        }
    }

    private fun handleData(product: Product) {
        binding.productname.setText(product.namaProduk)
        binding.stock.setText(product.stok.toString())
        binding.price.setText(product.harga.toString())
        binding.description.setText(product.description.toString())
        binding.addimage.setImageBitmap(product.imagePath)
        binding.save.setOnClickListener {
            product.imagePath?.let { it1 -> updateProduct(it1) }
        }
    }

    private fun openGalleryForImage() {
        getAction.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    val getAction = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val contentResolver = this.activity?.contentResolver
            val inputStream = uri.let { contentResolver?.openInputStream(it) }
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            view?.findViewById<ImageView>(R.id.addimage)?.setImageBitmap(bitmap)
            binding.save.setOnClickListener {
                updateProduct(bitmap)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backbutton -> {
                val fragProduct = FragmentProduct()
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_frag, fragProduct)
                    commit()
                }
            }
            R.id.addimage -> {
                openGalleryForImage()
            }
        }
    }

    private fun updateProduct(bitmap: Bitmap) {
        val nama = binding.productname.text.toString()
        val stok = binding.stock.text.toString().toInt()
        val harga = binding.price.text.toString().toInt()
        val desc = binding.description.text.toString()

        GlobalScope.launch {
            dao = AppDatabase.getInstance(requireContext()).getDao()
            val product = Product(arguments?.getInt("id"), nama, harga, stok,desc, bitmap)
            dao.update(product)
            val fragmentProduct = FragmentProduct()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.main_frag, fragmentProduct)
                commit()
            }

        }
    }
}