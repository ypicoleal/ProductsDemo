package com.example.productsdemo.site

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.productsdemo.databinding.FragmentCountryBinding
import com.example.productsdemo.site.groupieviews.SiteItemView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.viewModel

class SitesFragment  : Fragment() {

    private val viewModel: SiteViewModel by viewModel()

    private val binding by lazy { FragmentCountryBinding.inflate(layoutInflater) }
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.items.adapter = groupAdapter
        groupAdapter.setOnItemClickListener { item, _ ->
            (item as? SiteItemView)?.item?.let {
                navigateToSearch(it.id)
            }
        }

        observeViewModel()
        viewModel.getSites()
    }

    private fun observeViewModel() {
        viewModel.data.observe(viewLifecycleOwner, {
            groupAdapter.update(it.map { site ->  SiteItemView(site) })
        })
    }

    private fun navigateToSearch(siteId: String) {
        val action = SitesFragmentDirections
                .actionCountryFragmentToSearchFragment()
                .setSiteId(siteId)

        findNavController().navigate(action)
    }
}