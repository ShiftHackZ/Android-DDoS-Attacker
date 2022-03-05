package com.shifthackz.android.attacker.validator

import com.shifthackz.android.attacker.R

enum class ValidationError(val error: Int) {
    INVALID_IP_ADDRESS(R.string.error_invalid_ip),
    INVALID_DOMAIN(R.string.error_invalid_domain),

    EMPTY_TIMEOUT(R.string.empty_timeout),
    ZERO_TIMEOUT(R.string.zero_timeout),

    EMPTY_THREADS(R.string.empty_threads),
    ZERO_THREADS(R.string.zero_threads),

    EMPTY_PORT(R.string.empty_port),
    ZERO_PORT(R.string.zero_port),
    LARGE_PORT(R.string.large_port),

    NONE(0)
}