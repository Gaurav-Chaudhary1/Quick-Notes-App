package com.example.notesmakingapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class NotesRCAdapter(private var notes: List<Note>, context: Context): RecyclerView.Adapter<NotesRCAdapter.MyAdapter>(){

    private val db: MyHelper = MyHelper(context)
    private var isPinned = false

    class MyAdapter(viewItem: View): RecyclerView.ViewHolder(viewItem) {
        val titleText = viewItem.findViewById<TextView>(R.id.titleTextView)
        val descriptionText = viewItem.findViewById<TextView>(R.id.DescTextView)
        val deleteBtn = viewItem.findViewById<ImageView>(R.id.deleteBtn)
        val pinBtn = viewItem.findViewById<ImageView>(R.id.pinBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view,parent,false)
        return MyAdapter(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        val note = notes[position]
        holder.titleText.text = note.title
        holder.descriptionText.text = note.description

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,UpdateNoteActivity::class.java).apply {
                putExtra("note_id",note.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteBtn.setOnClickListener{
            db.deleteData(note.id)
            refreshData(db.getAllData())
            Toast.makeText(holder.itemView.context,"Deleted",Toast.LENGTH_SHORT).show()
        }
        holder.pinBtn.setOnClickListener{
            isPinned = !isPinned
            if (isPinned) {
                holder.pinBtn.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.black_pinned))
            } else {
                holder.pinBtn.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.pinned2))
            }
        }

    }
    fun refreshData(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}
