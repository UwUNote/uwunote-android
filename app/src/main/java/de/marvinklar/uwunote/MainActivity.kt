package de.marvinklar.uwunote

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)

        val newNoteButton = findViewById<View>(R.id.newNoteButton) as FloatingActionButton
        newNoteButton.setOnClickListener { _ -> startActivity(Intent(baseContext, ViewNoteActivity::class.java)) }

        loadNotes()
    }

    private fun loadNotes() {
        val notesRecycler = findViewById<View>(R.id.notes) as RecyclerView
        notesRecycler.layoutManager = LinearLayoutManager(applicationContext)
        notesRecycler.itemAnimator = DefaultItemAnimator()
        notesRecycler.adapter = NotesAdapter(applicationContext)
    }

    override fun onResume() {
        super.onResume()

        loadNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}