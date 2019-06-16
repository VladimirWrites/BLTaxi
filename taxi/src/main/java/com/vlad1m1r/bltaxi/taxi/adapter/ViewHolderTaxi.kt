package com.vlad1m1r.bltaxi.taxi.adapter

import android.view.View

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewHolderTaxi(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!
}
