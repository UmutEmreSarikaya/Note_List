package com.umut.challenge2

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umut.challenge2.databinding.FragmentListNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@AndroidEntryPoint
class ListNoteFragment : Fragment(R.layout.fragment_list_note) {
    @Inject
    lateinit var noteDao: NoteDao
    private lateinit var binding: FragmentListNoteBinding
    private lateinit var noteAdapter: NoteAdapter
    private var notes: MutableList<Note> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListNoteBinding.inflate(layoutInflater)

        noteAdapter = NoteAdapter(
            ::itemClickListener,
            ::editButtonClickListener,
            ::shareButtonClickListener
        )
        binding.recyclerView.adapter = noteAdapter

        GlobalScope.launch(Dispatchers.IO) {
            notes = noteDao.getAll()
            GlobalScope.launch(Dispatchers.Main) {
                noteAdapter.setNotes(notes)

                if (notes.size == 0) {
                    binding.textInfo.visibility = View.VISIBLE
                } else {
                    binding.textInfo.visibility = View.GONE
                }
            }
        }

        binding.buttonAddNote.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, AddNoteFragment())
                ?.addToBackStack("fragment_stack")
                ?.commit()
        }

        return binding.root
    }

    private fun itemClickListener(note: Note, position: Int) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Do you want to remove the note ?")
        builder.setMessage("Note will be removed!")

        builder.setPositiveButton("Yes") { _, _ ->
            GlobalScope.launch(Dispatchers.IO) {
                noteDao.delete(note)
            }
            notes.removeAt(position)
            noteAdapter.notifyItemRemoved(position)

            if (notes.size == 0) {
                binding.textInfo.visibility = View.VISIBLE
            } else {
                binding.textInfo.visibility = View.GONE
            }
        }

        builder.setNegativeButton("Cancel") { _, _ ->
        }
        builder.show()
    }

    private fun editButtonClickListener(note: Note) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, EditNoteFragment.newInstance(note))
            ?.addToBackStack("fragment_stack")
            ?.commit()
    }

    private fun shareButtonClickListener(note: Note) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, note.text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }

}