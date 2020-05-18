package br.com.farras.appzinho.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.farras.appzinho.models.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getAll(): List<Event>

    @Insert
    @JvmSuppressWildcards
    fun createAll(objects: List<Event>)

    @Insert
    fun insert(event: Event)

    @Query("DELETE FROM event")
    fun deleteAll()

    @Delete
    fun delete(user: Event)
}