package btu.ninidze.stepcounter.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import btu.ninidze.stepcounter.R
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.databinding.FragmentDetailsBinding
import btu.ninidze.stepcounter.ui.BaseFragment
import btu.ninidze.stepcounter.util.Constants.EMPTY_STRING
import btu.ninidze.stepcounter.util.extensions.getDominantSwatch
import btu.ninidze.stepcounter.util.extensions.isColorDark
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    private val viewModel by viewModels<DetailsViewModel>()

    private val args: DetailsFragmentArgs by navArgs()

    private var tokenId: String = EMPTY_STRING
    private var from: String = EMPTY_STRING

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding
        get() = FragmentDetailsBinding::inflate

    override fun getArgs() {
        tokenId = args.tokenId ?: EMPTY_STRING
        from = args.from ?: EMPTY_STRING
    }

    override fun init() {
        when(from) {
            "ShelfFragment" -> { binding.btnSelect.text = "Sell" }
            "ShopFragment" -> { binding.btnSelect.text = "Buy" }
        }
        viewModel.getSelectedSneaker(tokenId)
        subscribeToObservers()
        setListeners()
    }

    private fun setListeners() = with(binding) {
        btnSelect.setOnClickListener {
            if (from == "ShelfFragment") return@setOnClickListener
            viewModel.buySneakers(tokenId)
        }
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun subscribeToObservers() = with(binding) {
        viewModel.buySneakers.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_detailsFragment_to_counterFragment)

        }

        viewModel.selectedSneaker.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    val data = response.data

                    tvName.text = data.name
                    tvPrice.text = data.price.toString()
                    tvEarnRate.text = getString(R.string.earn_rate, data.earnRate)

                    ivSneakers.getDominantSwatch(data.imageUrl) {
                        if (it != null) {
                            root.setBackgroundColor(it.dominantSwatch?.rgb!!)
                            if (it.dominantSwatch?.rgb!!.isColorDark()) {
                                btnBack.setImageResource(R.drawable.ic_previous_light)
                            } else {
                                btnBack.setImageResource(R.drawable.ic_previous)
                            }

                        }
                    }
                }
                is Resource.Error -> {  }
            }
        }
    }
}