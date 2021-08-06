package com.example.sharedpreferences

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncSharedPreferences {
    companion object{
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    private var INSTANCE : SharedPreferences ?= null
    private var applicationContext = Utility.getInstance()?.applicationContext

        fun encryptedSharedPreferencesInstance(): SharedPreferences? {
            var instance = INSTANCE
            if (instance == null) {
                instance = applicationContext?.let {
                    EncryptedSharedPreferences.create(
                        "shared_preferences_filename",
                        masterKeyAlias,
                        it,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    )
                }
                INSTANCE = instance
            }
            return instance
        }

        fun saveToSharedPrefs(KEY_NAME: String, value: String) {
            val encSharedPreferences = encryptedSharedPreferencesInstance()
            val editor = encSharedPreferences?.edit()
            editor?.putString(KEY_NAME, value)
            editor?.apply()
        }

        fun getValueString(KEY_NAME: String): String? {
            val encSharedPreferences = encryptedSharedPreferencesInstance()
            return encSharedPreferences?.getString(KEY_NAME, null)
        }
    }
}