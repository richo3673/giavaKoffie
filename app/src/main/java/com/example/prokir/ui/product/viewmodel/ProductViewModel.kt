package com.example.prokir.ui.product.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prokir.database.AppDatabase
import com.example.prokir.database.Product
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class ProductViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()
    private var dataBaseInstance: AppDatabase? = null

    var productList = MutableLiveData<List<Product>>()
    var product = MutableLiveData<Product>()
    fun setInstanceOfDB(dataBaseInstance: AppDatabase) {
        this.dataBaseInstance = dataBaseInstance
    }

    fun saveDataIntoDb(data: Product) {
        dataBaseInstance?.getDao()?.insert(data)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                getProductData(null)
            }, {
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun getProductData(keyword: String?) {
        dataBaseInstance?.getDao()?.getAllProducts()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if (!it.isNullOrEmpty()) {
                    if(keyword != null)
                    productList.postValue(it.filter { it.namaProduk?.contains(keyword) ?: false })
                    else{
                        productList.postValue(it)
                    }
                } else {
                    productList.postValue(listOf())
                }
                it.forEach {
                    it.namaProduk?.let { it1 -> Log.v("Product Name", it1) }
                }
            }, {
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun getProductByID(product_id: Int) {
        dataBaseInstance?.getDao()?.getProductbyId(product_id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                product.postValue(it)
            }, {
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun deleteProduct(product: Product) {
        dataBaseInstance?.getDao()?.delete(product)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                //Refresh Page data
                getProductData(null)
            }, {

            })?.let {
                compositeDisposable.add(it)
            }
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

}