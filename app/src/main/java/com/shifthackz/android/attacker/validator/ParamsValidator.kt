package com.shifthackz.android.attacker.validator

import com.shifthackz.android.attacker.attack.contract.AttackParam
import com.shifthackz.android.attacker.validator.fields.FieldValidation
import com.shifthackz.android.attacker.validator.fields.PortValidation
import com.shifthackz.android.attacker.validator.fields.TargetValidation
import com.shifthackz.android.attacker.validator.fields.NumberValidationValidation

class ParamsValidator : Validation {

    override fun validate(
        param: AttackParam,
        value: String,
        onValid: () -> Unit,
        onError: (ValidationError) -> Unit
    ) = with(getValidator(param)) {
        validate(value)?.let(onError::invoke) ?: onValid.invoke()
    }

    private fun getValidator(param: AttackParam): FieldValidation = when (param) {
        AttackParam.TARGET -> TargetValidation()
        AttackParam.PORT -> PortValidation()
        AttackParam.PROTOCOL -> FieldValidation { null }
        AttackParam.TIMEOUT -> NumberValidationValidation(
            ValidationError.EMPTY_TIMEOUT,
            ValidationError.ZERO_TIMEOUT
        )
        AttackParam.FLOOD_MSG -> FieldValidation { null }
        AttackParam.THREADS ->  NumberValidationValidation(
            ValidationError.EMPTY_THREADS,
            ValidationError.ZERO_THREADS
        )
    }
}