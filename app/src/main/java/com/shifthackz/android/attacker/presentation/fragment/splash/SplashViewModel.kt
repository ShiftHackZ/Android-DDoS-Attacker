package com.shifthackz.android.attacker.presentation.fragment.splash

import com.shifthackz.android.attacker.base.BaseViewModel
import com.shifthackz.android.attacker.database.dao.LogDao
import com.shifthackz.android.attacker.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashViewModel(
    dao: LogDao
) : BaseViewModel() {

    val onNextRoute = SingleLiveEvent<Unit>()

    init {
        Observable.timer(SPLASH_DELAY, TimeUnit.SECONDS)
            .flatMapCompletable { dao.clear() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onNextRoute.value = Unit }
            .addToDisposable()
    }

    companion object {
        private const val SPLASH_DELAY = 3L
    }
}