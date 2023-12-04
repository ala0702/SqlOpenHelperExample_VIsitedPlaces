package com.example.sqliteopenhelper

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

//    recyclerview things
    private lateinit var newRecyclerView : RecyclerView
    private lateinit var newViewHolder : ViewHolder

//    sqlite things
    lateinit var newMyDatabaseHelper : MyDatabaseHelper
    lateinit var idVisitedPlace: ArrayList<Int>
    lateinit var countryName: ArrayList<String>
    lateinit var townName: ArrayList<String>
    lateinit var ratingScore: ArrayList<Float>



    private lateinit var newArrayList: ArrayList<Place>

    lateinit var button_addItem: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val title = title // lub val title = actionBar?.title
//        Log.d("MainActivity", "Title: $title")


        button_addItem = findViewById(R.id.button_addItem)
        button_addItem.setOnClickListener {
            val intent = Intent(
                this,
                AddItemActivity::class.java
            )
            startActivity(intent)
        }

      newMyDatabaseHelper = MyDatabaseHelper(this)
        newArrayList = ArrayList()
        // Call storeDataInArrays to populate newArrayList with data
        storeDataInArrays()
        val myAdapter = MyAdapter(newArrayList)

        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.adapter = myAdapter
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
    }


    private fun storeDataInArrays() {
        var cursor: Cursor? = newMyDatabaseHelper.getAllData()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val place = Place(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getFloat(3)
                )
                newArrayList.add(place)
            }
            cursor.close() // Close the cursor after use
        } else {
            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show()
        }
    }

}