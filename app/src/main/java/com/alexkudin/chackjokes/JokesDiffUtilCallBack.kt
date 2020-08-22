package com.alexkudin.chackjokes

import androidx.recyclerview.widget.DiffUtil
import com.alexkudin.chackjokes.data.Joke

class JokesDiffUtilCallBack: DiffUtil.ItemCallback<Joke>() {

    override fun areItemsTheSame(oldItem: Joke, newItem: Joke) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke) = oldItem == newItem
}