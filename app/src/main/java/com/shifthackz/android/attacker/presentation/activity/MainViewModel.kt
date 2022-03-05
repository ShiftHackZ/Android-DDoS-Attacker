package com.shifthackz.android.attacker.presentation.activity

import com.shifthackz.android.attacker.attack.coordinator.AttackCoordinator
import com.shifthackz.android.attacker.base.BaseViewModel
import com.shifthackz.android.attacker.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(coordinator: AttackCoordinator) : BaseViewModel() {

    val isRunning = SingleLiveEvent<Boolean>()

    init {
        !coordinator.observeState()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, Throwable::printStackTrace)
    }
}