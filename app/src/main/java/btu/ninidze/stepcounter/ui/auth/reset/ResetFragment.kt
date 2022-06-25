package btu.ninidze.stepcounter.ui.auth.reset

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import btu.ninidze.stepcounter.R
import btu.ninidze.stepcounter.databinding.ResetFragmentBinding
import btu.ninidze.stepcounter.ui.BaseFragment
import btu.ninidze.stepcounter.util.ValidationUtil
import btu.ninidze.stepcounter.util.extensions.resetField
import btu.ninidze.stepcounter.util.extensions.setErrorField
import btu.ninidze.stepcounter.util.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetFragment: BaseFragment<ResetFragmentBinding>() {

    private val viewModel by viewModels<ResetViewModel>()

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> ResetFragmentBinding
        get() = ResetFragmentBinding::inflate

    override fun init() {
        listeners()
        observers()
    }

    private fun listeners() = with(binding) {
        emailEtReset.resetField {
            resetBtn.isEnabled = true
        }
        val email = emailEtReset.text.toString().trim()

        resetBtn.setOnClickListener {
            if (!ValidationUtil.isEmailValid(email) || email.isNotEmpty()) {
                emailEtReset.setErrorField()
                return@setOnClickListener
            }
            viewModel.resetPassword(email)
        }
    }

    private fun observers() = with(binding) {
        viewModel.navigateState.observe(viewLifecycleOwner) { status ->
            if (status) {
                requireContext().showToast("Check email")
                val navController =
                    requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navController.navController.navigate(R.id.action_resetFragment_to_loginFragment)
            } else {
                emailEtReset.setErrorField()
            }
        }
    }


}