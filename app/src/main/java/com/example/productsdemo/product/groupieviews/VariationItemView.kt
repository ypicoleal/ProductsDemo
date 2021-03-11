package com.example.productsdemo.product.groupieviews

import android.view.View
import com.example.domain.models.ProductVariation
import com.example.productsdemo.R
import com.example.productsdemo.databinding.VariationItemLayoutBinding
import com.xwray.groupie.viewbinding.BindableItem

class VariationItemView(private val variation: ProductVariation) : BindableItem<VariationItemLayoutBinding>() {

    override fun getLayout() = R.layout.variation_item_layout

    override fun initializeViewBinding(view: View) = VariationItemLayoutBinding.bind(view)

    override fun bind(viewBinding: VariationItemLayoutBinding, position: Int) {
        viewBinding.apply {
            name.text = variation.name + ": "
            value.text = variation.value
        }
    }
}