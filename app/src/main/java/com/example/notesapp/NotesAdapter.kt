package com.example.notesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class NotesAdapter (private var notes: List<Note>,context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

        private val db: NotesDatabaseHelper= NotesDatabaseHelper(context)
        private var filteredNotes: List<Note> = notes

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val titleTextView: TextView = itemView.findViewById(R.id.titleTextview)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton:ImageView =itemView.findViewById(R.id.updateButton)
        val deleteButton:ImageView =itemView.findViewById(R.id.deletButton)
        val noteCheckBox: CheckBox = itemView.findViewById(R.id.noteCheckBox)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent, false)
        return NoteViewHolder(view)

    }

    override fun getItemCount(): Int = filteredNotes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = filteredNotes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content




        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Note deleted ", Toast.LENGTH_SHORT).show()
        }

        holder.noteCheckBox.setOnCheckedChangeListener(null) // Remove the listener before setting the state
        holder.noteCheckBox.isChecked = note.isChecked
        holder.noteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            note.isChecked = isChecked
            db.updateNote(note) // Save the state of checkbox in the database
        }


    }


    fun refreshData(newNotes: List<Note>){
        notes = newNotes
        filter("")
    }

    fun filter(query: String) {
        filteredNotes = if (query.isEmpty()) {
            notes
        } else {
            notes.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.content.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()

    }

}
