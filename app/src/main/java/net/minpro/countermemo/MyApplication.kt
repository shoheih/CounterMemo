package net.minpro.countermemo

import android.app.Application
import android.content.Context
import io.realm.Realm

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Realm.init(this)
    }

    companion object {
        lateinit var appContext: Context
    }
}