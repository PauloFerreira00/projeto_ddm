package br.com.farras.appzinho

import android.app.Application
import br.com.farras.appzinho.di.Modules
import com.google.firebase.FirebaseApp
import org.koin.android.ext.android.startKoin

class FarrasApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this@FarrasApplication)
        startKoin(androidContext = this@FarrasApplication, modules = Modules.all)
    }
}