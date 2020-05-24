package br.com.farras.appzinho.features.map


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.farras.appzinho.R
import br.com.farras.appzinho.helpers.Result
import br.com.farras.appzinho.helpers.Error
import br.com.farras.appzinho.models.Event
import com.google.firebase.firestore.FirebaseFirestore

class MapViewModel(private val firebaseDB: FirebaseFirestore): ViewModel() {

    fun getEvents(): LiveData<Result<List<Event>>> {
        val liveData = MutableLiveData<Result<List<Event>>>()
        firebaseDB.collection("events").get()
            .addOnSuccessListener { document ->
                val events = document.toObjects(Event::class.java)
                liveData.value = Result(events)
            }
            .addOnFailureListener {
                liveData.value = Result(failure = Error(message = "Ocorreu um erro!"))
            }
        return liveData
    }
}