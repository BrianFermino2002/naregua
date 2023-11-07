package com.example.naregua

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

    override fun onTerminate() {
        super.onTerminate()

        FirebaseAuth.getInstance().signOut()
    }

}