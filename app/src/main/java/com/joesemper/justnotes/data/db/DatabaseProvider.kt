package com.joesemper.justnotes.data.db

import androidx.lifecycle.LiveData
import com.joesemper.justnotes.data.model.Note
import com.joesemper.justnotes.data.model.User
import kotlinx.coroutines.flow.Flow

interface DatabaseProvider {
    fun getCurrentUser(): User?
    fun observeNotes(): Flow<List<Note>>
    suspend fun addOrReplaceNote(newNote: Note)
    suspend fun deleteNote(noteId: String)
}