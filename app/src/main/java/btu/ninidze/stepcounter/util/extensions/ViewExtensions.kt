package btu.ninidze.stepcounter.util.extensions

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.palette.graphics.Palette
import btu.ninidze.stepcounter.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun createPaletteAsync(bitmap: Bitmap, block: (Palette?) -> Unit) {
    Palette.from(bitmap).generate { palette ->
        block.invoke(palette)
    }
}

fun ImageView.getDominantSwatch(url: String, block: (Palette?) -> Unit) {
    Glide.with(this.context)
        .asBitmap()
        .load(url)
        .centerCrop()
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                setImageBitmap(resource)
                createPaletteAsync(resource) { palette ->
                    block.invoke(palette)
                }

            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }
        })
}

fun ImageView.loadImg(url: String) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.custom_card_rate)
        .into(this)
}