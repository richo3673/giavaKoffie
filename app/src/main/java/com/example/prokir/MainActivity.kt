package com.example.prokir

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.prokir.database.*
import com.example.prokir.databinding.ActivityMainBinding
import com.example.prokir.ui.product.FragmentProduct
import com.example.prokir.ui.profile.FragmentProfile
import com.example.prokir.ui.statistic.FragmentStatistic


class MainActivity : AppCompatActivity(), View.OnClickListener, Communicator {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "db")
//            .fallbackToDestructiveMigration()
//            .build()
        val fragOne = FragmentHome()

        binding.productfragment.setOnClickListener(this)
        binding.homefragment.setOnClickListener(this)
        binding.addTrans.setOnClickListener(this)
        binding.statisticButton.setOnClickListener(this)
        binding.profileFragment.setOnClickListener(this)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_frag, fragOne)
            commit()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.productfragment -> {
                val fragProduct = FragmentProduct()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_frag, fragProduct)
                    commit()
                }
            }
            R.id.homefragment -> {
                val fragOne = FragmentHome()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_frag, fragOne)
                    commit()
                }
            }
            R.id.addTrans -> {
                val fragThree = FragmentOrder()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_frag, fragThree)
                    commit()
                }
            }
            R.id.statisticButton -> {
                val fragStat = FragmentStatistic()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_frag, fragStat)
                    commit()
                    }
            }
            R.id.profileFragment -> {
                val fragStat = FragmentProfile()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_frag, fragStat)
                    commit()
                }
            }
        }
    }

    override fun passData(prod_id: Int) {
        val bundle = Bundle()
        bundle.putInt("prod_id", prod_id)
        val trans = this.supportFragmentManager.beginTransaction()
        val fthree = FragmentOrder()
        fthree.arguments = bundle
        trans.replace(R.id.main_frag, fthree)
        trans.commit()
    }
//    private fun initData(){
//        val cust1 = Customer("Jang Hyun", "Korea", "08237141234")
//        val cust2 = Customer("Kim Chang", "Italy", "08237141235")
////        db.customerDao().insert(cust1, cust2)
//    }
}