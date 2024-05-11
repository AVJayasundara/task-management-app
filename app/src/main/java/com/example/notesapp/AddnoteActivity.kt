package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityAddnoteBinding
import com.example.notesapp.databinding.ActivityMainBinding

class AddnoteActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddnoteBinding
    private  lateinit var db:NotesDatabaseHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityAddnoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db =NotesDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title =binding.titleEditText.text.toString()
            val content=binding.contentEdittext.text.toString()
            val note= Note(0,title,content)
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        }

    }
}