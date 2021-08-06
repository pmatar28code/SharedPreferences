package com.example.sharedpreferences

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson

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

        fun saveToEncryptedSharedPrefsString(KEY_NAME: String, value: String) {
            val encSharedPreferences = encryptedSharedPreferencesInstance()
            val editor = encSharedPreferences?.edit()
            editor?.putString(KEY_NAME, value)
            editor?.apply()
        }

        fun getValueString(KEY_NAME: String): String? {
            val encSharedPreferences = encryptedSharedPreferencesInstance()
            return encSharedPreferences?.getString(KEY_NAME, null)
        }

        fun convertTestClassToJsonString(classObj:TestClass):String{
            var gson = Gson()
            return gson.toJson(classObj)
        }

        fun convertJsonStringToTestClass(stringObj:String):TestClass{
            var gson = Gson()
            return gson.fromJson(stringObj,TestClass::class.java)
        }

        fun removeValueFromEncShareDPrefs(keyString: String) {
            val encSharedPreferences = encryptedSharedPreferencesInstance()
            val editor = encSharedPreferences?.edit()
            editor?.remove(keyString)
            editor?.apply()
        }

        fun clearSharedPreference() {
            val encSharedPreferences = encryptedSharedPreferencesInstance()
            val editor = encSharedPreferences?.edit()
            editor?.clear()
            editor?.commit()
        }
    }
}