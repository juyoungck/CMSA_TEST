package com.example.closetmanagementservicesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    DBHelper dbHelper;
    ImageView listview_img;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        dbHelper = new DBHelper(this);
        ArrayList<HashMap<String, String>> indexList = dbHelper.getTableIndexNames();

        listview_img = findViewById(R.id.listview_img);
        listview = findViewById(R.id.listview);

        CustomAdapter adapter = new CustomAdapter(this, indexList);
        listview.setAdapter(adapter);
    }
}