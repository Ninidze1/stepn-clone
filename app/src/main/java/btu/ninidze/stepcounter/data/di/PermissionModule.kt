package btu.ninidze.stepcounter.data.di

import androidx.fragment.app.Fragment
import btu.ninidze.stepcounter.util.FragmentPermissionManager
import btu.ninidze.stepcounter.util.PermissionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class PermissionModule {

    @Provides
    fun bindFragmentPermissionManager(fragment: Fragment): PermissionManager =
        FragmentPermissionManager(fragment, fragment.requireActivity())

}