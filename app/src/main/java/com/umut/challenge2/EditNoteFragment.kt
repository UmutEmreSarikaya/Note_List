package com.umut.challenge2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umut.challenge2.databinding.FragmentEditNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"

@DelicateCoroutinesApi
@AndroidEntryPoint
class EditNoteFragment : Fragment() {

    @Inject
    lateinit var noteDao: NoteDao

    private var note: Note? = null
    private lateinit var binding: FragmentEditNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditNoteBinding.inflate(layoutInflater)

        note = arguments?.getParcelable(ARG_PARAM1)

        binding.editTextUpdateHeader.setText(note?.header)
        binding.editTextUpdateText.setText(note?.text)

        binding.buttonUpdateNote.setOnClickListener {

            note?.header = binding.editTextUpdateHeader.text.toString()
            note?.text = binding.editTextUpdateText.text.toString()

            GlobalScope.launch(Dispatchers.IO) {
                noteDao.update(note)
            }

            activity?.supportFragmentManager?.popBackStack()
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, ListNoteFragment())
                ?.commit()
        }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(note: Note) =
            EditNoteFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, note)
                }
            }
    }

}