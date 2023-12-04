package com.example.sqliteopenhelper

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

//    recyclerview things
    private lateinit var newRecyclerView : RecyclerView
    private lateinit var newViewHolder : ViewHolder

//    empty screen
    private lateinit var tv_noData : TextView
    private lateinit var iv_emptyScreen : ImageView


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

        tv_noData = findViewById(R.id.tv_noData)
        iv_emptyScreen = findViewById(R.id.iv_emptyScreen)

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
        if (cursor != null  && cursor.count > 0) {
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
        }
        else {
            tv_noData.visibility = View.VISIBLE
            iv_emptyScreen.visibility = View.VISIBLE

            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_all){
            confirmDialogDeleteAllData()
        }
        return super.onOptionsItemSelected(item)
    }

    fun confirmDialogDeleteAllData() {
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Deletion of all items" )
        builder.setMessage("Are you sure to delete all data?")
            .setPositiveButton("Yes"){ dialog, which ->
                val db = MyDatabaseHelper(this)
                db.deleteAll()
                recreate()
            }
            .setNegativeButton("No"){ dialog, which ->

            }
            .show()
    }
}