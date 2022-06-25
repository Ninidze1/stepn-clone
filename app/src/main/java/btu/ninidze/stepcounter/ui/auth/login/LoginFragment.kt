package btu.ninidze.stepcounter.ui.auth.login

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import btu.ninidze.stepcounter.R
import btu.ninidze.stepcounter.databinding.LoginFragmentBinding
import btu.ninidze.stepcounter.ui.BaseFragment
import btu.ninidze.stepcounter.util.ValidationUtil
import btu.ninidze.stepcounter.util.extensions.resetField
import btu.ninidze.stepcounter.util.extensions.setErrorField
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>() {

    private val viewModel by viewModels<LoginViewModel>()

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> LoginFragmentBinding
        get() = LoginFragmentBinding::inflate

    override fun init() {
        listeners()
        observers()
    }

    private fun listeners() = with(binding) {
        registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        resetBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetFragment)
        }
        loginBtn.setOnClickListener {
            viewModel.validateFieldsAndLogin(
                email = emailEt.text?.trim().toString(),
                password = passwordEt.text?.trim().toString(),
                onEmailError = { emailEt.setErrorField() },
                onPasswordError = { passwordEt.setErrorField() }
            )

            emailEt.resetField {
                loginBtn.isEnabled = true
            }
            passwordEt.resetField {
                loginBtn.isEnabled = true
            }
        }
    }

    private fun observers() = with(binding) {

        viewModel.navigateState.observe(viewLifecycleOwner) { status ->
            if (status) {
                findNavController().navigate(R.id.action_loginFragment_to_wearFragment)
            } else {
                emailEt.setErrorField()
                passwordEt.setErrorField()
            }
        }
    }

}