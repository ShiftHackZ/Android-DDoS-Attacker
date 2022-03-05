package com.shifthackz.android.attacker.validator.fields

import com.shifthackz.android.attacker.validator.ValidationError

fun interface FieldValidation {
    fun validate(value: String): ValidationError?
}