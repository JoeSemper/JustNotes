package com.joesemper.justnotes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joesemper.justnotes.data.db.FireStoreDatabaseProvider
import com.joesemper.justnotes.data.model.Note
import kotlin.random.Random

private val idRandom = Random(0)
val noteId: Long
    get() = idRandom.nextLong()

class Repository(val provider: FireStoreDatabaseProvider) : NotesRepository {
    override fun observeNotes(): LiveData<List<Note>> {
        return provider.observeNotes()
    }

    override fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>> {
        return provider.addOrReplaceNote(newNote)
    }
}

val notesRepository: NotesRepository by lazy { Repository(FireStoreDatabaseProvider()) }
