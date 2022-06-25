package btu.ninidze.stepcounter.ui.auth.register.model

data class RegisterFormState(
    var userNameError: Int? = null,
    var emailError: Int? = null,
    var passwordError: Int? = null,
    var repeatPasswordError: Int? = null,
    var isDataValid: Boolean = false
)