package com.joesemper.justnotes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.joesemper.justnotes.data.Repository

class MainViewModel : ViewModel() {
    fun observeViewState(): LiveData<MainViewState> = Repository.observeNotes()
        .map {
            if (it.isEmpty()) MainViewState.EMPTY else MainViewState.Value(it)
        }
}