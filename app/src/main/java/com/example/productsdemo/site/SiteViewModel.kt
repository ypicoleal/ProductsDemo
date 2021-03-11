package com.example.productsdemo.site

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.actions.LoadSites
import com.example.domain.models.Site
import kotlinx.coroutines.launch

class SiteViewModel(private val loadSites: LoadSites) : ViewModel() {

    val data = MutableLiveData<List<Site>>()
    val errorData = MutableLiveData<Boolean>()

    fun getSites() {
        viewModelScope.launch {
            loadSites().let { result ->
                when (result) {
                    is LoadSites.Result.Success -> data.postValue(result.items)
                    is LoadSites.Result.Error -> errorData.postValue(false)
                    is LoadSites.Result.NetworkError -> errorData.postValue(true)
                }
            }
        }
    }
}