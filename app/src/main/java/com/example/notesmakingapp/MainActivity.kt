package com.example.notesmakingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmakingapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var db: MyHelper
    private lateinit var notesAdapter: NotesRCAdapter
    private lateinit var recylerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recylerView = findViewById(R.id.recylerView)

        //DataBase Class
        db = MyHelper(this)

        //Adapter Class
        notesAdapter = NotesRCAdapter(db.getAllData(),this)

        //Setting Layout in Recycler View
        recylerView.layoutManager = LinearLayoutManager(this)
        recylerView.adapter = notesAdapter

        //second activity opening on click of btn
        val addBtn = findViewById<FloatingActionButton>(R.id.addBtn)
        addBtn.setOnClickListener {
            val intent = Intent(applicationContext,SecondActivity::class.java)
            startActivity(intent)
        }
    }

    //resume the recycler view
    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllData())
    }
}