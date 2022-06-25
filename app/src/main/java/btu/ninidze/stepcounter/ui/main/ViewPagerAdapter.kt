package btu.ninidze.stepcounter.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import btu.ninidze.stepcounter.ui.main.count.StepFragment
import btu.ninidze.stepcounter.ui.main.shelf.ShelfFragment
import btu.ninidze.stepcounter.ui.main.shop.ShopFragment
import btu.ninidze.stepcounter.util.Constants.NUM_TABS

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val onClick: (String, String) -> Unit) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = NUM_TABS

    override fun createFragment(position: Int): Fragment {
        val stepFragment = StepFragment()
        val shopFragment = ShopFragment()
        val shelfFragment = ShelfFragment()

        shopFragment.onClick = { tokenId, from ->
            onClick.invoke(tokenId, from)
        }

        shelfFragment.onClick = { tokenId, from ->
            onClick.invoke(tokenId, from)
        }
        return when(position) {
            0 -> stepFragment
            1 -> shopFragment
            else -> shelfFragment
        }
    }
}