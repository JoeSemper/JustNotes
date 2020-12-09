package com.joesemper.justnotes.presentation

import androidx.lifecycle.*
import com.joesemper.justnotes.data.NotesRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(private val notesRepository: NotesRepository)  : ViewModel() {

    private val notesLiveData = MutableLiveData<MainViewState>()

    init {
        notesRepository.observeNotes()
            .onEach {
                notesLiveData.value = if (it.isEmpty()) MainViewState.EMPTY else MainViewState.Value(it)
            }
            .launchIn(viewModelScope)
    }

    fun observeViewState(): LiveData<MainViewState> = notesLiveData
}