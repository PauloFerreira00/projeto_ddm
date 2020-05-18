package br.com.farras.appzinho.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.farras.appzinho.helpers.ViewState
import com.google.firebase.auth.FirebaseAuth
import br.com.farras.appzinho.models.Result
import br.com.farras.appzinho.models.Error

class LoginViewModel(private val firebaseAuth: FirebaseAuth): ViewModel() {

    val state: LiveData<ViewState> get() = _state
    private val _state: MutableLiveData<ViewState> = MutableLiveData()

    fun login(username: String = "", password: String = ""): LiveData<Result<String>> {
        _state.postValue(ViewState.LOADING)

        val liveData = MutableLiveData<Result<String>>()
        firebaseAuth.signInWithEmailAndPassword("$username@teste.com", password).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                liveData.value = Result(success = "")
            } else {
                liveData.value = Result(failure = Error(message = "Usuário e/ou senha incorretos!"))
            }
        }
        return liveData
    }

}