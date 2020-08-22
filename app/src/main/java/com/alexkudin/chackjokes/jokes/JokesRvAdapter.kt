package com.alexkudin.chackjokes.jokes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexkudin.chackjokes.JokesDiffUtilCallBack
import com.alexkudin.chackjokes.data.Joke
import com.alexkudin.chackjokes.databinding.RvCardJokeBinding

/**
 * Attention! Elements change their position and recycler scroll to the relevant position
 * after reloading because the request to API get jokes in random order.
 * It isn't a bug or bad user experience (I think).
 * */

class JokesRvAdapter(
    diffUtilCallBack: JokesDiffUtilCallBack
): ListAdapter<Joke, JokesRvAdapter.JokesViewHolder>(diffUtilCallBack) {

    init {
        setHasStableIds(true)
    }


    override fun getItemId(position: Int) = getItem(position).id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        val binding = RvCardJokeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JokesViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        holder.bind()
    }

    inner class JokesViewHolder(
        private val binding: RvCardJokeBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.joke = getItem(adapterPosition)
            binding.executePendingBindings()
        }
    }
}