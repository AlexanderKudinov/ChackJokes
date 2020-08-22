package com.alexkudin.chackjokes.jokes

import com.alexkudin.chackjokes.data.Joke
import com.alexkudin.chackjokes.helpers.Constants.JOKES_FIELD_ARRAY
import com.alexkudin.chackjokes.helpers.parseToJokes
import com.alexkudin.chackjokes.server.RetrofitUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import moxy.viewstate.strategy.alias.SingleState

interface IJokesView: MvpView {

    @OneExecution
    fun onLoading()
    @SingleState
    fun successfulLoading(jokes: ArrayList<Joke>)
    @OneExecution
    fun errorLoading()
}

@InjectViewState
class JokesPresenter: MvpPresenter<IJokesView>() {

    private val disposableContainer = CompositeDisposable()

    init {
        downloadChackJokes()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableContainer.clear()
    }


    fun downloadChackJokes(count: Int = 1000) {
        viewState.onLoading()
        disposableContainer.add(
            RetrofitUtils.create()
                .getJokesByName(count = count, firstName = "Chack", lastName = "Noris")
                .flatMap { json ->
                    if (json.has(JOKES_FIELD_ARRAY)) {
                        return@flatMap Single.create<ArrayList<Joke>> {
                            val jokes = json.get(JOKES_FIELD_ARRAY).asJsonArray.parseToJokes()
                            it.onSuccess(jokes)
                        }
                    }
                    // if there are no jokes
                    throw Throwable()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.successfulLoading(jokes = it)
                }, {
                    viewState.errorLoading()
                    it.printStackTrace()
                })
        )
    }
}