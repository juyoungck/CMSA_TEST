package com.example.closetmanagementservicesapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

// 이미지 버튼을 클릭했을 때 실행되는 java 코드
public class DetailActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int c_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // DB OPEN
        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();


        ImageView imageView = (ImageView) findViewById(R.id.detail_c_img);
        EditText detail_c_loc = (EditText) findViewById(R.id.detail_c_loc);
        EditText detail_c_name = (EditText) findViewById(R.id.detail_c_name);
        EditText detail_c_type = (EditText) findViewById(R.id.detail_c_type);
        EditText detail_c_size = (EditText) findViewById(R.id.detail_c_size);
        EditText detail_c_brand = (EditText) findViewById(R.id.detail_c_brand);
        EditText detail_c_tag = (EditText) findViewById(R.id.detail_c_tag);
        EditText detail_c_memo = (EditText) findViewById(R.id.detail_c_memo);
        EditText detail_c_date = (EditText) findViewById(R.id.detail_c_date);
        EditText detail_c_stack = (EditText) findViewById(R.id.detail_c_stack);
        // Button detail_save = (Button) findViewById(R.id.detail_save);
        // Button detail_delete = (Button) findViewById(R.id.detail_delete);

        Intent intent = getIntent();
        c_id = intent.getIntExtra("c_id", -1);
        String c_img = intent.getStringExtra("c_img");
        int c_loc = intent.getIntExtra("c_loc", 1);
        String c_name = intent.getStringExtra("c_name");
        String c_type = intent.getStringExtra("c_type");
        String c_size = intent.getStringExtra("c_size");
        String c_brand = intent.getStringExtra("c_brand");
        int c_tag = intent.getIntExtra("c_tag", 1);
        String c_memo = intent.getStringExtra("c_memo");
        String c_date = intent.getStringExtra("c_date");
        int c_stack = intent.getIntExtra("c_stack", 0);

        Bitmap bitmap = BitmapFactory.decodeFile(c_img);
        imageView.setImageBitmap(bitmap);
        detail_c_loc.setText(String.valueOf(c_loc));
        detail_c_name.setText(c_name);
        detail_c_type.setText(c_type);
        detail_c_size.setText(c_size);
        detail_c_brand.setText(c_brand);
        detail_c_tag.setText(String.valueOf(c_tag));
        detail_c_memo.setText(c_memo);
        detail_c_date.setText(c_date);
        detail_c_stack.setText(String.valueOf(c_stack));

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack_detail);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
