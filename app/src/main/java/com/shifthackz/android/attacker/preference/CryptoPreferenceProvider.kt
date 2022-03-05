package com.shifthackz.android.attacker.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object CryptoPreferenceProvider {

    private const val SECRET_SHARED_PREFS = "secret_shared_prefs"

    fun getPrefs(context: Context): SharedPreferences {
        val masterKeyAlias = getMasterKeyAlias()
        return EncryptedSharedPreferences.create(
            SECRET_SHARED_PREFS,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun getMasterKeyAlias() = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
}
