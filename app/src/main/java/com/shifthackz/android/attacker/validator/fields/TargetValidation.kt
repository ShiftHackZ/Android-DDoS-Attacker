package com.shifthackz.android.attacker.validator.fields

import com.shifthackz.android.attacker.validator.ValidationError

class TargetValidation : FieldValidation {

    override fun validate(value: String): ValidationError? = when (detectTargetType(value)) {
        Type.IP -> validateIp(value)
        Type.DOMAIN -> validateDomain(value)
    }

    private fun validateDomain(domain: String): ValidationError? {
        val regexDomain = Regex(REGEX_DOMAIN)
        return if (!domain.matches(regexDomain)) {
            ValidationError.INVALID_DOMAIN
        } else {
            null
        }
    }

    private fun validateIp(ip: String): ValidationError? {
        val regexIp = Regex(REGEX_IP)
        return if (!ip.matches(regexIp)) {
            ValidationError.INVALID_IP_ADDRESS
        } else {
            null
        }
    }

    private fun detectTargetType(value: String): Type = when {
        hasOnlyDigits(value) -> Type.IP
        else -> Type.DOMAIN
    }

    private fun hasOnlyDigits(value: String): Boolean {
        val parsedInt = value.replace(".", "").toLongOrNull()
        return parsedInt?.let { true } ?: false
    }

    enum class Type {
        IP, DOMAIN
    }

    companion object {
        private const val REGEX_IP = "\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|\$)){4}\\b"
        private const val REGEX_DOMAIN = "^(([a-zA-Z]{1})|([a-zA-Z]{1}[a-zA-Z]{1})|([a-zA-Z]{1}[0-9]{1})|([0-9]{1}[a-zA-Z]{1})|([a-zA-Z0-9][a-zA-Z0-9-_]{1,61}[a-zA-Z0-9]))\\.([a-zA-Z]{2,6}|[a-zA-Z0-9-]{2,30}\\.[a-zA-Z]{2,3})\$"
    }
}