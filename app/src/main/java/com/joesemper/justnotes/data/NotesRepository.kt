package com.joesemper.justnotes.data

import com.joesemper.justnotes.data.model.User
import com.joesemper.justnotes.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun getCurrentUser(): User?
    fun observeNotes(): Flow<List<Note>>
    suspend fun addOrReplaceNote(newNote: Note)
    suspend fun deleteNote(noteId: String)
}