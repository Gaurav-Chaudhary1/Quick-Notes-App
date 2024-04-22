package com.example.notesmakingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var edt_Title: EditText
    private lateinit var edt_Desc: EditText
    private lateinit var db: MyHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        edt_Title = findViewById(R.id.ed_TitleUpdate)
        edt_Desc = findViewById(R.id.ed_NotesUpdate)
        toolbar = findViewById(R.id.toolbar2)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        db = MyHelper(this)

        noteId = intent.getIntExtra("note_id",-1)
        if (noteId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        edt_Title.setText(note.title)
        edt_Desc.setText(note.description)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home){
            onBackPressed()
            return true
        } else if (id == R.id.action_save){
            val updateTitle = edt_Title.text.toString()
            val updateDesc = edt_Desc.text.toString()
            val updateNote = Note(noteId,updateTitle,updateDesc)
            db.updateData(updateNote)
            finish()
            Toast.makeText(this,"Changes Saved",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}