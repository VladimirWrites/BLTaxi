package com.vlad1m1r.baseui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId)