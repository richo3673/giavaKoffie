package com.example.prokir.ui.product

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.prokir.R
import com.example.prokir.database.AppDao
import com.example.prokir.database.AppDatabase
import com.example.prokir.database.Product
import com.example.prokir.databinding.FragmentAddProductBinding
import com.example.prokir.ui.product.viewmodel.ProductViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FragmentAddProduct : Fragment(R.layout.fragment_add_product), View.OnClickListener {
    private lateinit var binding: FragmentAddProductBinding
    private lateinit var dao: AppDao
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
        dao = AppDatabase.getInstance(requireContext()).getDao()
        binding.backbutton.setOnClickListener(this)
        binding.addimage.setOnClickListener {
            openGalleryForImage()
        }
        binding.save.setOnClickListener {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()

        }
    }


    val getAction = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val contentResolver = this.activity?.contentResolver
            val inputStream = uri.let { contentResolver?.openInputStream(it) }
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            binding.addimage.setImageBitmap(bitmap)
            binding.save.setOnClickListener {
                val nama = binding.productname.text.toString()
                val stok = binding.stock.text.toString()
                val harga = binding.price.text.toString()
                val desc = binding.description.text.toString()
                //pakai observer
                if (nama != "" && stok != "" && harga != "" && desc != "") {
                    val product = Product(null, nama, harga.toInt(), stok.toInt(), desc, bitmap)
                    viewModel?.saveDataIntoDb(product)
                    val fragmentProduct = FragmentProduct()
                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        add(R.id.main_frag, fragmentProduct)
                        commit()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please fill out all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun openGalleryForImage() {
        getAction.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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
        }
    }

}
