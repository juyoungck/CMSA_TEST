package com.example.closetmanagementservicesapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class post extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        Spinner c_type_spinner = (Spinner) findViewById(R.id.c_type_post);
        ArrayAdapter<CharSequence> c_type_adapter = ArrayAdapter.createFromResource(this, R.array.c_type_array, android.R.layout.simple_spinner_item);
        c_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_type_spinner.setAdapter(c_type_adapter);

        Spinner c_size_spinner = (Spinner) findViewById(R.id.c_size_post);
        ArrayAdapter<CharSequence> c_size_adapter = ArrayAdapter.createFromResource(this, R.array.c_size_array, android.R.layout.simple_spinner_item);
        c_size_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_size_spinner.setAdapter(c_size_adapter);
    }
}
