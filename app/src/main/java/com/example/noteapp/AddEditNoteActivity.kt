package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.databinding.ActivityAddEditNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditNoteBinding
    private lateinit var viewModal: NoteViewModal
    private var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModal::class.java]

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID", -1)

            binding.itBtnAddUpdate.text = "Update note"
            binding.idEditNoteTitle.setText(noteTitle)
            binding.idEditNoteDescription.setText(noteDescription)
        } else {
            binding.itBtnAddUpdate.text = "Save note"
        }

        binding.itBtnAddUpdate.setOnClickListener {
            val noteTitle = binding.idEditNoteTitle.text.toString()
            val noteDescription = binding.idEditNoteDescription.text.toString()

            if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                val currentDate: String = sdf.format(Date())
                val updateNote = Note(noteTitle, noteDescription, currentDate)
                updateNote.id = noteID
                viewModal.updateNote(updateNote)
                Toast.makeText(this, "Note update...", Toast.LENGTH_LONG).show()
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    viewModal.addNote(Note(noteTitle, noteDescription, currentDate))
                    Toast.makeText(this, "Note added...", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}