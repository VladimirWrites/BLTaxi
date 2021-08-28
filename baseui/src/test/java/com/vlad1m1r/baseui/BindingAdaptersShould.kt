package com.vlad1m1r.baseui

import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class BindingAdaptersShould {

    private val view = mock<View>()
    private val imageView = mock<ImageView>()

    private val bitmapDrawable = mock<BitmapDrawable>()

    @Test
    fun makeViewVisible_whenNotGone() {
        view.goneUnless(true)
        verify(view).visibility = View.VISIBLE
    }

    @Test
    fun makeViewGone_whenGone() {
        view.goneUnless(false)
        verify(view).visibility = View.GONE
    }

    @Test
    fun setImageViewTileMode() {
        imageView.setImageViewTileMode(bitmapDrawable)

        inOrder(bitmapDrawable, imageView) {
            verify(bitmapDrawable).setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
            verify(imageView).background = bitmapDrawable
        }
    }
}