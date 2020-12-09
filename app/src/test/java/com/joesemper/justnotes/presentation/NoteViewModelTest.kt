package com.joesemper.justnotes.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joesemper.justnotes.data.NotesRepository
import com.joesemper.justnotes.data.model.Note
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.*
import org.junit.rules.TestRule

class NoteViewModelTest {

    private val notesRepositoryMock = mockk<NotesRepository>()
    private lateinit var viewModel: NoteViewModel

    private var _resultLiveData = MutableLiveData(
        Result.success(
            Note()
        )
    )

    private val resultLiveData: LiveData<Result<Note>> get() = _resultLiveData

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { notesRepositoryMock.addOrReplaceNote(any()) } returns resultLiveData
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Error LiveData contains nothing after save`() {
        val currentNote = Note(title = "Hello!")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.saveNote()

        Assert.assertTrue(viewModel.showError().value == null)
    }

    @Test
    fun `Error LiveData contains value after save`() {
        every { notesRepositoryMock.addOrReplaceNote(any()) } returns MutableLiveData(
            Result.failure(
                IllegalAccessError()
            )
        )

        val currentNote = Note(title = "Hello!")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.saveNote()

        Assert.assertTrue(viewModel.showError().value != null)
    }

    @Test
    fun `ViewModel Note title changed`() {
        val currentNote = Note(title = "Hello!")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.updateTitle("Alloha")

        Assert.assertEquals("Alloha", viewModel.note?.title)
    }

    @Test
    fun `NotesRepository addOrReplaceNote called with correct title Note`() {
        viewModel = NoteViewModel(notesRepositoryMock, null)

        viewModel.updateNote("Hello!")
        viewModel.saveNote()

        verify(exactly = 1) {
            notesRepositoryMock.addOrReplaceNote(match { it.note == "Hello!" })
        }

    }
}