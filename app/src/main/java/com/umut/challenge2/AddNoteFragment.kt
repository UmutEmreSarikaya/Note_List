package com.umut.challenge2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umut.challenge2.databinding.FragmentAddNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    @Inject
    lateinit var noteDao: NoteDao
    private lateinit var binding: FragmentAddNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(layoutInflater)

        binding.buttonSaveNewNote.setOnClickListener {
            val newNote = Note(
                0,
                binding.editTextHeader.text.toString(),
                binding.editTextText.text.toString()
            )
            GlobalScope.launch(Dispatchers.IO) {
                noteDao.insert(newNote)
            }

            activity?.supportFragmentManager?.popBackStack()
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, ListNoteFragment())
                ?.commit()
        }

        return binding.root
    }

}