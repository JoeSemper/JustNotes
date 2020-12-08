package com.joesemper.justnotes.data

import androidx.lifecycle.LiveData
import com.joesemper.justnotes.data.model.User
import com.joesemper.justnotes.data.model.Note

interface NotesRepository {
    fun getCurrentUser(): User?
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>>
    fun deleteNote(noteId: String): LiveData<Result<Unit>>
}