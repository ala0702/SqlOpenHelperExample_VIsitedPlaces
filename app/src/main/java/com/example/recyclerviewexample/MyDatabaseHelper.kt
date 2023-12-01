package com.example.recyclerviewexample

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION: Int = 1
        private const val DATABASE_NAME: String = "Visited.db"
    }

    private val TABLE_NAME = "countries"
    private val COLUMN_ID = "id"
    private val COLUMN_NAME = "country"
    private val COLUMN_TOWN = "town"
    private val COLUMN_RATING = "rating"


    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT NOT NULL, " +
                "$COLUMN_TOWN TEXT, " +
                "$COLUMN_RATING FLOAT);"

        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }

    fun addPlace(country: String, town: String, rating : Double){

        if (country.isBlank() || town.isBlank()) {
            // Display a message or handle the error when country or town is empty
            Toast.makeText(context, "Country and town cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if ( rating > 100) {
            // Display a message or handle the error when country or town is empty
            Toast.makeText(context, "Rating cannot be higher then 100.", Toast.LENGTH_SHORT).show()
            return
        }

        val db : SQLiteDatabase = this.writableDatabase // this refers to our SqLiteHelper class
        val data = ContentValues().apply {
            put(COLUMN_NAME, country)
            put(COLUMN_TOWN, town)
            put(COLUMN_RATING, rating)
        }

        val newRowId = db.insert(TABLE_NAME, null, data)
        // insert method returns a Long, it's a good practice to use -1L

        if (newRowId != -1L) {
            // Insertion successful
            Toast.makeText(context, "Insertion successful",Toast.LENGTH_SHORT).show()

        } else {
            // Insertion failed
            Toast.makeText(context, "Insertion failed",Toast.LENGTH_SHORT).show()
        }

    }


    fun getAllData() : Cursor?{
        val query : String = "SELECT * FROM $TABLE_NAME"
        val db : SQLiteDatabase? = this.readableDatabase

        var cursor : Cursor? = null
        if(db != null)
        {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun updateData(rowId: Int, countryName: String, townName : String, rating: Float) {

        if (countryName.isBlank() || townName.isBlank()) {
            // Display a message or handle the error when country or town is empty
            Toast.makeText(context, "Country and town cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if ( rating > 100) {
            // Display a message or handle the error when country or town is empty
            Toast.makeText(context, "Rating cannot be higher then 100.", Toast.LENGTH_SHORT).show()
            return
        }

        val db : SQLiteDatabase = this.writableDatabase // this refers to our SqLiteHelper class
        val data = ContentValues().apply {
            put(COLUMN_NAME, countryName)
            put(COLUMN_TOWN, townName)
            put(COLUMN_RATING, rating)
        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(rowId.toString())


        val rowUpdated = db.update(TABLE_NAME, data, whereClause, whereArgs)


        if (rowUpdated != -1) {
            // Insertion successful
            Toast.makeText(context, "Update successful",Toast.LENGTH_SHORT).show()

        } else {
            // Insertion failed
            Toast.makeText(context, "Update failed",Toast.LENGTH_SHORT).show()
        }

    }
}
