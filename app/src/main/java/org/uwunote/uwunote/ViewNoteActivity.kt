package org.uwunote.uwunote

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_view_note.*

class ViewNoteActivity : AppCompatActivity() {

    private lateinit var noteEditor: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_note)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete ->  {
                AlertDialog.Builder(this)
                        .setTitle(R.string.delete_note_security_question_title)
                        .setMessage(R.string.you_sure_question)
                        .setPositiveButton(R.string.yes) { _, _ ->
                            deleteNote()
                        }
                        .setNegativeButton(R.string.no) {_, _ ->
                            //Do nothing
                        }
                        .show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteNote() {
        val uid = intent.getStringExtra("uid")

        if (uid != null) {
            val note = Note(applicationContext, uid)
            note.delete()
            val intent = Intent(applicationContext, MainActivity::class.java)
            applicationContext.startActivity(intent)
        }
    }
}
