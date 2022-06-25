package btu.ninidze.stepcounter.util

import android.text.Editable
import android.widget.EditText
import btu.ninidze.stepcounter.R
import btu.ninidze.stepcounter.util.extensions.setErrorField
import java.util.regex.Pattern.compile

object ValidationUtil {

    fun isEmailValid(email: String?): Boolean {
        if (email == null) {
            return false
        }
        val emailRegex = compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
        )
        return emailRegex.matcher(email).matches()
    }

    fun isNameValid(name: String?): Boolean {
        if (name == null) {
            return false
        }
        return name.isNotEmpty()
    }

    fun isPasswordValid(password: String?): Boolean {
        if (password == null) {
            return false
        }
        return password.length >= 6
    }

    fun doesPasswordMatch(password: String?, repeated: String?): Boolean {
        if (password == null || repeated == null) {
            return false
        }
        return (password == repeated) && password.isNotEmpty()
    }

    fun setOrClearErrors(fields: Map<EditText, Int?>) {
        fields.forEach { entry ->
            if (entry.value != null) {
                entry.key.apply {
                    setErrorField()
                    error = entry.key.context.getString(entry.value!!)
                }
            } else {
                entry.key.error = null
            }
        }
    }
}