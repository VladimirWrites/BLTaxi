package com.vlad1m1r.bltaxi.domain

import com.vlad1m1r.bltaxi.domain.Language.*

enum class Language private constructor(val code: String) {
    EN("en"),
    SR("sr"),
    HR("hr"),
    BS("bs")
}

fun fromCode(code: String): Language {
    return when(code) {
        EN.code -> EN
        SR.code -> SR
        HR.code -> HR
        BS.code -> BS
        else -> EN
    }
}
