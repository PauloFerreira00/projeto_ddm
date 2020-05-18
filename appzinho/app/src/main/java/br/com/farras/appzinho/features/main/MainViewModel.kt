package br.com.farras.appzinho.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.farras.appzinho.models.Event
import com.google.firebase.firestore.FirebaseFirestore
import br.com.farras.appzinho.models.Result

class MainViewModel(private val firestore: FirebaseFirestore): ViewModel() {

    private val mutableLiveData = MutableLiveData<Result<List<Event>>>()

    fun getEvents(): MutableLiveData<Result<List<Event>>> {
        firestore.collection("events").get().addOnSuccessListener { document ->
            val events = document.toObjects(Event::class.java)
            mutableLiveData.value = Result(events)
        }
        return mutableLiveData
    }
}