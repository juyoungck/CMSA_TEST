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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Spinner;
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
    private boolean isModified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // DB OPEN
        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();


        ImageView imageView = (ImageView) findViewById(R.id.detail_c_img);
        Spinner detail_c_loc = (Spinner) findViewById(R.id.detail_c_loc);
        EditText detail_c_name = (EditText) findViewById(R.id.detail_c_name);
        Spinner detail_c_type = (Spinner) findViewById(R.id.detail_c_type);
        Spinner detail_c_size = (Spinner) findViewById(R.id.detail_c_size);
        EditText detail_c_brand = (EditText) findViewById(R.id.detail_c_brand);
        // EditText detail_c_tag = (EditText) findViewById(R.id.detail_c_tag);
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
        //detail_c_loc.setText(String.valueOf(c_loc));
        detail_c_name.setText(c_name);
        //detail_c_type.setText(c_type);
        //detail_c_size.setText(c_size);
        detail_c_brand.setText(c_brand);
        //detail_c_tag.setText(String.valueOf(c_tag));
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

        Button detailModifyButton = findViewById(R.id.detail_Modify);
        Button deleteButton = findViewById(R.id.detail_delete);
        // 수정 버튼 클릭 전 비활성화
        Spinner detailctype = findViewById(R.id.detail_c_type);
        Spinner detailcloc = findViewById(R.id.detail_c_loc);
        Spinner detailcsize = findViewById(R.id.detail_c_size);
        EditText detailcname= findViewById(R.id.detail_c_name);
        EditText detailcbrand= findViewById(R.id.detail_c_brand);
        CheckBox weatherSelectspring= findViewById(R.id.weatherSelect_spring);
        CheckBox weatherSelectsummer= findViewById(R.id.weatherSelect_summer);
        CheckBox weatherSelectfall= findViewById(R.id.weatherSelect_fall);
        CheckBox weatherSelectwinter= findViewById(R.id.weatherSelect_winter);
        CheckBox weatherSelectcommunal= findViewById(R.id.weatherSelect_communal);
        EditText detailcmemo= findViewById(R.id.detail_c_memo);
        EditText detailcdate= findViewById(R.id.detail_c_date);
        EditText detailcstack= findViewById(R.id.detail_c_stack);
        detailctype.setEnabled(false);
        detailctype.setClickable(false);
        detailcloc.setEnabled(false);
        detailcloc.setClickable(false);
        detailcsize.setEnabled(false);
        detailcsize.setClickable(false);

        detailModifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isModified) {
                    detailModifyButton.setText("저장");
                    isModified = true;
                    // 수정 버튼 클릭 후 활성화
                    detailctype.setEnabled(true);
                    detailcloc.setEnabled(true);
                    detailcsize.setEnabled(true);
                    detailcsize.setClickable(true);
                    detailcname.setEnabled(true);
                    detailcbrand.setEnabled(true);
                    detailcmemo.setEnabled(true);
                    detailcdate.setEnabled(true);
                    detailcstack.setEnabled(true);
                    weatherSelectspring.setEnabled(true);
                    weatherSelectsummer.setEnabled(true);
                    weatherSelectfall.setEnabled(true);
                    weatherSelectwinter.setEnabled(true);
                    weatherSelectcommunal.setEnabled(true);
                    detailctype.setClickable(true);
                    detailcloc.setClickable(true);
                    detailcsize.setClickable(true);
                } else {
                    // 두 번째 클릭: 다이얼로그 표시
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                    builder.setMessage("옷을 저장하겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setMessage("옷을 삭제하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }

                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
        Spinner detail_c_type_spinner = (Spinner) findViewById(R.id.detail_c_type);  // 옷 종류 호출
        ArrayAdapter<CharSequence> detail_c_type_adapter = ArrayAdapter.createFromResource(this, R.array.c_type_array, android.R.layout.simple_spinner_item);
        detail_c_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detail_c_type_spinner.setAdapter(detail_c_type_adapter);
        EditText detail_c_type_add = (EditText) findViewById(R.id.detail_c_type_add);   // 옷 종류(직접입력) 호출


        detail_c_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String c_type = detail_c_type_spinner.getSelectedItem().toString();

                if (c_type.equals("직접입력")) {
                    detail_c_type_add.setVisibility(View.VISIBLE);
                } else {
                    detail_c_type_add.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Spinner detail_c_size_spinner = (Spinner) findViewById(R.id.detail_c_size);  // 옷 사이즈 호출
        ArrayAdapter<CharSequence> detail_c_size_adapter = ArrayAdapter.createFromResource(this, R.array.c_size_array, android.R.layout.simple_spinner_item);
        detail_c_size_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detail_c_size_spinner.setAdapter(detail_c_size_adapter);
        EditText detail_c_size_add = (EditText) findViewById(R.id.detail_c_size_add);   // 옷 사이즈((직접입력) 호출

        detail_c_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String c_size = detail_c_size_spinner.getSelectedItem().toString();

                if (c_size.equals("직접입력")) {
                    detail_c_size_add.setVisibility(View.VISIBLE);
                } else if (c_size.equals("선택안함")) {
                    detail_c_size_add.setVisibility(View.INVISIBLE);
                    detail_c_size_add.setText("");
                } else {
                    detail_c_size_add.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}