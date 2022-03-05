package com.shifthackz.android.attacker.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        disposable.clear()
    }

    operator fun Disposable.not() {
        disposable.add(this)
    }

    fun Disposable.addToDisposable(): Disposable = this.apply(disposable::add)
}