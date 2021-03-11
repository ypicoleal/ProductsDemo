package com.example.productsdemo.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.actions.LoadProductSearch
import com.example.domain.model.ProductSearchItem
import kotlinx.coroutines.launch

class ProductSearchViewModel(private val loadProductSearch: LoadProductSearch) : ViewModel() {

    val data = MutableLiveData<List<ProductSearchItem>>()
    val errorData = MutableLiveData<String>()

    fun searchProducts(query: String, site: String) {
        viewModelScope.launch {
            loadProductSearch(query, site).let { result ->
                when (result) {
                    is LoadProductSearch.Result.Success -> data.postValue(result.items)
                    is LoadProductSearch.Result.Error -> errorData.postValue("error")
                    is LoadProductSearch.Result.NetworkError -> errorData.postValue("network error")
                }
            }
        }
    }
}