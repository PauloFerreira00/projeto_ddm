package br.com.farras.appzinho.features.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.farras.appzinho.models.Error
import br.com.farras.appzinho.models.Event
import com.google.firebase.firestore.FirebaseFirestore
import br.com.farras.appzinho.models.Result

class RegisterViewModel(private val firestore: FirebaseFirestore): ViewModel() {

    fun register(event: Event): LiveData<Result<String>> {
        val liveData = MutableLiveData<Result<String>>()
        val event = hashMapOf(
            "name" to event.name,
            "date" to event.date
        )

        firestore.collection("events")
            .add(event as Map<String, Any>)
            .addOnSuccessListener {
                liveData.value =
                Result(success = "Cadastro realizado com sucesso!")
            }
            .addOnFailureListener { liveData.value = Result(failure = Error(message = "Problema ao realizar cadastro, tente novamente!")) }

        return liveData
    }

}