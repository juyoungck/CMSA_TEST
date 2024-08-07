package com.example.closetmanagementservicesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;

import java.io.ByteArrayOutputStream;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 하단 등록 버튼 이동
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CodyAdd.class);
                startActivity(intent);
            }
        });

        // 정렬 버튼
        ImageButton btnSort = (ImageButton) findViewById(R.id.btnSort);
        btnSort.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_sort, null);

                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();

                Button tabsortClose = view1.findViewById(R.id.tabsortClose);
                tabsortClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });

                // 정렬 버튼(옷 종류)
                CheckBox clothesSelect_all = (CheckBox) view1.findViewById(R.id.clothesSelect_all);
                CheckBox clothesSelect_top = (CheckBox) view1.findViewById(R.id.clothesSelect_top);
                CheckBox clothesSelect_bottom = (CheckBox) view1.findViewById(R.id.clothesSelect_bottom);
                CheckBox clothesSelect_outer = (CheckBox) view1.findViewById(R.id.clothesSelect_outer);
                CheckBox clothesSelect_shoes = (CheckBox) view1.findViewById(R.id.clothesSelect_shoes);
                CheckBox clothesSelect_onepiece = (CheckBox) view1.findViewById(R.id.clothesSelect_onepiece);
                CheckBox clothesSelect_hat = (CheckBox) view1.findViewById(R.id.clothesSelect_hat);
                CheckBox clothesSelect_bag = (CheckBox) view1.findViewById(R.id.clothesSelect_bag);
                CheckBox clothesSelect_etc = (CheckBox) view1.findViewById(R.id.clothesSelect_etc);

                clothesSelect_top.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                                    && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                                    && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                                    && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                                clothesSelect_all.setChecked(true);
                            }
                        } else {
                            clothesSelect_all.setChecked(false);
                        }
                    }
                });

                clothesSelect_bottom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                                    && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                                    && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                                    && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                                clothesSelect_all.setChecked(true);
                            }
                        } else {
                            clothesSelect_all.setChecked(false);
                        }
                    }
                });

                clothesSelect_outer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                                    && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                                    && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                                    && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                                clothesSelect_all.setChecked(true);
                            }
                        } else {
                            clothesSelect_all.setChecked(false);
                        }
                    }
                });

                clothesSelect_shoes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                                    && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                                    && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                                    && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                                clothesSelect_all.setChecked(true);
                            }
                        } else {
                            clothesSelect_all.setChecked(false);
                        }
                    }
                });

                clothesSelect_onepiece.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                                    && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                                    && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                                    && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                                clothesSelect_all.setChecked(true);
                            }
                        } else {
                            clothesSelect_all.setChecked(false);
                        }
                    }
                });

                clothesSelect_hat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                                    && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                                    && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                                    && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                                clothesSelect_all.setChecked(true);
                            }
                        } else {
                            clothesSelect_all.setChecked(false);
                        }
                    }
                });

                clothesSelect_bag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                                    && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                                    && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                                    && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                                clothesSelect_all.setChecked(true);
                            }
                        } else {
                            clothesSelect_all.setChecked(false);
                        }
                    }
                });

                clothesSelect_etc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                                    && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                                    && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                                    && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                                clothesSelect_all.setChecked(true);
                            }
                        } else {
                            clothesSelect_all.setChecked(false);
                        }
                    }
                });

                // 정렬 버튼(옷 종류 - 모두 선택)
                clothesSelect_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            clothesSelect_top.setChecked(true);
                            clothesSelect_bottom.setChecked(true);
                            clothesSelect_outer.setChecked(true);
                            clothesSelect_shoes.setChecked(true);
                            clothesSelect_onepiece.setChecked(true);
                            clothesSelect_hat.setChecked(true);
                            clothesSelect_bag.setChecked(true);
                            clothesSelect_etc.setChecked(true);
                        } else {
                            if (!clothesSelect_top.isChecked() || !clothesSelect_bottom.isChecked()
                                    || !clothesSelect_outer.isChecked() || !clothesSelect_shoes.isChecked()
                                    || !clothesSelect_onepiece.isChecked() || !clothesSelect_hat.isChecked()
                                    || !clothesSelect_bag.isChecked() || !clothesSelect_etc.isChecked()) {
                                clothesSelect_all.setChecked(false);
                            } else {
                                clothesSelect_top.setChecked(false);
                                clothesSelect_bottom.setChecked(false);
                                clothesSelect_outer.setChecked(false);
                                clothesSelect_shoes.setChecked(false);
                                clothesSelect_onepiece.setChecked(false);
                                clothesSelect_hat.setChecked(false);
                                clothesSelect_bag.setChecked(false);
                                clothesSelect_etc.setChecked(false);
                            }
                        }
                    }
                });

                // 정렬 버튼 (계절)
                CheckBox weatherSelect_spring = (CheckBox) view1.findViewById(R.id.weatherSelect_spring);
                CheckBox weatherSelect_summer = (CheckBox) view1.findViewById(R.id.weatherSelect_summer);
                CheckBox weatherSelect_fall = (CheckBox) view1.findViewById(R.id.weatherSelect_fall);
                CheckBox weatherSelect_winter = (CheckBox) view1.findViewById(R.id.weatherSelect_winter);
                CheckBox weatherSelect_communal = (CheckBox) view1.findViewById(R.id.weatherSelect_communal);

                weatherSelect_spring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            weatherSelect_spring.setBackgroundColor(Color.parseColor("#a374db"));
                            if (weatherSelect_summer.isChecked() &&
                                    weatherSelect_fall.isChecked() && weatherSelect_winter.isChecked()) {
                                weatherSelect_communal.setChecked(true);
                                weatherSelect_communal.setBackgroundColor(Color.parseColor("#a374db"));
                            }
                        } else {
                            weatherSelect_spring.setBackgroundColor(Color.parseColor("#e9ecef"));
                            weatherSelect_communal.setChecked(false);
                        }
                    }
                });

                weatherSelect_summer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            weatherSelect_summer.setBackgroundColor(Color.parseColor("#a374db"));
                            if (weatherSelect_spring.isChecked() && weatherSelect_fall.isChecked()
                                    && weatherSelect_winter.isChecked()) {
                                weatherSelect_communal.setChecked(true);
                                weatherSelect_communal.setBackgroundColor(Color.parseColor("#a374db"));
                            }
                        } else {
                            weatherSelect_summer.setBackgroundColor(Color.parseColor("#e9ecef"));
                            weatherSelect_communal.setChecked(false);
                        }
                    }
                });

                weatherSelect_fall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            weatherSelect_fall.setBackgroundColor(Color.parseColor("#a374db"));
                            if (weatherSelect_spring.isChecked() && weatherSelect_summer.isChecked()
                                    && weatherSelect_winter.isChecked()) {
                                weatherSelect_communal.setChecked(true);
                                weatherSelect_communal.setBackgroundColor(Color.parseColor("#a374db"));
                            }
                        } else {
                            weatherSelect_fall.setBackgroundColor(Color.parseColor("#e9ecef"));
                            weatherSelect_communal.setChecked(false);
                        }
                    }
                });

                weatherSelect_winter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            weatherSelect_winter.setBackgroundColor(Color.parseColor("#a374db"));
                            if (weatherSelect_spring.isChecked() && weatherSelect_summer.isChecked()
                                    && weatherSelect_fall.isChecked()) {
                                weatherSelect_communal.setChecked(true);
                                weatherSelect_communal.setBackgroundColor(Color.parseColor("#a374db"));
                            }
                        } else {
                            weatherSelect_winter.setBackgroundColor(Color.parseColor("#e9ecef"));
                            weatherSelect_communal.setChecked(false);
                        }
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
                            weatherSelect_communal.setBackgroundColor(Color.parseColor("#a374db"));
                            weatherSelect_spring.setBackgroundColor(Color.parseColor("#a374db"));
                            weatherSelect_summer.setBackgroundColor(Color.parseColor("#a374db"));
                            weatherSelect_fall.setBackgroundColor(Color.parseColor("#a374db"));
                            weatherSelect_winter.setBackgroundColor(Color.parseColor("#a374db"));

                        } else {
                            weatherSelect_communal.setBackgroundColor(Color.parseColor("#e9ecef"));
                            if (!weatherSelect_spring.isChecked() || !weatherSelect_summer.isChecked()
                                    || !weatherSelect_fall.isChecked() || !weatherSelect_winter.isChecked()) {
                                weatherSelect_communal.setChecked(false);
                            } else {
                                weatherSelect_spring.setChecked(false);
                                weatherSelect_summer.setChecked(false);
                                weatherSelect_fall.setChecked(false);
                                weatherSelect_winter.setChecked(false);
                                weatherSelect_spring.setBackgroundColor(Color.parseColor("#e9ecef"));
                                weatherSelect_summer.setBackgroundColor(Color.parseColor("#e9ecef"));
                                weatherSelect_fall.setBackgroundColor(Color.parseColor("#e9ecef"));
                                weatherSelect_winter.setBackgroundColor(Color.parseColor("#e9ecef"));
                            }
                        }
                    }
                });

                // 정렬 버튼 (하단 정렬)
                CheckBox sortSelect_name = (CheckBox) view1.findViewById(R.id.sortSelect_name);
                CheckBox sortSelect_asc = (CheckBox) view1.findViewById(R.id.sortSelect_asc);

                sortSelect_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(sortSelect_name.isChecked()) {
                            sortSelect_name.setBackgroundColor(Color.parseColor("#ced4da"));
                            sortSelect_name.setText("날짜순정렬");
                        } else {
                            sortSelect_name.setBackgroundColor(Color.parseColor("#e9ecef"));
                            sortSelect_name.setText("이름순정렬");
                        }
                    }
                });
                sortSelect_asc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(sortSelect_asc.isChecked()) {
                            sortSelect_asc.setBackgroundColor(Color.parseColor("#ced4da"));
                            sortSelect_asc.setText("↓ 내림차순");
                        } else {
                            sortSelect_asc.setBackgroundColor(Color.parseColor("#e9ecef"));
                            sortSelect_asc.setText("↑ 오름차순");
                        }
                    }
                });
            }
        });
        
        // MyApplication 클래스에서 DBHelper를 가져오는 코드
        MyApplication app = (MyApplication) getApplicationContext();
        dbHelper = app.getDbHelper();

        // db 파일을 읽기/쓰기가 가능한 형식으로 연다
        db = dbHelper.getWritableDatabase();


        // 임의 데이터 입력을 위한 메서드
        insertDummyData();

        // 임의 데이터 출력을 위한 메서드
        displayData();
        TextView tv_rain,tv_wind,tv_cloud;

        // Spinner 값 출력 (TEST)
        fillSpinner();

        
    }

    // 임의 데이터 입력
    private void insertDummyData() {
        // values 생성 후 Closet_Location 테이블에 임의 값 입력
        ContentValues values = new ContentValues();
        values.put("c_loc", 1);
        values.put("c_loc_name", "옷장1");
        values.put("c_loc_date", "20240723");
        db.insert("Closet_Location", null, values);
        values.put("c_loc", 2);
        values.put("c_loc_name", "옷장2");
        values.put("c_loc_date", "20240806");
        db.insert("Closet_Location", null, values);
        values.put("c_loc", 3);
        values.put("c_loc_name", "봄 옷장");
        values.put("c_loc_date", "20240806");
        db.insert("Closet_Location", null, values);

        // 입력할 값 배열로 정렬
        int[] imageResources = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
        String[] names = {"라운드 반팔티", "청바지", "후드집업", "니트셔츠"};
        String[] types = {"1", "2", "1", "1"};
        String[] sizes = {"100(XL)", "100(XL)", null, null};
        String[] brands = {"MUSINSA", null, null, null};
        String[] memos = {"조금 낡음", null, null, "버릴 예정"};

        // for 문으로 배열 끝까지 Main_Closet 테이블에 임의값 입력
        for (int i = 0; i < 4; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageResources[i]);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            values = new ContentValues();
            values.put("c_id", i + 1);
            values.put("c_loc", 1);
            values.put("c_img", imageBytes);
            values.put("c_name", names[i]);
            values.put("c_type", types[i]);
            values.put("c_size", sizes[i]);
            values.put("c_brand", brands[i]);
            values.put("c_tag", 1);
            values.put("c_memo", memos[i]);
            values.put("c_date", "20240723");
            values.put("c_stack", 0);
            db.insert("Main_Closet", null, values);
        }
    }

    // 임의 데이터 출력, 추후 출력 코드 작성 시 아래와 비슷하게 작성할 예정
    private void displayData() {
        // Main_Closet 테이블의 모든 값을 불러옴
        Cursor cursor = db.query("Main_Closet", null, null, null, null, null, null);

        // 커서 위치 유효성 검사 후 문제가 없으면 해당 코드 실행
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getCount();

            for (int i = 0; i < count; i++) {
                String c_name = cursor.getString(cursor.getColumnIndexOrThrow("c_name"));
                byte[] c_img = cursor.getBlob(cursor.getColumnIndexOrThrow("c_img"));

                Bitmap bitmap = BitmapFactory.decodeByteArray(c_img, 0, c_img.length);

                // getResources().getIdentifier() 메서드를 사용해서 xml 파일의 clothTag1, clothTag2 등의 id 값들을 차례대로 불러옴
                @SuppressLint("DiscouragedApi") int textViewId = getResources().getIdentifier("clothTag" + (i + 1), "id", getPackageName());
                @SuppressLint("DiscouragedApi") int imageButtonId = getResources().getIdentifier("clothImgbtn" + (i + 1), "id", getPackageName());

                TextView textView = findViewById(textViewId);
                ImageButton imageButton = findViewById(imageButtonId);

                // 유효성 검사 후 문제가 없으면 해당 코드 실행 (현재 오류 발생 중, 추후 수정)
                if (textView != null && imageButton != null) {
                    textView.setText(c_name);
                    imageButton.setImageBitmap(bitmap);

                    // 이미지 버튼을 클릭하면 해당하는 컬럼의 모든 데이터를 볼 수 있는 탭으로 이동한다. (DetailActivity.java와 activity_detail.xml 파일 참고)
                    imageButton.setOnClickListener(view -> {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                        intent.putExtra("c_img", cursor.getBlob(cursor.getColumnIndexOrThrow("c_img")));
                        intent.putExtra("c_loc", cursor.getInt(cursor.getColumnIndexOrThrow("c_loc")));
                        intent.putExtra("c_name", cursor.getString(cursor.getColumnIndexOrThrow("c_name")));
                        intent.putExtra("c_type", cursor.getString(cursor.getColumnIndexOrThrow("c_type")));
                        intent.putExtra("c_size", cursor.getString(cursor.getColumnIndexOrThrow("c_size")));
                        intent.putExtra("c_brand", cursor.getString(cursor.getColumnIndexOrThrow("c_brand")));
                        intent.putExtra("c_tag", cursor.getInt(cursor.getColumnIndexOrThrow("c_tag")));
                        intent.putExtra("c_memo", cursor.getString(cursor.getColumnIndexOrThrow("c_memo")));
                        intent.putExtra("c_date", cursor.getString(cursor.getColumnIndexOrThrow("c_date")));
                        intent.putExtra("c_stack", cursor.getInt(cursor.getColumnIndexOrThrow("c_stack")));
                        startActivity(intent);
                    });
                }

                if (!cursor.moveToNext()) {
                    break;
                }
            }
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private Spinner fillSpinner() {
        String SpinnerInfo = "SELECT c_loc_name FROM Closet_Location ORDER BY c_loc ASC";

        Cursor c = db.rawQuery(SpinnerInfo, null);

        MatrixCursor matrixCursor = new MatrixCursor(new String[] {"_id", "c_loc_name"});

        int id = 0;

        while (c.moveToNext()) {
            String c_loc = c.getString(c.getColumnIndex("c_loc_name"));
            matrixCursor.addRow(new Object[] {id++, c_loc});
        }

        c.close();

        String[] from = new String[] {"c_loc_name"};
        int[] to = new int[] {android.R.id.text1};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, matrixCursor, from, to, 0);
        simpleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        Spinner main_c_loc = (Spinner) findViewById(R.id.main_c_loc);
        main_c_loc.setAdapter(simpleCursorAdapter);

        return main_c_loc;
    }


    // MainActivity가 종료 될 때 호출 되는 메서드
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // MyApplication 클래스에서 앱이 정상적으로 종료될 때 db 파일을 닫는 코드가 있지만
        // 정상적으로 종료되지 않았을 때는 db 파일이 닫히지 앋기 때문에 이 곳에도 코드를 추가함
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
