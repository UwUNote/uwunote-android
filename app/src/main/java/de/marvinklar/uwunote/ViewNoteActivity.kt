package de.marvinklar.uwunote

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_view_note.*

class ViewNoteActivity : AppCompatActivity() {

    private lateinit var noteEditor: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_note)
        setSupportActionBar(toolbar)

        noteEditor = findViewById(R.id.NoteText)

        val uid = intent.getStringExtra("uid")

        if (uid != null) {
            val note = Note(applicationContext, uid)
            note.load()
            noteEditor.setText(note.content, TextView.BufferType.EDITABLE)
        }

        saveButton.setOnClickListener { view ->
            val content = noteEditor.text.toString()

            val note = if (uid != null) {
                Note(applicationContext, uid)
            } else {
                Note(applicationContext)
            }

            if (content.isEmpty()) {
                note.delete()
                Snackbar.make(view, getText(R.string.deleted), Snackbar.LENGTH_LONG).show()
            } else {
                note.content = content
                note.save()
                Snackbar.make(view, getText(R.string.saved), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
