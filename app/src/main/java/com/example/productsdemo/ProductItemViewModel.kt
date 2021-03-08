package com.example.productsdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.actions.LoadProductItem
import com.example.domain.model.ProductItem
import kotlinx.coroutines.launch

class ProductItemViewModel(private val loadProductItem: LoadProductItem): ViewModel() {

    val data = MutableLiveData<ProductItem>()
    val errorData = MutableLiveData<String>()

    fun loadData() {
        viewModelScope.launch {
            loadProductItem("MLA902364637").let { result ->
                when (result) {
                    is LoadProductItem.Result.Success -> data.postValue(result.productItem)
                    is LoadProductItem.Result.Error -> errorData.postValue("error")
                    is LoadProductItem.Result.NetworkError -> errorData.postValue("network error")
                }
            }
        }
    }
}