package com.joesemper.justnotes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.justnotes.R
import com.joesemper.justnotes.data.model.Note
import com.joesemper.justnotes.databinding.ItemNoteBinding
import kotlinx.android.synthetic.main.item_note.view.*

val DIFF_UTIL: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return true
    }
}

class MainAdapter(val noteHandler: (Note) -> Unit) :
    ListAdapter<Note, MainAdapter.NoteViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(
        parent: ViewGroup,
        private val binding: ItemNoteBinding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        private lateinit var currentNote: Note

        private val clickListener: View.OnClickListener = View.OnClickListener {
            noteHandler(currentNote)
        }

        fun bind(item: Note) {
            currentNote = item
            with(itemView) {
                title.text = item.title
                body.text = item.note
                setBackgroundColor(item.color.mapToColor(context))
                setOnClickListener(clickListener)
            }
        }
    }
}