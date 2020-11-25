package com.joesemper.justnotes.ui

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.joesemper.justnotes.R
import com.joesemper.justnotes.data.model.*
import com.joesemper.justnotes.presentation.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment(R.layout.fragment_note) {
    private val note: Note? by lazy(LazyThreadSafetyMode.NONE) { arguments?.getParcelable(NOTE_KEY) }

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NoteViewModel(note) as T
            }
        }).get(
            NoteViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNote()
        initToolbar()
        initColorChoose()
    }

    private fun initNote() {
        viewModel.note?.let {
            titleEt.setText(it.title)
            bodyEt.setText(it.note)
            noteCard.setCardBackgroundColor(it.color.mapToColor(context as AppCompatActivity))
        }

        titleEt.addTextChangedListener {
            viewModel.updateTitle(it?.toString() ?: "")
        }

        bodyEt.addTextChangedListener {
            viewModel.updateNote(it?.toString() ?: "")
        }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { (activity as AppCompatActivity).onBackPressed() }
    }

    private fun initColorChoose() {
        colorChooseButton.setOnClickListener {
            val currentColor = viewModel.note?.color?.colorId ?: -1 // тут note всегда null, не смог разобраться почему
            val arrayOfColors = getArrayOfColors()

            val builder = AlertDialog.Builder(context as AppCompatActivity)
            builder.setTitle(getString(R.string.color_select_title))
                .setSingleChoiceItems(arrayOfColors, currentColor) { dialogInterface, i ->
                    val color = getColorByNumber(i)
                    updateColor(color)
                    dialogInterface.dismiss()
                }
                .setNeutralButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
                .setPositiveButton(getString(R.string.random)) { dialog, _ ->
                    val color = getRandomColor()
                    updateColor(color)
                    dialog.cancel()
                }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun updateColor(color: Color) {
        viewModel.updateColor(color)
        noteCard.setCardBackgroundColor(color.mapToColor(context as AppCompatActivity))
    }

    companion object {
        const val NOTE_KEY = "Note"

        fun create(note: Note? = null): NoteFragment {
            val fragment = NoteFragment()
            val arguments = Bundle()
            arguments.putParcelable(NOTE_KEY, note)
            fragment.arguments = arguments

            return fragment
        }
    }
}