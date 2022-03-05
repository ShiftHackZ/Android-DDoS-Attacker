package com.shifthackz.android.attacker.validator.fields

import com.shifthackz.android.attacker.validator.ValidationError

class NumberValidationValidation(
    val emptyError: ValidationError,
    val zeroError: ValidationError
) : FieldValidation {

    override fun validate(value: String): ValidationError? = when {
        value.isEmpty() -> emptyError
        value.toInt() == 0 -> zeroError
        else -> null
    }
}