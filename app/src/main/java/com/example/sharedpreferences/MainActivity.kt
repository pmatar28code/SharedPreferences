package com.example.sharedpreferences

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val accessKey = TestClass(
            token = "pedro",
            refreshToken = "pedrito",
            expireTime = 10
        )
        val keyStringAccesskey = "Access_key"
        val jsonAccessKey = EncSharedPreferences.convertTestClassToJsonString(accessKey)
        EncSharedPreferences.saveToEncryptedSharedPrefsString(keyStringAccesskey,jsonAccessKey)
        val returnedStringFromEncSharedPrefs = EncSharedPreferences.getValueString(keyStringAccesskey)
        Log.e("RETURNED STRING FROM ENC SHARED PREFS ","$returnedStringFromEncSharedPrefs")
        val jsonObjectAccessKey = returnedStringFromEncSharedPrefs?.let {
            EncSharedPreferences.convertJsonStringToTestClass(
                it
            )
        }
        Log.e("STRING FROM ENC SHARED PREFS TO OBJECT","$jsonObjectAccessKey")
        //EncSharedPreferences.removeValueFromEncShareDPrefs(keyStringAccesskey)
        //EncSharedPreferences.clearSharedPreference()
    }
}