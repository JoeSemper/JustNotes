package com.joesemper.justnotes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.justnotes.data.NotesRepository
import com.joesemper.justnotes.errors.NoAuthException
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class SplashViewModel(private val repository: NotesRepository) : ViewModel() {

    private val viewStateLiveData = MutableLiveData<SplashViewState>()

    init {
        viewModelScope.launch {
            val user = repository.getCurrentUser()

            viewStateLiveData.value = if (user != null) {
                SplashViewState.Auth
            } else {
                SplashViewState.Error(error = NoAuthException())
            }
        }
    }

    fun observeViewState(): LiveData<SplashViewState> = viewStateLiveData
}