package br.com.farras.appzinho.di

import br.com.farras.appzinho.features.login.LoginViewModel
import br.com.farras.appzinho.features.main.MainViewModel
import br.com.farras.appzinho.features.register.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object Modules {

    private val viewModels = module {
        viewModel { LoginViewModel(get(name = "FirebaseAuth")) }

        viewModel { RegisterViewModel(get(name = "FirebaseFirestore")) }

        viewModel { MainViewModel(get(name = "FirebaseFirestore")) }
    }

    private val general = module {
        single(name = "FirebaseAuth") { FirebaseAuth.getInstance() }

        single(name = "FirebaseFirestore") { FirebaseFirestore.getInstance() }
    }

    val all = listOf(viewModels, general)
}