package com.joesemper.justnotes.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.joesemper.justnotes.R
import com.joesemper.justnotes.data.model.*
import com.joesemper.justnotes.data.noteId
import com.joesemper.justnotes.databinding.FragmentNoteBinding
import com.joesemper.justnotes.presentation.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NoteFragment : Fragment() {
    private val note: Note? by lazy(LazyThreadSafetyMode.NONE) { arguments?.getParcelable(NOTE_KEY) }

    private val viewModel by viewModel<NoteViewModel> {
        parametersOf(note)
    }

    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNote()
        initToolbar()
        initColorChoose()
    }

    private fun initNote() {

        with(binding) {
            viewModel.note?.let {
                titleEt.isVisible = true
                titleEt.setText(it.title)
                bodyEt.setText(it.note)
                noteCard.setCardBackgroundColor(it.color.mapToColor(requireContext()))
            }

            viewModel.showError().observe(viewLifecycleOwner) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_while_saving),
                    Toast.LENGTH_LONG
                ).show()
            }

            buttonApply.setOnClickListener {
                viewModel.saveNote()
                (activity as AppCompatActivity).onBackPressed()
            }

            buttonDellNote.setOnClickListener {
                deleteNote()
            }

            titleEt.addTextChangedListener {
                viewModel.updateTitle(it?.toString() ?: "")
            }

            bodyEt.addTextChangedListener {
                viewModel.updateNote(it?.toString() ?: "")
            }
        }
    }

    private fun initToolbar() {
        with(requireActivity() as AppCompatActivity) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }

        with(toolbar) {
            setNavigationOnClickListener {
                (activity as AppCompatActivity).onBackPressed()
            }
            title = viewModel.note?.title ?: getString(R.string.note_creation_title)
        }
    }

    private fun initColorChoose() {
        colorChooseButton.setOnClickListener {
            val currentColor = viewModel.note?.color?.colorId ?: -1
            val arrayOfColors = getArrayOfColors()

            val builder = AlertDialog.Builder(requireContext())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.delete_dialog_message)
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
            .setPositiveButton(getString(R.string.ok_button)) {dialog, _ ->
                viewModel.deleteNote(note?.id.toString())
                (activity as AppCompatActivity).onBackPressed()
            }
        val dialog = builder.create()
        dialog.show()
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