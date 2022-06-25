package btu.ninidze.stepcounter.ui.auth.login.model

data class LoginFormState(
    var emailError: Int? = null,
    var passwordError: Int? = null,
    var isDataValid: Boolean = false
)