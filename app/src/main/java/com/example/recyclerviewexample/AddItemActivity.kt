package com.example.recyclerviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar

class AddItemActivity : AppCompatActivity() {

    private lateinit var country_input: EditText
    private lateinit var town_input : EditText
    private lateinit var  rating_input: EditText
    private lateinit var button_add_position: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)


        country_input = findViewById(R.id.et_country)
        town_input = findViewById(R.id.et_town)
        rating_input = findViewById(R.id.et_rating)
        button_add_position = findViewById(R.id.button_confirm_item)

        button_add_position.setOnClickListener {
            val myDB = MyDatabaseHelper(this)
            myDB.addPlace(
                country_input.text.toString(),
                town_input.text.toString(),
                rating_input.text.toString().toDoubleOrNull()?: 0.0)
        }

    }
}