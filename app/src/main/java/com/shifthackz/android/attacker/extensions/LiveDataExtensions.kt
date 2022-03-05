package com.shifthackz.android.attacker.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T : Any?> LiveData<T>.observeNonNull(owner: LifecycleOwner, action: (T) -> Unit) {
    observe(owner) { it?.let(action::invoke) }
}