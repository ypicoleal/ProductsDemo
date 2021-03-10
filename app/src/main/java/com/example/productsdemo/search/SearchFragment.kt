package com.example.productsdemo.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.productsdemo.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.text.setOnClickListener {
            navigateToProduct("MCO537652200")
        }

        //setNavigation()
    }

    private fun navigateToProduct(productId: String) {
        val action = SearchFragmentDirections
            .actionSearchFragmentToProductItemFragment()
            .setProductId(productId)

        findNavController().navigate(action)
    }

    private fun setNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }
}