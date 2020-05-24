package br.com.farras.appzinho

import android.app.Application
import android.content.Context
import br.com.farras.appzinho.di.Modules
import com.google.firebase.FirebaseApp
import org.koin.android.ext.android.startKoin

class FarrasApplication: Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this@FarrasApplication)
        startKoin(androidContext = this@FarrasApplication, modules = Modules.all)
        appContext = applicationContext
    }
}