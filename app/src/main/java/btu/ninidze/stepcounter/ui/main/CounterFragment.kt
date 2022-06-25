package btu.ninidze.stepcounter.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import btu.ninidze.stepcounter.R
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.databinding.FragmentCounterBinding
import btu.ninidze.stepcounter.ui.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class CounterFragment : BaseFragment<FragmentCounterBinding>() {

    private val viewModel by viewModels<CounterViewModel>()

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCounterBinding
        get() = FragmentCounterBinding::inflate

    override fun init() = with(binding) {
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle) { tokenId, from ->
            findNavController().navigate(
                CounterFragmentDirections.actionCounterFragmentToDetailsFragment(tokenId, from)
            )
        }
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = arrayOf("WEAR", "SHOP", "SHELF")[position]
        }.attach()

    }
}