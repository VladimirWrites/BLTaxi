package com.vlad1m1r.bltaxi.taxi.ui.adapter

import android.view.View

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class TaxiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!
}
