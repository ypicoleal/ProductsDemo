package com.example.productsdemo.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.productsdemo.R
import com.example.productsdemo.databinding.FragmentSearchBinding
import com.example.productsdemo.search.groupieviews.SearchItemView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel: ProductSearchViewModel by viewModel()

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.items.adapter = groupAdapter
        groupAdapter.setOnItemClickListener { item, _ ->
            (item as? SearchItemView)?.item?.let {
                navigateToProduct(it.id)
            }
        }
        setNavigation()
        observeViewModel()

        binding.search.setOnEditorActionListener { searchView, actionId, _ ->
            val site = args.siteId
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchProducts(searchView.text.toString(), site ?: "")
            }
            false
        }
    }

    private fun observeViewModel() {
        viewModel.data.observe(viewLifecycleOwner, { items ->
            groupAdapter.update(items.map { SearchItemView(it) })
        })

        viewModel.errorData.observe(viewLifecycleOwner, { isNetworkError ->
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_description)
                .setPositiveButton(R.string.close) { dialog, _ ->
                    activity?.finish()
                }.apply {
                    if (isNetworkError) {
                        setNegativeButton(R.string.retry) { _, _ ->
                            viewModel.searchProducts(binding.search.text.toString(), args.siteId ?: "")
                        }
                    }
                }
                .create()
                .show()
        })
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