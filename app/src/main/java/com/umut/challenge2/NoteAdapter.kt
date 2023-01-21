package com.umut.challenge2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    val itemClickListener: (Note, Int) -> Unit,
    val editButtonClickListener: (Note) -> Unit,
    val shareButtonClickListener: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var noteList: MutableList<Note> = mutableListOf()

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val header = itemView.findViewById<TextView>(R.id.note_header)
        private val text = itemView.findViewById<TextView>(R.id.note_text)
        private val buttonEdit = itemView.findViewById<ImageButton>(R.id.button_edit_note)
        private val buttonShare = itemView.findViewById<ImageButton>(R.id.button_share_note)

        init {
            buttonShare.setOnClickListener {
                shareButtonClickListener(noteList[adapterPosition])
            }

            buttonEdit.setOnClickListener {
                editButtonClickListener(noteList[adapterPosition])
            }

            itemView.setOnLongClickListener {
                itemClickListener(noteList[adapterPosition], adapterPosition)
                true
            }
        }

        fun bindItems(item: Note) {
            header.text = item.header
            text.text = item.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bindItems(noteList[position])

    }

    fun setNotes(noteList: MutableList<Note>){
        this.noteList = noteList
        notifyDataSetChanged()
    }

    override fun getItemCount() = noteList.size
}