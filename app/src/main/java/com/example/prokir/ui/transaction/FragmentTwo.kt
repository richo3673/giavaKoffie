package com.example.prokir

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prokir.database.AppDao
import com.example.prokir.database.AppDatabase
import com.example.prokir.databinding.FragmentOneBinding
import com.example.prokir.databinding.FragmentTwoBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ragmentTwo : Fragment(R.layout.fragment_two)
{
    private lateinit var dao: AppDao
    private lateinit var binding: FragmentTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTwoBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(null, dao.getAllCustomers().size.toString())
    }

    private fun initData(){
    }
}