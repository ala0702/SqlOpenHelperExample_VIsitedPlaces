package com.example.sqliteopenhelper

import android.app.ActionBar
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.properties.Delegates

class UpdateItemActivity : AppCompatActivity() {

    private lateinit var country_input: EditText
    private lateinit var town_input : EditText
    private lateinit var  rating_input: EditText
    private lateinit var button_update_position: Button
    private lateinit var button_delete_position: Button

    private var itemId by Delegates.notNull<Int>()
    private lateinit var countryName: String
    private lateinit var townName: String
    private var ratingScore by Delegates.notNull<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_item)

        country_input = findViewById(R.id.et_countryEdit)
        town_input = findViewById(R.id.et_townEdit)
        rating_input = findViewById(R.id.et_ratingEdit)
        button_update_position = findViewById(R.id.button_update_item)
        button_delete_position = findViewById(R.id.button_delete_item)

        //First call this before button update!!
        getAndSetIntentData()

        //changing navbar
        val ab : androidx.appcompat.app.ActionBar? = getSupportActionBar()
        ab?.setTitle(countryName)

        button_update_position.setOnClickListener{
            val db = MyDatabaseHelper(this)
            Log.d("MyDebug", "$itemId, $countryName, $townName, $ratingScore")
            db.updateData(itemId, country_input.text.toString(), town_input.text.toString(), rating_input.text.toString().toFloat())
        }

        button_delete_position.setOnClickListener{
            val db = MyDatabaseHelper(this)
            db.deleteOneRow(itemId)
        }
    }


    fun getAndSetIntentData() {
        if (intent.hasExtra("itemId") && intent.hasExtra("countryName") &&
            intent.hasExtra("townName") && intent.hasExtra("ratingScore")
        ) {
            Log.d("MyDebug", "Starting getAndSetIntentData()")
            // getting data from intent
            itemId = intent.getIntExtra("itemId", 0)
            countryName = intent.getStringExtra("countryName") ?: ""
            townName = intent.getStringExtra("townName") ?: ""
            ratingScore = intent.getFloatExtra("ratingScore", 0.0f)

            //setting new data
            Log.d("MyDebug", "$itemId, $countryName, $townName, $ratingScore")

            country_input.setText(countryName)
            town_input.setText(townName)
            rating_input.setText(ratingScore.toString())
        } else {
            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show()
        }
    }

    fun confirmDialog() {
        val builder : AlertDialog.Builder =  AlertDialog.Builder(this)
        builder.setTitle("Deletion of $countryName - $townName" )
        builder.setMessage("Are you sure to delete this position?")
        builder.setPositiveButton("Yes"){ dialog, which ->
            val db = MyDatabaseHelper(this)
            db.deleteOneRow(itemId)
        }

    }


}
