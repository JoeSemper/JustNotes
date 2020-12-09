package com.joesemper.justnotes.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joesemper.justnotes.data.NotesRepository
import com.joesemper.justnotes.data.model.Note
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception


class MainViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()


    private val notesRepositoryMock = mockk<NotesRepository>()

    private var _resultLiveData = MutableLiveData(
        listOf<Note>()
    )
    private val resultLiveData: LiveData<List<Note>> get() = _resultLiveData

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        every { notesRepositoryMock.observeNotes() } returns resultLiveData
        viewModel = MainViewModel(notesRepositoryMock)
    }

    @Test
    fun `should call observeNotes once`() {
        verify(exactly = 0) { notesRepositoryMock.observeNotes() }
    }
}
