package com.vlad1m1r.baseui

import androidx.databinding.Observable

fun <T: Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
    object: Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable?, i: Int) =
            callback(observable as T)
    }.also { addOnPropertyChangedCallback(it) }