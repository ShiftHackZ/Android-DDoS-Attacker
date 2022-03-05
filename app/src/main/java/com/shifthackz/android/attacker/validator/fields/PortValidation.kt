package com.shifthackz.android.attacker.validator.fields

import com.shifthackz.android.attacker.validator.ValidationError

class PortValidation : FieldValidation {

    override fun validate(value: String): ValidationError? = when {
        value.isEmpty() -> ValidationError.EMPTY_PORT
        value.toInt() == 0 -> ValidationError.ZERO_PORT
        value.toInt() > MAX_PORT -> ValidationError.LARGE_PORT
        else -> null
    }

    companion object {
        private const val MAX_PORT = 65535
    }
}