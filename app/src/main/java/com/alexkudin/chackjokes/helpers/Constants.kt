package com.alexkudin.chackjokes.helpers

import android.os.Build

object Constants {

    // API fields
    const val JOKES_FIELD_ID = "id"
    const val JOKES_FIELD_TEXT = "joke"
    const val JOKES_FIELD_ARRAY = "value"

    // URL's
    const val DOCUMENTATION_URL = "http://www.icndb.com/api/"
    const val BASE_URL = "https://api.icndb.com/"

    val USER_AGENT = "Mozilla/5.0 (Linux; U; Android ${Build.VERSION.RELEASE}; en-us; " +
            "${Build.BRAND} ${Build.MODEL} Build/FRG83) AppleWebKit/533.1 (KHTML, like Gecko) " +
            "Version/4.0 Mobile Safari/533.1"
}