package br.com.farras.appzinho.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.farras.appzinho.database.AppDatabase
import br.com.farras.appzinho.models.Event
import com.google.firebase.firestore.FirebaseFirestore
import br.com.farras.appzinho.models.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(private val firestore: FirebaseFirestore, private val database: AppDatabase): ViewModel() {

    val dao = database.eventDao()

    private val mutableLiveData = MutableLiveData<Result<List<Event>>>()

    fun getEvents(): MutableLiveData<Result<List<Event>>> {
        firestore.collection("events").get().addOnSuccessListener { document ->
            val events = document.toObjects(Event::class.java)
            mutableLiveData.value = Result(events)
            GlobalScope.launch {
                dao.deleteAll()
                dao.createAll(events)
            }
        }
        return mutableLiveData
    }

    fun getEventsFromLocal(): MutableLiveData<Result<List<Event>>> {
        GlobalScope.launch {
            val events = dao.getAll()
            mutableLiveData.postValue(Result(success = events))
        }
        return mutableLiveData
    }
}