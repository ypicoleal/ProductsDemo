package com.example.productsdemo.product.groupieviews

import android.graphics.Color
import android.view.View
import com.example.domain.model.Attribute
import com.example.productsdemo.R
import com.example.productsdemo.databinding.PropertyItemLayoutBinding
import com.xwray.groupie.viewbinding.BindableItem

class AttributeItemView(private val attribute: Attribute) : BindableItem<PropertyItemLayoutBinding>() {

    override fun getLayout() = R.layout.property_item_layout

    override fun initializeViewBinding(view: View) = PropertyItemLayoutBinding.bind(view)

    override fun bind(viewBinding: PropertyItemLayoutBinding, position: Int) {
        val colorName = Color.parseColor("#ebebeb".takeIf { position % 2 == 0 } ?: "#f5f5f5")
        val colorValue = Color.parseColor("#f5f5f5".takeIf { position % 2 == 0 } ?: "#ffffff")
        viewBinding.apply {
            name.text = attribute.name
            name.setBackgroundColor(colorName)
            value.text = attribute.value
            value.setBackgroundColor(colorValue)
        }
    }
}