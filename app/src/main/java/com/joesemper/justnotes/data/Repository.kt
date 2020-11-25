package com.joesemper.justnotes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joesemper.justnotes.data.model.Note
import kotlin.random.Random

private val idRandom = Random(0)
val noteId: Long
    get() = idRandom.nextLong()

object Repository : NotesRepository {
    private val notes: MutableList<Note> = mutableListOf()

    private val allNotes = MutableLiveData(getListForNotify())

    override fun observeNotes(): LiveData<List<Note>> {
        return allNotes
    }

    override fun addOrReplaceNote(newNote: Note) {
        notes.find { it.id == newNote.id }?.let {
            if (it == newNote) return
            notes.remove(it)
        }

        notes.add(newNote)

        allNotes.postValue(
            getListForNotify()
        )
    }

    private fun getListForNotify(): List<Note> = notes.toMutableList().also {
        it.reverse()
    }
}