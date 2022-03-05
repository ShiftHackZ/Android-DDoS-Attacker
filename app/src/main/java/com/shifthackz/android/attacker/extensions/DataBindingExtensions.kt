package com.shifthackz.android.attacker.extensions

import androidx.databinding.Observable
import androidx.databinding.ObservableField

fun <T> onPropertyChangedCallback(
    field: ObservableField<T>,
    callback: (T) -> Unit
): Observable.OnPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
        field.get()?.let { callback(it) }
    }
}
