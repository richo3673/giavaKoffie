package com.example.prokir

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prokir.database.*
import com.example.prokir.databinding.FragmentOneBinding
import com.example.prokir.ui.transaction.helper.TransactionAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentTransaction : Fragment(R.layout.fragment_one) {
    private lateinit var dao: AppDao
    private lateinit var binding: FragmentOneBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dao = AppDatabase.getInstance(requireContext()).getDao()
        binding = FragmentOneBinding.inflate(layoutInflater)
        activity?.runOnUiThread {
//            GlobalScope.launch {
//                initData()
//                adapter = TransactionAdapter(dao.getAllOrders())
//            }
        }
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        GlobalScope.launch{
//            dao.deleteAllOrder()
//        }

        recyclerView = view.findViewById(R.id.records)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        GlobalScope.launch {
            initData()
            adapter = TransactionAdapter(dao.getAllOrders())
            activity?.runOnUiThread {
                recyclerView.adapter = adapter
            }
        }

    }

    private fun initData() {
//        val cust1 = Customer("Jang Hyun", "Korea", "08237141234")
//        val cust2 = Customer("Pesa Chang", "Italy", "08237141235")
//        dao.insert(cust1, cust2)
//        val p1 = Product("Kopi Hitam", 50000, 25, "-")
//        val p2 = Product("Kopi Putih", 52000, 28, "-")
//        dao.insert(p1, p2)
//        val dateFormat : DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss ")
//        val date: Date = Date()
//        val dateTime = dateFormat.format(date)
//        val o1 = Order(1, dateTime, 5000, 100000)
//        val o2 = Order(2, dateTime, 5000, 104000)
//        dao.insert(o1, o2)
//        GlobalScope.launch {
//            val oi1 = OrderItems(1, 1, 2, dao.searchProductById(1).harga?.times(2))
//            val oi2 = OrderItems(2, 2, 2, dao.searchProductById(2).harga?.times(2))
//            dao.insert(oi2)
//        }
    }
}