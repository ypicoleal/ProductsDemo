package com.example.productsdemo.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.actions.LoadProductDescription
import com.example.domain.actions.LoadProductItem
import com.example.domain.models.ProductItem
import kotlinx.coroutines.launch

class ProductItemViewModel(
        private val loadProductItem: LoadProductItem,
        private val loadProductDescription: LoadProductDescription
        ): ViewModel() {

    val data = MutableLiveData<ProductItem>()
    val description = MutableLiveData<String>()
    val errorData = MutableLiveData<Boolean>()

    fun loadData(productId: String) {
        viewModelScope.launch {
            loadProductItem(productId).let { result ->
                when (result) {
                    is LoadProductItem.Result.Success -> data.postValue(result.productItem)
                    is LoadProductItem.Result.Error -> errorData.postValue(false)
                    is LoadProductItem.Result.NetworkError -> errorData.postValue(true)
                }
            }
        }
    }

    fun loadDescription(productId: String) {
        viewModelScope.launch {
            loadProductDescription(productId).let { result ->
                when (result) {
                    is LoadProductDescription.Result.Success -> description.postValue(result.description)
                    is LoadProductDescription.Result.Error -> errorData.postValue(false)
                    is LoadProductDescription.Result.NetworkError -> errorData.postValue(true)
                }
            }
        }
    }
}