package com.example.prokir

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.prokir.database.*
import com.example.prokir.databinding.FragmentThreeBinding
import com.example.prokir.ui.transaction.FragmentProducts
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FragmentOrder : Fragment(R.layout.fragment_three)
{
    private lateinit var dao: AppDao
    private lateinit var binding: FragmentThreeBinding
    private var datagiven: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThreeBinding.inflate(layoutInflater)
        datagiven = arguments?.getInt("prod_id")
        Log.i(null, "data given: "+datagiven.toString())
        return inflater.inflate(R.layout.fragment_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao = AppDatabase.getInstance(requireContext()).getDao()

        val dateFormat : DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss ")
        val dateTime = dateFormat.format(Date())
        view.findViewById<TextView>(R.id.tanggal).text = dateTime

        if (datagiven != null){
            GlobalScope.launch {
                val prod = dao.searchProductById(datagiven!!)
                Log.i(null, "fthreetest : "+prod.namaProduk)
                activity?.runOnUiThread {
                    view.findViewById<TextView>(R.id.coffeename).text = prod.namaProduk
                    view.findViewById<TextView>(R.id.coffeeprice).text = "Rp " + prod.harga.toString()
                    view.findViewById<TextView>(R.id.submit).isEnabled = true
                }

            }
        }

        view.findViewById<ImageView>(R.id.products).setOnClickListener {
            val fragProducts = FragmentProducts()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.main_frag, fragProducts)
                commit()
            }
        }

        view.findViewById<TextView>(R.id.submit).setOnClickListener {
            Toast.makeText(requireContext(), "bisa diklik", Toast.LENGTH_SHORT).show()

            if(datagiven!=null && view.findViewById<EditText>(R.id.jmlProduk).text.toString()!=""){
                Log.i(null, "lolos ordernya")
                val tanggal: String = view.findViewById<TextView>(R.id.tanggal).text.toString()
                val subtotal: Int = getOnlyNumber(view.findViewById<TextView>(R.id.hargasubtotal).text
                    .toString())
                    .toInt()
                Log.i(null, "iki subtotal e woi : $subtotal")
                GlobalScope.launch {
                    val order: Order = Order(1, tanggal, 0, subtotal)
                    dao.insert(order)
                    val orderItems: OrderItems = OrderItems(
                        dao.getNewestOrder().order_id,
                        dao.searchProductById(datagiven!!).product_id,
                        view.findViewById<EditText>(R.id.jmlProduk).text.toString().toInt(),
                        getOnlyNumber(view.findViewById<TextView>(R.id.hargasubtotal).text.toString()).toInt()
                    )
                    dao.insert(orderItems)
                    Log.i(null, "jumlah e nambah ora : "+ dao.getAllOrders().size)
                }
                val fragOne = FragmentHome()
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_frag, fragOne)
                    commit()
                }
            }
        }

        view.findViewById<EditText>(R.id.jmlProduk).addTextChangedListener(quantityWatcher)
    }

    private val quantityWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            Log.e(null, view?.findViewById<EditText>(R.id.jmlProduk)?.text.toString())
            if(view?.findViewById<EditText>(R.id.jmlProduk)?.text.toString()!=""){
                val kuantitas: Double = view?.findViewById<EditText>(R.id.jmlProduk)?.text.toString().toDouble()
                val totalItem: Double =
                    getOnlyNumber(view?.findViewById<TextView>(R.id.coffeeprice)?.text.toString())*kuantitas
                val pajak: Double = totalItem*0.1
                val subtotal: Double = totalItem+pajak
                view?.findViewById<TextView>(R.id.hargatotalitem)?.text = "Rp $totalItem"
                view?.findViewById<TextView>(R.id.hargapajak)?.text = "Rp $pajak"
                view?.findViewById<TextView>(R.id.hargasubtotal)?.text = "Rp $subtotal"
            }
            else{
                view?.findViewById<TextView>(R.id.hargatotalitem)?.text = "Rp 0"
                view?.findViewById<TextView>(R.id.hargapajak)?.text = "Rp 0"
                view?.findViewById<TextView>(R.id.hargasubtotal)?.text = "Rp 0"
            }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }

    private val getOnlyNumber: (String) -> Double = { str: String ->
        Log.i(null, "oeeee "+str.replace(Regex("[^0-9]"), "").toDouble())
        str.replace(Regex("[^0-9]"), "").toDouble()
    }

}