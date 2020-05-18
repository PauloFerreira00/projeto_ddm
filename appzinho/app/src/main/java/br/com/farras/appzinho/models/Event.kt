package br.com.farras.appzinho.models

import com.google.firebase.firestore.PropertyName
import java.io.Serializable

data class Event(
    @PropertyName("name") val name: String = "",
    @PropertyName("date") val date: String = "-"
): Serializable