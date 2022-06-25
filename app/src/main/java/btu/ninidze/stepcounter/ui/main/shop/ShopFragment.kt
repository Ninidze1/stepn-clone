package btu.ninidze.stepcounter.ui.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.databinding.FragmentShowBinding
import btu.ninidze.stepcounter.ui.BaseFragment
import btu.ninidze.stepcounter.ui.main.CollectionRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : BaseFragment<FragmentShowBinding>() {

    private val viewModel by viewModels<ShopViewModel>()

    lateinit var onClick: (String, String) -> Unit

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentShowBinding
        get() = FragmentShowBinding::inflate

    private lateinit var adapter: CollectionRecyclerViewAdapter

    override fun init() {
        viewModel.getCollection()
        setUpRecyclerView()
        subscribeToObservers()
    }

    private fun subscribeToObservers() = with(binding) {
        viewModel.collection.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    val data = response.data
                    adapter.submitList(data.sortedBy { it.tokenId.toInt() })
                }
                is Resource.Error ->  {  }
            }
        }
    }

    private fun setUpRecyclerView() = with(binding) {
        adapter = CollectionRecyclerViewAdapter("ShopFragment") { tokenId, from ->
            onClick.invoke(tokenId, from)
        }
        rvShop.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@ShopFragment.adapter
        }
    }


}