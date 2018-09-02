package org.uwunote.uwunote

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.File

class NotesAdapter(private val context: Context) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    val noteList: List<Note>

    init {
        noteList = getAllNotes()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val content: TextView

        init {
            content = view.findViewById<View>(R.id.content) as TextView

            view.setOnClickListener { view ->
                val intent = Intent(context, ViewNoteActivity::class.java)
                intent.putExtra("uid", noteList[adapterPosition].uid.toString())
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.content.text = note.content!!.split("\n")[0]
    }

    override fun getItemCount(): Int {
        return getAllNotes().size
    }

    private fun getAllNotes(): ArrayList<Note> {
        val notes: ArrayList<Note> = ArrayList()
        var note: Note?
        for(file: File in File(context.filesDir.absolutePath + File.separator + noteFolder).listFiles()) {
            note = Note(context, file.name)
            note.load()
            notes.add(note)
        }
        return notes
    }
}