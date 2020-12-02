package com.joesemper.justnotes.data.db

import androidx.lifecycle.LiveData
import com.joesemper.justnotes.data.model.Note

interface DatabaseProvider {
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>>
}