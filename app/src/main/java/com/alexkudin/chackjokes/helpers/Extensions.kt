package com.alexkudin.chackjokes.helpers

import com.alexkudin.chackjokes.data.Joke
import com.alexkudin.chackjokes.helpers.Constants.JOKES_FIELD_ID
import com.alexkudin.chackjokes.helpers.Constants.JOKES_FIELD_TEXT
import com.google.gson.JsonArray

fun JsonArray.parseToJokes(): ArrayList<Joke> {
    val jokes = arrayListOf<Joke>()
    forEach {
        try {
            jokes.add(
                Joke(
                    id = it.asJsonObject.get(JOKES_FIELD_ID).asLong,
                    text = it.asJsonObject.get(JOKES_FIELD_TEXT).asString
                )
            )
        } catch (e: ClassCastException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    return jokes
}