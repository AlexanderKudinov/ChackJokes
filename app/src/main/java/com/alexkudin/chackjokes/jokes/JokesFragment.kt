package com.alexkudin.chackjokes.jokes

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.alexkudin.chackjokes.JokesDiffUtilCallBack
import com.alexkudin.chackjokes.R
import com.alexkudin.chackjokes.data.Joke
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_jokes.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class JokesFragment: MvpAppCompatFragment(R.layout.fragment_jokes), IJokesView {

    private val adapter = JokesRvAdapter(JokesDiffUtilCallBack())
    private var jokesCount = 0

    @InjectPresenter
    lateinit var presenter: JokesPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        rvJokes.adapter = adapter
    }





    private fun setListeners() {
        btnReloadJokes.setOnClickListener {
            if (!swipeRefresh.isRefreshing && isCountValid()) {
                presenter.downloadChackJokes(
                    count = if (isCountEmpty()) 1000
                            else getJokesCount()
                )
                swipeRefresh.isRefreshing = true
            }
        }

        swipeRefresh.setOnRefreshListener {
            if (isCountValid()) {
                presenter.downloadChackJokes(
                    count = if (isCountEmpty()) 1000
                            else getJokesCount()
                )
            } else
                swipeRefresh.isRefreshing = false
        }
    }


    private fun isCountValid() = inputJokes.text.toString().length <= 3

    private fun isCountEmpty() = inputJokes.text.toString().isEmpty()

    private fun getJokesCount(): Int {
        try {
            return inputJokes.text.toString().toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            return 1000
        }
    }



    /** implementations of JokesView */

    override fun onLoading() {
        swipeRefresh.isRefreshing = true
        textNoJokes.apply {
            visibility = VISIBLE
            text = resources.getString(R.string.pleaseWait)
        }
    }

    override fun successfulLoading(jokes: ArrayList<Joke>) {
        swipeRefresh.isRefreshing = false
        textNoJokes.text = resources.getString(R.string.noJokes)
        if (jokes.isNotEmpty())
            textNoJokes.visibility = GONE
        adapter.submitList(jokes)
        jokesCount = jokes.size
    }

    override fun errorLoading() {
        swipeRefresh.isRefreshing = false
        if (jokesCount == 0) {
            textNoJokes.apply {
                visibility = VISIBLE
                text = resources.getString(R.string.noJokes)
            }
        }
        else
            textNoJokes.visibility = GONE
        Snackbar.make(requireView(), resources.getString(R.string.errorDownloading), Snackbar.LENGTH_LONG).show()
    }
}