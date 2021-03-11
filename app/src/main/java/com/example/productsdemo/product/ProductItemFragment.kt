package com.example.productsdemo.product

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.productsdemo.R
import com.example.productsdemo.databinding.FragmentProductItemBinding
import com.example.productsdemo.product.adapters.SliderAdapter
import com.example.productsdemo.product.groupieviews.AttributeItemView
import com.example.productsdemo.product.groupieviews.VariationItemView
import com.example.productsdemo.utils.formatPrice
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.viewModel

class ProductItemFragment : Fragment() {

    private val viewModel: ProductItemViewModel by viewModel()

    private val binding by lazy { FragmentProductItemBinding.inflate(layoutInflater) }
    private val variationAdapter = GroupAdapter<GroupieViewHolder>()
    private val attributesAdapter = GroupAdapter<GroupieViewHolder>()

    private val args: ProductItemFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.variations.adapter = variationAdapter
        binding.attributes.adapter = attributesAdapter

        args.productId?.let {
            viewModel.loadData(it)
            viewModel.loadDescription(it)
        }
        setNavigation()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.data.observe(viewLifecycleOwner, {
            binding.loading.isVisible = false
            binding.subtitle.text = resources.getString(R.string.product_subtitle, it.condition, it.soldQuantity)
            binding.title.text = it.title
            binding.imagesCount.text = resources.getString(R.string.product_image_count, 1, it.images.size)
            binding.imagesPager.adapter = SliderAdapter(it.images)
            binding.imagesPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) = Unit
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
                override fun onPageSelected(position: Int) {
                    binding.imagesCount.text = resources.getString(R.string.product_image_count, position + 1, it.images.size)
                }
            })
            it.variations?.let { variations -> variationAdapter.update(variations.map { VariationItemView(it) }) }
            it.attributes.let { attributes -> attributesAdapter.update(attributes.map { AttributeItemView(it) }) }
            binding.price.text = it.price.formatPrice()
            binding.originalPrice.text = it.originalPrice.formatPrice()
            binding.originalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        })

        viewModel.description.observe(viewLifecycleOwner, {
            binding.description.text = it
        })

        viewModel.errorData.observe(viewLifecycleOwner, { isNetworkError ->
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_description)
                .setPositiveButton(R.string.close) { dialog, _ ->
                    findNavController().popBackStack()
                }.apply {
                    if (isNetworkError) {
                        setNegativeButton(R.string.retry) { _, _ ->
                            args.productId?.let {
                                viewModel.loadData(it)
                                viewModel.loadDescription(it)
                            }
                        }
                    }
                }
                .create()
                .show()
        })
    }

    private fun setNavigation() {
        binding.backIcon.setOnClickListener { findNavController().popBackStack() }

        requireActivity().onBackPressedDispatcher
                .addCallback(viewLifecycleOwner) { findNavController().popBackStack() }
    }
}