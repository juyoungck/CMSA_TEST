package com.example.closetmanagementservicesapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Post extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private List<Integer> c_loc_value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        // MyApplication 클래스에서 DBHelper를 가져오는 코드
        dbHelper = MyApplication.getDbHelper();

        // db 파일을 읽기/쓰기가 가능한 형식으로 연다
        db = dbHelper.getWritableDatabase();

        ImageView c_img_post = (ImageView) findViewById(R.id.c_img_post);   // 이미지 호출

        fillSpinner_location();                                             // 옷장 위치 값 호출

        EditText c_name_post = (EditText) findViewById(R.id.c_name_post);   // 옷 이름 호출

        EditText c_brand_post = (EditText) findViewById(R.id.c_brand_post); // 옷 브랜드 호출

        Spinner c_type_spinner = (Spinner) findViewById(R.id.c_type_post);  // 옷 종류 호출
        ArrayAdapter<CharSequence> c_type_adapter = ArrayAdapter.createFromResource(this, R.array.c_type_array, android.R.layout.simple_spinner_item);
        c_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_type_spinner.setAdapter(c_type_adapter);
        EditText c_type_post_add = (EditText) findViewById(R.id.c_type_post_add);   // 옷 종류(직접입력) 호출

        Spinner c_size_spinner = (Spinner) findViewById(R.id.c_size_post);  // 옷 사이즈 호출
        ArrayAdapter<CharSequence> c_size_adapter = ArrayAdapter.createFromResource(this, R.array.c_size_array, android.R.layout.simple_spinner_item);
        c_size_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_size_spinner.setAdapter(c_size_adapter);
        EditText c_size_post_add = (EditText) findViewById(R.id.c_size_post_add);   // 옷 사이즈((직접입력) 호출

        EditText c_memo_post = (EditText) findViewById(R.id.c_memo_post);   // 메모 호출

        Button save = (Button) findViewById(R.id.cloth_post);               // 등록 버튼


        c_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String c_type = c_type_spinner.getSelectedItem().toString();

                if (c_type.equals("직접입력")) {
                    c_type_post_add.setVisibility(View.VISIBLE);
                } else {
                    c_type_post_add.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        c_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String c_size = c_size_spinner.getSelectedItem().toString();

                if (c_size.equals("직접입력")) {
                    c_size_post_add.setVisibility(View.VISIBLE);
                } else if (c_size.equals("선택안함")) {
                    c_size_post_add.setVisibility(View.INVISIBLE);
                    c_size_post_add.setText("");
                } else {
                    c_size_post_add.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // 등록 버튼을 누르면 실행되는 함수
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = c_img_post.getDrawable();
                int selectedLocIndex = ((Spinner) findViewById(R.id.c_loc_post)).getSelectedItemPosition();
                Integer c_loc = c_loc_value.get(selectedLocIndex);
                String c_name = c_name_post.getText().toString();
                String c_type = c_type_spinner.getSelectedItem().toString();
                String c_type_add = c_type_post_add.getText().toString();
                String c_size = c_size_spinner.getSelectedItem().toString();
                String c_size_add = c_size_post_add.getText().toString();
                String c_brand = c_brand_post.getText().toString();
                String c_memo = c_memo_post.getText().toString();

                // if (drawable == null) {
                //                    Toast.makeText(getApplicationContext(), "사진 파일이 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                //                } else
                if (c_name.equals("")) {
                    Toast.makeText(getApplicationContext(), "옷 이름이 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else if (c_type.equals("직접입력") && c_type_add.equals("")) {
                    Toast.makeText(getApplicationContext(), "옷 종류가 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Post.this);
                    builder.setTitle("옷 등록")        // 제목 설정
                            .setMessage("해당 설정으로 옷을 등록하시겠습니까?")        // 메세지 설정
                            .setCancelable(false)                               // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    db.beginTransaction();
                                    try {
                                        ContentValues values = new ContentValues();
                                        values.put("c_loc", c_loc);
                                        values.put("c_name", c_name);

                                        if (c_type.equals("직접입력")) {
                                            values.put("c_type", c_type_add);
                                        } else {
                                            values.put("c_type", c_type);
                                        }

                                        if (c_size.equals("직접입력")) {
                                            values.put("c_size", c_size_add);
                                        } else if (c_size.equals("선택안함")) {
                                            values.put("c_size", "");
                                        } else {
                                            values.put("c_size", c_size);
                                        }

                                        values.put("c_brand", c_brand);
                                        values.put("c_tag", 1);
                                        values.put("c_memo", c_memo);
                                        values.put("c_date", getToday());
                                        values.put("c_stack", 0);

                                        long rowId = db.insert("Main_Closet", null, values);
                                        Cursor cursor = db.rawQuery("SELECT c_id FROM Main_Closet WHERE rowid = ?", new String[]{String.valueOf(rowId)});
                                        int cId = -1;

                                        if (cursor != null && cursor.moveToFirst()) {
                                            cId = cursor.getInt(cursor.getColumnIndex("c_id"));
                                            cursor.close();
                                        }

                                        String fileName = "image_" + cId + ".png";
                                        File directory = new File(getFilesDir(), "images");

                                        if (!directory.exists()) {
                                            directory.mkdirs();
                                        }

                                        File imageFile = new File(directory, fileName);

                                        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        values = new ContentValues();
                                        values.put("c_img", imageFile.getAbsolutePath());
                                        db.update("Main_Closet", values, "c_id = ?", new String[]{String.valueOf(cId)});

                                        db.setTransactionSuccessful();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        db.endTransaction();
                                    }

                                    Toast.makeText(getApplicationContext(), "옷 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Post.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //원하는 클릭 이벤트를 넣으시면 됩니다.
                                }
                            });

                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }
            }
        });
        weatherSelect(); // 태그(계절) 함수 호출
    }

    private void fillSpinner_location() {
        Spinner c_loc = findViewById(R.id.c_loc_post);

        List<String> locations = new ArrayList<>();
        c_loc_value = new ArrayList<>();    // c_loc 값을 저장할 리스트 초기화
        Cursor cursor = db.rawQuery("SELECT c_loc, c_loc_name FROM Closet_Location ORDER BY c_loc ASC", null);

        while (cursor.moveToNext()) {
            locations.add(cursor.getString(cursor.getColumnIndex("c_loc_name")));
            c_loc_value.add(cursor.getInt(cursor.getColumnIndex("c_loc")));
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_loc.setAdapter(adapter);
    }

    private String getToday() {
        DateFormat Today = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN);
        TimeZone KoreaTime = TimeZone.getTimeZone("Asia/Seoul");
        Today.setTimeZone(KoreaTime);

        Date date = new Date();

        return Today.format(date);
    }


    protected void weatherSelect() {
        // 정렬 버튼 (계절)
        CheckBox weatherSelect_spring = (CheckBox) findViewById(R.id.weatherSelect_spring);
        CheckBox weatherSelect_summer = (CheckBox) findViewById(R.id.weatherSelect_summer);
        CheckBox weatherSelect_fall = (CheckBox) findViewById(R.id.weatherSelect_fall);
        CheckBox weatherSelect_winter = (CheckBox) findViewById(R.id.weatherSelect_winter);
        CheckBox weatherSelect_communal = (CheckBox) findViewById(R.id.weatherSelect_communal);

        weatherSelect_spring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_summer.isChecked() && weatherSelect_fall.isChecked()
                            && weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if (weatherSelect_communal.isChecked()) {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(true);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(false);
                    }
                }
                weatherButtonBase();
            }
        });

        weatherSelect_summer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_spring.isChecked() && weatherSelect_fall.isChecked()
                            && weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if (weatherSelect_communal.isChecked()) {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(true);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(false);
                    }
                }
                weatherButtonBase();
            }
        });

        weatherSelect_fall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_spring.isChecked() && weatherSelect_summer.isChecked()
                            && weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if (weatherSelect_communal.isChecked()) {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(true);
                        weatherSelect_winter.setChecked(false);
                    }
                }
                weatherButtonBase();
            }
        });

        weatherSelect_winter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_spring.isChecked() && weatherSelect_summer.isChecked()
                            && weatherSelect_fall.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if (weatherSelect_communal.isChecked()) {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(true);
                    }
                }
                weatherButtonBase();
            }
        });

        // 정렬 버튼 (계절 - 전체 선택)
        weatherSelect_communal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 전체 선택 버튼이 선택된 경우
                    weatherSelect_spring.setChecked(true);
                    weatherSelect_summer.setChecked(true);
                    weatherSelect_fall.setChecked(true);
                    weatherSelect_winter.setChecked(true);

                } else {
                    if (!weatherSelect_spring.isChecked() || !weatherSelect_summer.isChecked()
                            || !weatherSelect_fall.isChecked() || !weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(false);
                    } else {
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(false);
                    }
                    weatherButtonBase();
                }
            }
        });
    }
    private void weatherButtonBase (){
        CheckBox weatherSelect_spring = (CheckBox) findViewById(R.id.weatherSelect_spring);
        CheckBox weatherSelect_summer = (CheckBox) findViewById(R.id.weatherSelect_summer);
        CheckBox weatherSelect_fall = (CheckBox) findViewById(R.id.weatherSelect_fall);
        CheckBox weatherSelect_winter = (CheckBox) findViewById(R.id.weatherSelect_winter);
        CheckBox weatherSelect_communal = (CheckBox) findViewById(R.id.weatherSelect_communal);
        if (weatherSelect_spring.isChecked()) {
            weatherSelect_spring.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_spring.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_summer.isChecked()) {
            weatherSelect_summer.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_summer.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_fall.isChecked()) {
            weatherSelect_fall.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_fall.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_winter.isChecked()) {
            weatherSelect_winter.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_winter.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_communal.isChecked()) {
            weatherSelect_communal.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_communal.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
    }
}
