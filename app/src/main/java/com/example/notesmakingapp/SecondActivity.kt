package com.example.notesmakingapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.notesmakingapp.databinding.ActivityMainBinding

class SecondActivity : AppCompatActivity() {
    private var isPinned = false
    val db =  MyHelper(this)
    private lateinit var toolbar: Toolbar
    private lateinit var edTitle: EditText
    private lateinit var edtNotes: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        edTitle = findViewById(R.id.ed_Title)
        edtNotes = findViewById(R.id.ed_Notes)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home){
            onBackPressed()
            return true
        } else if (id == R.id.action_save2){
            val title = edTitle.text.toString()
            val description = edtNotes.text.toString()
            db.insertData(title,description)
            finish()
            Toast.makeText(this, "Note Saved",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}