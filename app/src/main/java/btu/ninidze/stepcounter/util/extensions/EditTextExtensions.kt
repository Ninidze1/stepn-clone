package btu.ninidze.stepcounter.util.extensions

import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import btu.ninidze.stepcounter.R

fun EditText.resetField(block: ((EditText) -> Unit)? = null) {
    doOnTextChanged { text, _, _, _ ->
        if (text == null) {
            return@doOnTextChanged
        }
        if (text.isNotEmpty()) {
            setBackgroundResource(R.drawable.costum_input)
            block?.invoke(this)
        }
    }
}

fun EditText.setErrorField(errorText: String? = null) {
    setBackgroundResource(R.drawable.costum_error_input)
    text.clear()
    if (errorText != null) {
        context.showToast(errorText)
    }

}