package br.com.farras.appzinho.features.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.farras.appzinho.database.AppDatabase
import br.com.farras.appzinho.models.Error
import br.com.farras.appzinho.models.Event
import com.google.firebase.firestore.FirebaseFirestore
import br.com.farras.appzinho.models.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterViewModel(private val firestore: FirebaseFirestore, private val database: AppDatabase): ViewModel() {

    val dao = database.eventDao()

    fun register(event: Event): LiveData<Result<String>> {
        val liveData = MutableLiveData<Result<String>>()
        val eventHashMap = hashMapOf(
            "name" to event.name,
            "date" to event.date,
            "latitude" to event.latitude,
            "longitude" to event.longitude
        )

        firestore.collection("events")
            .add(eventHashMap as Map<String, Any>)
            .addOnSuccessListener {
                liveData.value =
                Result(success = "Cadastro realizado com sucesso!")
                GlobalScope.launch {
                    dao.insert(event)
                }
            }
            .addOnFailureListener { liveData.value = Result(failure = Error(message = "Problema ao realizar cadastro, tente novamente!")) }

        return liveData
    }

    fun registerOnLocal(event: Event): LiveData<Result<String>> {
        val liveData = MutableLiveData<Result<String>>()

        GlobalScope.launch {
            dao.insert(event)
            liveData.postValue(Result(success = "Cadastro realizado com sucesso!"))
        }

        return liveData
    }
}