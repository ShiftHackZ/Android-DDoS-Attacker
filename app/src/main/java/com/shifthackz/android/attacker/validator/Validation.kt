package com.shifthackz.android.attacker.validator

import com.shifthackz.android.attacker.attack.contract.AttackParam

interface Validation {

    fun validate(
        param: AttackParam = AttackParam.TARGET,
        value: String = "",
        onValid: () -> Unit = {},
        onError: (ValidationError) -> Unit = { _ -> }
    )
}