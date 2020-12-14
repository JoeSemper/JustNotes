package com.joesemper.justnotes.data.db

import androidx.lifecycle.LiveData
import com.joesemper.justnotes.data.model.Note
import com.joesemper.justnotes.data.model.User

interface DatabaseProvider {
    fun getCurrentUser(): User?
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>>
    fun deleteNote(noteId: String): LiveData<Result<Unit>>
}