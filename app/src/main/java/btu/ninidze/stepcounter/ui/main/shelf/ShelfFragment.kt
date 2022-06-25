package btu.ninidze.stepcounter.ui.main.shelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import btu.ninidze.stepcounter.R
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.databinding.FragmentShelfBinding
import btu.ninidze.stepcounter.ui.BaseFragment
import btu.ninidze.stepcounter.ui.main.CollectionRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShelfFragment : BaseFragment<FragmentShelfBinding>() {

    private val viewModel by viewModels<ShelfViewModel>()
    lateinit var onClick : (String, String) -> Unit

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentShelfBinding
        get() = FragmentShelfBinding::inflate

    private lateinit var adapter: CollectionRecyclerViewAdapter

    override fun init() {
        viewModel.getUsersSneakers()
        setUpRecyclerView()
        subscribeToObservers()
    }

    private fun subscribeToObservers() = with(binding) {
        viewModel.getUsersSneakers.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    val data = response.data
                    adapter.submitList(data.sortedBy { it.tokenId.toInt() })

                }
                is Resource.Error -> {  }
            }
        }
    }

    private fun setUpRecyclerView() = with(binding) {
        adapter = CollectionRecyclerViewAdapter("ShelfFragment") { tokenId, from ->
            onClick.invoke(tokenId, from)
        }
        rvShop.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@ShelfFragment.adapter
        }
    }

}