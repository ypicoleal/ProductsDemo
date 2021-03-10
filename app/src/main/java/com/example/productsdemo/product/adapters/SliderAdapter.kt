package com.example.productsdemo.product.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.productsdemo.databinding.ProductImageBinding

class SliderAdapter(private val images: List<String>): PagerAdapter() {

    override fun getCount() = images.size

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o as? ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(container.context)
        val binding = ProductImageBinding.inflate(layoutInflater, container, true)
        Glide
            .with(binding.image.context)
            .load(images[position])
            .into(binding.image)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as ConstraintLayout)
    }
}