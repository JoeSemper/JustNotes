package com.joesemper.justnotes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joesemper.justnotes.data.Repository

class MainViewModel : ViewModel () {

    private val viewSateLiveData: MutableLiveData<MainViewState> = MutableLiveData()


    init {
        viewSateLiveData.value = MainViewState(Repository.notes)
    }

    fun viewState() : LiveData<MainViewState> = viewSateLiveData
}