package com.caio.gravadormix

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class RecordingsActivity : AppCompatActivity() {

    private lateinit var recordingsList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordings)

        recordingsList = findViewById(R.id.recordingsList)

        val dir: File? = getExternalFilesDir(null)
        val files = dir?.listFiles()

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            files?.map { it.name }?.toMutableList() ?: mutableListOf()
        )

        recordingsList.adapter = adapter
    }
}
