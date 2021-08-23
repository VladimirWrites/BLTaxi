package com.vlad1m1r.bltaxi.domain

enum class Language(val code: String) {
    EN("en"),
    SR("sr"),
    HR("hr"),
    BS("bs");

    companion object {
        fun fromCode(code: String): Language {
            return when(code) {
                EN.code -> EN
                SR.code -> SR
                HR.code -> HR
                BS.code -> BS
                else -> EN
            }
        }
    }
}
