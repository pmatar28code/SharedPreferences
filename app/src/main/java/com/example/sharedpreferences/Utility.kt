package com.example.sharedpreferences

import android.app.Application
import android.content.Context

 class Utility:Application(){
     companion object {
         private var appContext: Context? = null
         fun getAppContext(): Context? {
             return appContext
         }
     }
     override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        /* If you has other classes that need context object to initialize when application is created,
         you can use the appContext here to process. */
    }
}