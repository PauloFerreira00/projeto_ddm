package br.com.farras.appzinho.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName
import java.io.Serializable

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo @PropertyName("name") val name: String = "",
    @ColumnInfo @PropertyName("date") val date: String = "-",
    @ColumnInfo @PropertyName("latitude") val latitude: Double = 0.0,
    @ColumnInfo @PropertyName("longitude") val longitude: Double = 0.0
): Serializable