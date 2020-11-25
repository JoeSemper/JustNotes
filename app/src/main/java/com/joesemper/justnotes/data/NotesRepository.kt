package com.joesemper.justnotes.data

import androidx.lifecycle.LiveData
import com.joesemper.justnotes.data.model.Note

interface NotesRepository {
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note)
}