package com.joesemper.justnotes.presentation

import com.joesemper.justnotes.data.model.Note

sealed class MainViewState {
    data class Value(val notes: List<Note>): MainViewState()
    object EMPTY  : MainViewState()
}