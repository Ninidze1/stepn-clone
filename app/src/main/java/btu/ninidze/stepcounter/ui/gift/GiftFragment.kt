package btu.ninidze.stepcounter.ui.gift

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import btu.ninidze.stepcounter.R
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.databinding.FragmentGiftBinding
import btu.ninidze.stepcounter.ui.BaseFragment
import btu.ninidze.stepcounter.util.extensions.getDominantSwatch
import btu.ninidze.stepcounter.util.extensions.getSneakersIdFromName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiftFragment : BaseFragment<FragmentGiftBinding>() {

    private val viewModel by viewModels<GiftViewModel>()

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGiftBinding
        get() = FragmentGiftBinding::inflate

    override fun init() {
        viewModel.getGiftedSneakers()
        subscribeToObservers()
        setListeners()

    }

    private fun setListeners() = with(binding) {
        llOptionFirst.setOnClickListener {
            val sneakersId = tvFirstId.tag.toString()
            findNavController().navigate(
                GiftFragmentDirections.actionGiftFragmentToDetailsFragment(sneakersId, "")
            )
        }
        llOptionSecond.setOnClickListener {
            val sneakersId = tvSecondId.tag.toString()
            findNavController().navigate(
                GiftFragmentDirections.actionGiftFragmentToDetailsFragment(sneakersId, "")
            )
        }
        llOptionThird.setOnClickListener {
            val sneakersId = tvThirdId.tag.toString()
            findNavController().navigate(
                GiftFragmentDirections.actionGiftFragmentToDetailsFragment(sneakersId, "")
            )
        }
    }

    private fun subscribeToObservers() = with(binding) {
        viewModel.giftedSneakers.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    val data = response.data

                    ivOptionFirst.getDominantSwatch(data[0].imageUrl) {
                        if (it != null) {
                            tvFirstId.apply {
                                tag = data[0].tokenId
                                text = data[0].name.getSneakersIdFromName()
                            }
                            llOptionFirst.setBackgroundColor(it.dominantSwatch?.rgb ?: R.color.secondary_color)
                        }
                    }
                    ivOptionSecond.getDominantSwatch(data[1].imageUrl) {
                        if (it != null) {
                            tvSecondId.apply {
                                tag = data[1].tokenId
                                text = data[1].name.getSneakersIdFromName()
                            }
                            llOptionSecond.setBackgroundColor(it.dominantSwatch?.rgb ?: R.color.secondary_color)
                        }
                    }
                    ivOptionThird.getDominantSwatch(data[2].imageUrl) {
                        if (it != null) {
                            tvThirdId.apply {
                                tag = data[2].tokenId
                                text = data[2].name.getSneakersIdFromName()
                            }
                            llOptionThird.setBackgroundColor(it.dominantSwatch?.rgb ?: R.color.secondary_color)
                        }
                    }
                }
                is Resource.Error -> {

                }
            }
        }
    }

}