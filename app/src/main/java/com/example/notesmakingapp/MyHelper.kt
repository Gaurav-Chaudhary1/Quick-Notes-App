package com.example.notesmakingapp

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "NOTES_DATA_TABLE"
        private const val DATABASE_TABLE = "NOTES_TABLE"
        private const val DATABASE_ID = "_id"
        private const val DATABASE_TITLE = "TITLE"
        private const val DATABASE_DESCRIPTION = "DESCRIPTION"
        private const val DATABASE_VERSION = 1
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val data = "CREATE TABLE $DATABASE_TABLE($DATABASE_ID INTEGER PRIMARY KEY AUTOINCREMENT,$DATABASE_TITLE TEXT,$DATABASE_DESCRIPTION TEXT)"
        db?.execSQL(data)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    //inserting data in DB
    fun insertData(title: String, description: String) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(DATABASE_TITLE,title)
            put(DATABASE_DESCRIPTION,description)
        }
        db.insert(DATABASE_TABLE,null,contentValues)
        db.close()
    }

    //getting data
    fun getAllData(): List<Note> {
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $DATABASE_TABLE"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DATABASE_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(DATABASE_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(DATABASE_DESCRIPTION))

            val note = Note(id,title,description)
            notesList.add(note)
        }
        cursor.close()
        db.close()
        return notesList
    }

    //updating data in DB
    fun updateData(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DATABASE_TITLE,note.title)
            put(DATABASE_DESCRIPTION,note.description)
        }
        db.update(DATABASE_TABLE,values,"$DATABASE_ID = ?", arrayOf(note.id.toString()))
        db.close()
    }

    //ID of data
    fun getNoteByID(noteID: Int): Note{
        val db = readableDatabase
        val query = "SELECT * FROM $DATABASE_TABLE WHERE $DATABASE_ID = $noteID"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(DATABASE_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(DATABASE_TITLE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(DATABASE_DESCRIPTION))

        cursor.close()
        db.close()
        return Note(id,title,description)
    }

    //deleting data from DB
    fun deleteData(noteID: Int){
        val db = writableDatabase
        db.delete(DATABASE_TABLE,"$DATABASE_ID = ?", arrayOf(noteID.toString()))
        db.close()
    }
}