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

    private val adapter = MainAdapter()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        mainRecycler.adapter = adapter

        viewMode.viewState().observe(viewLifecycleOwner, Observer<MainViewState> { t ->
            t?.let { adapter.notes = it.notes }
        })

        floatingActionButton.setOnClickListener(View.OnClickListener { addNewNote() })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add -> addNewNote()
            R.id.action_delete -> deleteNote()
        }
        return true
    }

    private fun addNewNote(){
        Repository.notes.add(Note("New note", "New note"))
        adapter.notes = Repository.notes
    }

    private fun deleteNote(){
        Repository.notes.removeLast()
        adapter.notes = Repository.notes
    }
}