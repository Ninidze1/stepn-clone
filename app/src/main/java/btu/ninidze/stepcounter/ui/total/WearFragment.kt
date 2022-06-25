package btu.ninidze.stepcounter.ui.total

import android.Manifest
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import btu.ninidze.stepcounter.BuildConfig
import btu.ninidze.stepcounter.R
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.databinding.FragmentWearBinding
import btu.ninidze.stepcounter.ui.BaseFragment
import btu.ninidze.stepcounter.util.Constants.STEPS_FROM_LAST_REBOOT
import btu.ninidze.stepcounter.util.PermissionManager
import btu.ninidze.stepcounter.util.extensions.setIntWithAnimation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WearFragment : BaseFragment<FragmentWearBinding>() {

    private val viewModel: WearViewModel by viewModels()

    @Inject
    lateinit var permissionManager: PermissionManager

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWearBinding
        get() = FragmentWearBinding::inflate

    override fun init() = with(binding) {

        viewModel.getUser()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionManager.requestPermission(
                Manifest.permission.ACTIVITY_RECOGNITION
            ) { isGranted ->
                if (isGranted) {
                    if (BuildConfig.DEBUG) {
                        viewModel.initPreviousStep(STEPS_FROM_LAST_REBOOT)
                        tvCounter.setIntWithAnimation(STEPS_FROM_LAST_REBOOT.toString()) {
                            viewModel.getUser.observe(viewLifecycleOwner) {
                                when(it) {
                                    is Resource.Success -> {
                                        viewModel.delayAndRun {
                                            if (it.data.inventory == "[]") {
                                                findNavController().navigate(R.id.action_wearFragment_to_giftFragment)
                                                return@delayAndRun
                                            }
                                            findNavController().navigate(R.id.action_wearFragment_to_counterFragment)

                                        }
                                    }
                                    is Resource.Error -> {}
                                }
                            }
                        }
                        return@requestPermission
                    }
                    viewModel.count.observe(viewLifecycleOwner) { steps ->
                        viewModel.initPreviousStep(steps)
                        tvCounter.setIntWithAnimation(steps.toString()) {
                            viewModel.delayAndRun {
                                findNavController().navigate(R.id.action_wearFragment_to_giftFragment)
                            }
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "give permissions", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}