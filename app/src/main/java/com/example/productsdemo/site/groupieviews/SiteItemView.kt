package com.example.productsdemo.site.groupieviews

import android.view.View
import com.example.domain.models.Site
import com.example.productsdemo.R
import com.example.productsdemo.databinding.SiteItemLayoutBinding
import com.xwray.groupie.viewbinding.BindableItem

class SiteItemView(val item: Site): BindableItem<SiteItemLayoutBinding>() {

    override fun getLayout() = R.layout.site_item_layout

    override fun initializeViewBinding(view: View) = SiteItemLayoutBinding.bind(view)

    override fun bind(viewBinding: SiteItemLayoutBinding, position: Int) {
        viewBinding.title.text = item.name
    }
}