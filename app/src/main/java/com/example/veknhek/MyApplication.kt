package com.example.veknhek

import android.app.Application
import com.example.veknhek.utility.PreferencesUtil
import com.google.firebase.Firebase
import com.google.firebase.initialize

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferencesUtil.init(this)
        Firebase.initialize(this)
    }
}

/*
Newsfeed:
    - Firebase.firestore.collection("newsfeed")

Comment in each Newsfeed
    - Firebase.firestore.collection("newsfeed").doc("news feed id").collection("comments")
    - Firebase.firestore.collection("newsfeed").doc("news feed id").collection("reactions")

 */