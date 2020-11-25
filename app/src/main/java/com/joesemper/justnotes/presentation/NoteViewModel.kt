package com.joesemper.justnotes.presentation

import androidx.lifecycle.ViewModel
import com.joesemper.justnotes.data.Repository
import com.joesemper.justnotes.data.model.Color
import com.joesemper.justnotes.data.model.Note

class NoteViewModel(var note: Note?) : ViewModel() {

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    fun updateColor(color: Color) {
        note = (note ?: generateNote()).copy(color = color)
    }

    override fun onCleared() {
        super.onCleared()

        note?.let {
            Repository.addOrReplaceNote(it)
        }
    }

    private fun generateNote(): Note {
        return Note()
    }
}