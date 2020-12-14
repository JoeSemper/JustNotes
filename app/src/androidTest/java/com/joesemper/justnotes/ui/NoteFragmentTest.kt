package com.joesemper.justnotes.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joesemper.justnotes.R
import com.joesemper.justnotes.data.model.Note
import com.joesemper.justnotes.presentation.NoteViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class NoteFragmentTest {

    private val mockViewModel: NoteViewModel = mockk(relaxed = true)

    @Before
    fun setup() {
        kotlin.runCatching { startKoin { } }

        loadKoinModules(
            module {
                viewModel { (_: Note?) ->
                    mockViewModel
                }
            }
        )
    }

    @After
    fun clean() {
        stopKoin()
    }

    @Test
    fun save_note_on_button_click() {
        launchFragmentInContainer<NoteFragment>(themeResId = R.style.Theme_AppCompat)

        Espresso.onView(withId(R.id.buttonApply)).perform(ViewActions.click())

        verify { mockViewModel.saveNote() }
    }

}