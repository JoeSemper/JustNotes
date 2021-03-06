package com.joesemper.justnotes.presentation

import androidx.lifecycle.*
import com.joesemper.justnotes.data.NotesRepository
import com.joesemper.justnotes.data.model.Color
import com.joesemper.justnotes.data.model.Note
import kotlinx.coroutines.launch

class NoteViewModel(private val notesRepository: NotesRepository, var note: Note?) : ViewModel() {

    private val showErrorLiveData = MutableLiveData<Boolean>()

    private val lifecycleOwner: LifecycleOwner = LifecycleOwner { viewModelLifecycle }
    private val viewModelLifecycle = LifecycleRegistry(lifecycleOwner).also {
        it.currentState = Lifecycle.State.RESUMED
    }

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    fun updateColor(color: Color) {
        note = (note ?: generateNote()).copy(color = color)
    }

    fun saveNote() {
        viewModelScope.launch {
            val noteValue = note ?: return@launch

            try {
                notesRepository.addOrReplaceNote(noteValue)
            } catch (th: Throwable) {
                showErrorLiveData.value = true
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            try {
                notesRepository.deleteNote(noteId)
            } catch (th: Throwable) {
                showErrorLiveData.value = true
            }
        }
    }

    fun showError(): LiveData<Boolean> = showErrorLiveData

    override fun onCleared() {
        super.onCleared()
        viewModelLifecycle.currentState = Lifecycle.State.DESTROYED
    }

    private fun generateNote(): Note {
        return Note()
    }
}