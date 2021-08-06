package com.example.sharedpreferences

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var accessKey = TestClass(
            token = "pedro",
            refreshToken = "pedrito",
            expireTime = 10
        )
        var keyStringAccesskey = "Access_key"
        var gson = Gson()
        var jsonAccessKey = gson.toJson(accessKey)
        var myEncryptedSharedPreferences = EncSharedPreferences.encryptedSharedPreferencesInstance(applicationContext)
        EncSharedPreferences.saveToSharedPrefs(applicationContext,keyStringAccesskey,jsonAccessKey)
        var returnedStringFromEncSharedPrefs = EncSharedPreferences.getValueString(applicationContext,keyStringAccesskey)
        Log.e("RETURNED STRING FROM ENC SHARED PREFS ","$returnedStringFromEncSharedPrefs")
        var jsonObjectAccessKey = gson.fromJson(returnedStringFromEncSharedPrefs,TestClass::class.java)
        Log.e("STRING FROM ENC SHARED PREFS TO OBJECT","$jsonObjectAccessKey")
    }
}