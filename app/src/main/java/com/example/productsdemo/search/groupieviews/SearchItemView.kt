package com.example.productsdemo.search.groupieviews

import android.view.View
import com.bumptech.glide.Glide
import com.example.domain.models.ProductSearchItem
import com.example.productsdemo.R
import com.example.productsdemo.databinding.SearchItemLayoutBinding
import com.example.productsdemo.utils.formatPrice
import com.xwray.groupie.viewbinding.BindableItem

class SearchItemView(val item: ProductSearchItem): BindableItem<SearchItemLayoutBinding>() {

    override fun getLayout() = R.layout.search_item_layout

    override fun initializeViewBinding(view: View) = SearchItemLayoutBinding.bind(view)

    override fun bind(viewBinding: SearchItemLayoutBinding, position: Int) {
        viewBinding.title.text = item.title
        viewBinding.price.text = item.price.formatPrice()
        Glide.with(viewBinding.thumbnail.context)
                .load(item.thumbnail.replace("http://", "https://"))
                .into(viewBinding.thumbnail)
    }
}