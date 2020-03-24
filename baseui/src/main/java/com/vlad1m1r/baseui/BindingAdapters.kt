package com.vlad1m1r.baseui

import android.view.View
import androidx.databinding.BindingAdapter
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView

@BindingAdapter("goneUnless")
fun View.goneUnless(visible: Boolean) {
    this.visibility = if(visible) View.VISIBLE else View.GONE
}

@BindingAdapter("srcTileMode")
fun ImageView.setImageViewTileMode(drawable: Drawable) {
    val bg = drawable as BitmapDrawable
    bg.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
    background = bg
}