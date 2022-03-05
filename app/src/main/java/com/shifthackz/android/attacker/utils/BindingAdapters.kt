package com.shifthackz.android.attacker.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.shifthackz.android.attacker.R
import com.shifthackz.android.attacker.attack.contract.AttackProtocol
import com.shifthackz.android.attacker.attack.contract.AttackType
import com.shifthackz.android.attacker.database.entity.LogEntity
import com.shifthackz.android.attacker.presentation.widget.DropdownAdapter
import com.shifthackz.android.attacker.validator.ValidationError
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("validationError")
fun TextInputLayout.bindValidationError(error: ValidationError?) {
    isErrorEnabled = error != null && error.error != 0
    setError(error?.error?.takeIf { it != 0 }?.let { context.getString(it) })
}

@BindingAdapter("dropdownItems")
fun AppCompatAutoCompleteTextView.bindDropdownItems(items: List<String>?) {
    val dropdownAdapter = DropdownAdapter(
        context,
        R.layout.item_dropdown,
        items?.toTypedArray() ?: emptyArray()
    )
    setDropDownBackgroundResource(R.color.colorInput)
    setAdapter(dropdownAdapter)
}

@BindingAdapter("dropdownAttack")
fun AppCompatAutoCompleteTextView.bindDropdownAttack(attack: AttackType?) {
    setText(attack?.attackName?.takeIf { it.isNotEmpty() } ?: "unknown")
    dismissDropDown()
}

@BindingAdapter("dropdownProtocol")
fun AppCompatAutoCompleteTextView.bindDropdownAttack(protocol: AttackProtocol?) {
    setText(protocol?.protocol?.takeIf { it.isNotEmpty() } ?: "unknown")
    dismissDropDown()
}

@SuppressLint("SetTextI18n")
@BindingAdapter("logItem")
fun TextView.bindLogItem(entity: LogEntity) {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    text = "${sdf.format(Date(entity.timestamp))} | ${entity.message}"
    setTextColor(ContextCompat.getColor(context, entity.color))
}

@BindingAdapter(value = ["formAttack", "formCurrent"], requireAll = true)
fun View.bindFormAttack(showOnAttack: AttackType?, currentAttack: AttackType?) {
    visibility = when {
        showOnAttack == currentAttack && showOnAttack != null && currentAttack != null -> View.VISIBLE
        else -> View.GONE
    }
}