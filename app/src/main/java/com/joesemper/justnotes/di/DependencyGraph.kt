package com.joesemper.justnotes.di

import com.joesemper.justnotes.data.NotesRepository
import com.joesemper.justnotes.data.NotesRepositoryImpl
import com.joesemper.justnotes.data.db.DatabaseProvider
import com.joesemper.justnotes.data.db.FireStoreDatabaseProvider
import com.joesemper.justnotes.data.model.Note
import com.joesemper.justnotes.presentation.MainViewModel
import com.joesemper.justnotes.presentation.NoteViewModel
import com.joesemper.justnotes.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

object DependencyGraph {
    private val repositoryModule by lazy {
        module {
            single { NotesRepositoryImpl(get()) } bind NotesRepository::class
            single { FireStoreDatabaseProvider() } bind DatabaseProvider::class
        }
    }

    private val viewModelModule by lazy {
        module {
            viewModel { MainViewModel(get()) }
            viewModel { SplashViewModel(get()) }
            viewModel { (note: Note?) -> NoteViewModel(get(), note) }
        }
    }


    val modules: List<Module> = listOf(repositoryModule, viewModelModule)
}