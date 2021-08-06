package com.example.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncSharedPreferences {
    companion object{
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    private var INSTANCE : SharedPreferences ?= null
    //private var applicationContext = Utility.getAppContext()

        fun encryptedSharedPreferencesInstance(applicationContext: Context):SharedPreferences {
            var instance = INSTANCE
            if (instance == null) {
                instance = EncryptedSharedPreferences.create(
                    "shared_preferences_filename",
                    masterKeyAlias,
                    applicationContext,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
                INSTANCE = instance
            }
            return instance
        }

        fun saveToSharedPrefs(applicationContext: Context,KEY_NAME: String, value: String) {
            val encSharedPreferences = encryptedSharedPreferencesInstance(applicationContext)
            val editor = encSharedPreferences.edit()
            editor.putString(KEY_NAME, value)
            editor.apply()
        }

        fun getValueString(applicationContext: Context,KEY_NAME: String): String? {
            val encSharedPreferences = encryptedSharedPreferencesInstance(applicationContext)
            return encSharedPreferences.getString(KEY_NAME, null)
        }
    }
}