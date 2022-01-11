package com.example.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.dao.NoteDao
import com.example.myapplication.data.model.Note
import com.example.myapplication.utils.Constants.NOTES_DATABASE_NAME

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NotesDatabase(): RoomDatabase() {

    // NoteDao
    abstract fun notesDao(): NoteDao

    // used to make the underlying objects singleton
    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            // if the instance is null then create a database and return
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    NOTES_DATABASE_NAME
                ).build()

                INSTANCE = instance
                //return instance
                instance
            }
        }
    }

}