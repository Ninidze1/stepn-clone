package btu.ninidze.stepcounter.ui.auth.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import btu.ninidze.stepcounter.R
import btu.ninidze.stepcounter.databinding.RegisterFragmentBinding
import btu.ninidze.stepcounter.ui.BaseFragment
import btu.ninidze.stepcounter.util.ValidationUtil
import btu.ninidze.stepcounter.util.extensions.resetField
import btu.ninidze.stepcounter.util.extensions.setErrorField
import btu.ninidze.stepcounter.util.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterFragmentBinding>() {

    private val viewModel by viewModels<RegisterViewModel>()

    override fun init() {
        listeners()
        observers()
    }

    private fun listeners() = with(binding) {
        resetFields()
        backToLoginBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        registerBtn.setOnClickListener {
            viewModel.validateFieldsAndRegister(
                name = nameRegEt.text.toString().trim(),
                email = emailRegEt.text.toString().trim(),
                password = passwordRegEt.text.toString().trim(),
                repPassword = passwordConfRegEt.text.toString().trim(),
                onNameError = { nameRegEt.setErrorField() },
                onEmailError = { emailRegEt.setErrorField() },
                onPasswordError = { passwordRegEt.setErrorField() },
                onRepPasswordError = { passwordConfRegEt.setErrorField() }
            )
        }
    }

    private fun observers() = with(binding) {
        viewModel.navigateState.observe(viewLifecycleOwner) { status ->
            if (status) {
                viewModel.createUser()
                findNavController().navigate(R.id.action_registerFragment_to_wearFragment)
            }
        }
    }

    private fun resetFields() = with(binding) {
        nameRegEt.resetField {
            registerBtn.isEnabled = true
        }
        emailRegEt.resetField {
            registerBtn.isEnabled = true
        }
        passwordConfRegEt.resetField {
            registerBtn.isEnabled = true
        }
        passwordRegEt.resetField {
            registerBtn.isEnabled = true
        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> RegisterFragmentBinding
        get() = RegisterFragmentBinding::inflate

}