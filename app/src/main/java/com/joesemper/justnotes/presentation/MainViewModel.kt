package com.joesemper.justnotes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.joesemper.justnotes.data.Repository
import com.joesemper.justnotes.data.notesRepository

class MainViewModel : ViewModel() {
    fun observeViewState(): LiveData<MainViewState> = notesRepository.observeNotes()
        .map {
            if (it.isEmpty()) MainViewState.EMPTY else MainViewState.Value(it)
        }
}