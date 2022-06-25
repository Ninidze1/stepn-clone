package btu.ninidze.stepcounter.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import btu.ninidze.stepcounter.R
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.databinding.FragmentSplashScreenBinding
import btu.ninidze.stepcounter.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment: BaseFragment<FragmentSplashScreenBinding>() {

    private val viewModel by viewModels<SplashScreenViewModel>()

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashScreenBinding
        get() = FragmentSplashScreenBinding::inflate

    override fun init() {
        viewModel.getUser()
        setUpAnimation()
    }

    private fun setUpAnimation() {
        binding.animatedIcon.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                viewModel.getUser.observe(viewLifecycleOwner) { response ->
                    when(response) {
                        is Resource.Success -> {
                            viewModel.runOnMain {
                                if (viewModel.getUID() == null) {
                                    findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                                    return@runOnMain
                                }
                                if (viewModel.countedOnce.value == false || response.data.inventory.toString() == "[]") {
                                    findNavController().navigate(R.id.action_splashScreenFragment_to_wearFragment)
                                    return@runOnMain
                                }
                                findNavController().navigate(R.id.action_splashScreenFragment_to_counterFragment)
                            }
                        }
                        is Resource.Error -> {}
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }
}