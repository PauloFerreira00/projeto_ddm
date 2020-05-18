package br.com.farras.appzinho.di

import androidx.room.Room
import br.com.farras.appzinho.database.AppDatabase
import br.com.farras.appzinho.features.login.LoginViewModel
import br.com.farras.appzinho.features.main.MainViewModel
import br.com.farras.appzinho.features.register.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object Modules {

    private val viewModels = module {
        viewModel { LoginViewModel(get(name = "FirebaseAuth")) }

        viewModel { RegisterViewModel(get(name = "FirebaseFirestore"), get(name = "appDatabase")) }

        viewModel { MainViewModel(get(name = "FirebaseFirestore"), get(name = "appDatabase")) }
    }

    private val general = module {

        single("appDatabase") {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
                .build()
        }

        single { get<AppDatabase>().eventDao() }

        single(name = "FirebaseAuth") { FirebaseAuth.getInstance() }

        single(name = "FirebaseFirestore") { FirebaseFirestore.getInstance() }
    }

    val all = listOf(viewModels, general)
}