package com.joesemper.justnotes.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.joesemper.justnotes.R
import com.joesemper.justnotes.data.Repository
import com.joesemper.justnotes.data.model.Note
import com.joesemper.justnotes.presentation.MainViewModel
import com.joesemper.justnotes.presentation.MainViewState
import com.joesemper.justnotes.ui.adapter.MainAdapter
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewMode by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(
            MainViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MainAdapter {
            navigateToNote(it)
        }

        mainRecycler.adapter = adapter

        viewMode.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is MainViewState.Value -> {
                    adapter.submitList(it.notes)
                }
                MainViewState.EMPTY -> Unit
            }
        }

        floatingActionButton.setOnClickListener {
            navigateToCreation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    private fun navigateToNote(note: Note) {
        (requireActivity() as MainActivity).navigateTo(NoteFragment.create(note))
    }

    private fun navigateToCreation() {
        (requireActivity() as MainActivity).navigateTo(NoteFragment.create(null))
    }

}