package br.com.farras.appzinho.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.farras.appzinho.dao.EventDao
import br.com.farras.appzinho.models.Event

@Database(entities = [Event::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}

object Database {

}