package btu.ninidze.stepcounter.util.extensions

import android.animation.ValueAnimator
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.animation.doOnEnd
import androidx.core.text.isDigitsOnly

fun TextView.setIntWithAnimation(finalValue: String, duration: Long = 950, block: () -> Unit ) {
    if (finalValue.isDigitsOnly()) {
        val animator = ValueAnimator.ofInt(text.toString().toIntSafely(), finalValue.toIntSafely())
        animator.duration = duration
        animator.addUpdateListener { animation ->
            text = animation.animatedValue.toString()
        }
        animator.start()
        animator.doOnEnd {
            block.invoke()
        }
    }
}

//fun TextView.setColorSensitiveText(text: String, @ColorRes backgroundColor: Int) {
//    if (backgroundColor.isColorDark()) {
//
//    }
//}