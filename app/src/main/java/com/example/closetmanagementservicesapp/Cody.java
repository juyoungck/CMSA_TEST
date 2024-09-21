package com.example.closetmanagementservicesapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;
import androidx.core.util.Pair;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Cody extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private GridLayout gridLayout;
    private int imgCounter = 3001;
    private int tagCounter = 4001;
    private int imgViewCounter = 5001;
    private int imgRow = 0;
    private int tagRow = 0;
    private int Call = 0;

    private List<Integer> imgCounterList;
    private List<Integer> imgViewCounterList;
    private List<Integer> tagCounterList;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cody);

        // DB OPEN
        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();

        gridLayout = findViewById(R.id.gl_cody);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.btnCody);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                bottomNavigationView.setItemBackgroundResource(android.R.color.transparent);
                if (itemId == R.id.btnCloset) {
                    bottomNavigationView.setItemBackgroundResource(android.R.color.transparent);
                    startActivity(new Intent(Cody.this, MainActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }

                return false;
            }
        });

        Pair<List<Integer>, List<Integer>> counters = ItemCodyImgBtn(imgCounter);
        imgCounterList = counters.first;
        imgViewCounterList = counters.second;

        ItemCodyTag(tagCounter);

        displayDataCody();

        basicLocation();
        // 코디 위치 스피너 출력
        fillSpinner_cod_loc();

        // 하단 등록 버튼 이동
        ImageButton btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                LinearLayout Add_menu = (LinearLayout)inflater.inflate(R.layout.addmenu, null);

                Add_menu.setBackgroundColor(Color.parseColor("#99000000"));

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
                addContentView(Add_menu,param);

                Button BtnAddClothes = (Button) findViewById(R.id.btnAddClothes);
                Button BtnAddCody = (Button) findViewById(R.id.btnAddCodey);
                ImageButton AddMenuClose = (ImageButton) findViewById(R.id.addMenuClose);

                BtnAddClothes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Post.class);
                        startActivity(intent);
                    }
                });
                BtnAddCody.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), CodyAdd.class);
                        startActivity(intent);
                    }
                });
                AddMenuClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewGroup parentViewGroup = (ViewGroup) Add_menu.getParent();
                        if(parentViewGroup != null) {
                            parentViewGroup.removeView(Add_menu);
                        }
                    }
                });
            }
        });


        // 설정 탭 이동
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
            }
        });

        // 정렬 버튼 창
        ImageButton btnSort = (ImageButton) findViewById(R.id.btnSort);
        btnSort.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Cody.this);
                View tabView = LayoutInflater.from(Cody.this).inflate(R.layout.tab_sort, null);

                bottomSheetDialog.setContentView(tabView);
                bottomSheetDialog.show();

                // 정렬 버튼 닫기
                ImageButton tabsortClose = tabView.findViewById(R.id.tabsortClose);
                tabsortClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });

                // 정렬 기능 호출 (TabSort 클래스)
                TabSort tabsort = new TabSort();

                // 정렬 (옷 종류)
                tabsort.clothesSelect(tabView);

                // 정렬 (날씨)
                tabsort.weatherSelect(tabView);
            }
        });

        // 수정 버튼
        ImageButton btnModify = (ImageButton) findViewById(R.id.btnModify);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(Cody.this);
                View modifyView = LayoutInflater.from(Cody.this).inflate(R.layout.tab_modify, null);

                dialog.setContentView(modifyView);
                dialog.show();

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params.height = 1200;
                dialog.getWindow().setAttributes(params);

                ImageButton tabmodifyClose = modifyView.findViewById(R.id.tabmodifyClose);
                tabmodifyClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TabModify tabModify = new TabModify(Cody.this);

                tabModify.modifyButton(modifyView);
            }
        });
    }

    private void displayDataCody() {
        // Main_Closet 테이블의 모든 값을 불러옴
        Cursor cursor = db.query("Coordy", null, null, null, null, null, null);

        int initialImgCounter = 3001;
        int initialTagCounter = 4001;
        int initialImgViewCounter = 5001;

        // 커서 위치 유효성 검사 후 문제가 없으면 해당 코드 실행
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getCount();

            GridLayout gridLayout = findViewById(R.id.gl_cody);

            for (int i = 0; i < count; i++) {
                String cod_name = cursor.getString(cursor.getColumnIndexOrThrow("cod_name"));

                String cod_thumbnail = cursor.getString(cursor.getColumnIndexOrThrow("cod_thumbnail"));
                String cod_index1 = cursor.getString(cursor.getColumnIndexOrThrow("cod_index1"));
                String cod_index2 = cursor.getString(cursor.getColumnIndexOrThrow("cod_index2"));
                String cod_index3 = cursor.getString(cursor.getColumnIndexOrThrow("cod_index3"));
                String cod_index4 = cursor.getString(cursor.getColumnIndexOrThrow("cod_index4"));
                String cod_index5 = cursor.getString(cursor.getColumnIndexOrThrow("cod_index5"));
                String cod_index6 = cursor.getString(cursor.getColumnIndexOrThrow("cod_index6"));
                String cod_index7 = cursor.getString(cursor.getColumnIndexOrThrow("cod_index7"));
                String cod_index8 = cursor.getString(cursor.getColumnIndexOrThrow("cod_index8"));

                Bitmap thumbitmap = BitmapFactory.decodeFile(cod_thumbnail);
                Bitmap bitmap1 = BitmapFactory.decodeFile(cod_index1);
                Bitmap bitmap2 = BitmapFactory.decodeFile(cod_index2);
                Bitmap bitmap3 = BitmapFactory.decodeFile(cod_index3);
                Bitmap bitmap4 = BitmapFactory.decodeFile(cod_index4);
                Bitmap bitmap5 = BitmapFactory.decodeFile(cod_index5);
                Bitmap bitmap6 = BitmapFactory.decodeFile(cod_index6);
                Bitmap bitmap7 = BitmapFactory.decodeFile(cod_index7);
                Bitmap bitmap8 = BitmapFactory.decodeFile(cod_index8);


                int imgCounter = initialImgCounter + i; // ImageButton의 ID
                int tagCounter = initialTagCounter + i; // TextView의 ID
                int imgViewCounter = initialImgViewCounter + i;

                ImageButton imageButton = (ImageButton) findViewById(imgCounter);
                TextView textView = (TextView) findViewById(tagCounter);
                ImageView imageView = (ImageView) findViewById(imgViewCounter);


                // 유효성 검사 후 문제가 없으면 해당 코드 실행 (현재 오류 발생 중, 추후 수정)
                if (textView != null && imageView != null) {
                    textView.setText(cod_name);
                    imageButton.setImageBitmap(thumbitmap);

                    int batchSize = 16;
                    int imageViewGroupSize = 8;
                    int batchIndex = 0;

                    for (int j = 0; j < imgViewCounterList.size(); j++) {
                        // 새로운 배치일 때 batchIndex 갱신
                        if (j % batchSize == 0 && j != 0) {
                            batchIndex += batchSize;
                        }

                        if (Call % 2 == 0 && j >= batchIndex && j < batchIndex + imageViewGroupSize) {
                            int imgViewId = imgViewCounterList.get(j);
                            imageView = findViewById(imgViewId);

                            int adjustedId = j % imageViewGroupSize;

                            if (adjustedId == 0) {
                                imageView.setImageBitmap(bitmap1);
                            } else if (adjustedId == 1) {
                                imageView.setImageBitmap(bitmap2);
                            } else if (adjustedId == 2) {
                                imageView.setImageBitmap(bitmap3);
                            } else if (adjustedId == 3) {
                                imageView.setImageBitmap(bitmap4);
                            } else if (adjustedId == 4) {
                                imageView.setImageBitmap(bitmap5);
                            } else if (adjustedId == 5) {
                                imageView.setImageBitmap(bitmap6);
                            } else if (adjustedId == 6) {
                                imageView.setImageBitmap(bitmap7);
                            } else if (adjustedId == 7) {
                                imageView.setImageBitmap(bitmap8);
                            }

                        } else if (Call % 2 == 1 && j >= batchIndex + imageViewGroupSize && j < batchIndex + batchSize) {
                            int imgViewId = imgViewCounterList.get(j);
                            imageView = findViewById(imgViewId);
                            int adjustedId = j % imageViewGroupSize;

                            if (adjustedId == 0) {
                                imageView.setImageBitmap(bitmap1);
                            } else if (adjustedId == 1) {
                                imageView.setImageBitmap(bitmap2);
                            } else if (adjustedId == 2) {
                                imageView.setImageBitmap(bitmap3);
                            } else if (adjustedId == 3) {
                                imageView.setImageBitmap(bitmap4);
                            } else if (adjustedId == 4) {
                                imageView.setImageBitmap(bitmap5);
                            } else if (adjustedId == 5) {
                                imageView.setImageBitmap(bitmap6);
                            } else if (adjustedId == 6) {
                                imageView.setImageBitmap(bitmap7);
                            } else if (adjustedId == 7) {
                                imageView.setImageBitmap(bitmap8);
                            }

                        }
                    }

                    Call++;
                    if ((imgCounter - 3000) % 2 == 0) {
                        imgRow++;
                        imgCounter++;
                        imgViewCounter += 8;
                        Pair<List<Integer>, List<Integer>> result = ItemCodyImgBtn(imgCounter);

                        // 새로운 값을 기존 리스트에 추가
                        imgCounterList.clear();  // 기존 리스트 내용 삭제
                        imgCounterList.addAll(result.first);  // 새로운 값으로 갱신

                        imgViewCounterList.clear();  // 기존 리스트 내용 삭제
                        imgViewCounterList.addAll(result.second);  // 새로운 값으로 갱신

                        // 카운터 업데이트
                        imgCounter += imgCounterList.size();
                        imgViewCounter += imgViewCounterList.size();
                    }

                    if ((tagCounter - 4000) % 2 == 0) {
                        tagRow++;
                        tagCounter++;
                        List<Integer> tagCounterList = ItemCodyTag(tagCounter);
                        tagCounter += tagCounterList.size();
                    }

                    int finalI = i;

                }
                cursor.moveToNext();
            }
            cursor.close();
        }
    }


    // 코디 위치 스피너 출력
    private void fillSpinner_cod_loc() {
        Spinner spinner = findViewById(R.id.main_cod_loc);

        List<String> locations = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT cod_loc_name FROM Coordy_Location ORDER BY cod_loc ASC", null);

        while (cursor.moveToNext()) {
            locations.add(cursor.getString(cursor.getColumnIndex("cod_loc_name")));
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void basicLocation() {
        ContentValues values = new ContentValues();
        values.put("c_loc", 1);
        values.put("c_loc_name", "옷장 1");
        values.put("c_loc_date", "2024-09-16");
        db.insert("Closet_Location", null, values);

        values = new ContentValues();
        values.put("cod_loc", 1);
        values.put("cod_loc_name", "코디 1");
        values.put("cod_loc_date", "2024-09-16");
        db.insert("Coordy_Location", null, values);
    }

    private Pair<List<Integer>, List<Integer>> ItemCodyImgBtn(int imgCounter){

        List<Integer> imgCounters = new ArrayList<>();
        List<Integer> imgViewCounters = new ArrayList<>();

        GridLayout gridLayout = findViewById(R.id.gl_cody);
        gridLayout.setPadding(0,-70,0,0);
        gridLayout.setRowCount(50);
        gridLayout.setColumnCount(2);

        // 전체 코디 목록
        for (int col = 0; col < 2; col++) {
            FrameLayout frameLayout = new FrameLayout(this);
            GridLayout.LayoutParams frameParams = new GridLayout.LayoutParams();
            frameParams.width = 625;
            frameParams.height = 700;
            frameParams.setMargins(60, 120,  -190, 0);
            frameLayout.setLayoutParams(frameParams);

            GridLayout innerGridLayout = new GridLayout(this);
            innerGridLayout.setRowCount(4);
            innerGridLayout.setColumnCount(2);

            // 내부 이미지 뷰(2X4)
            for (int row = 0; row < 4; row++) {
                for(int gridCol = 0; gridCol < 2; gridCol++) {

                    ImageView gridImgView = new ImageView(this);
                    gridImgView.setBackgroundColor(Color.parseColor("#efefef"));
                    gridImgView.setId(imgViewCounter);
                    gridImgView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    GridLayout.LayoutParams gridImgViewParams = new GridLayout.LayoutParams();
                    gridImgViewParams.width = 210;
                    gridImgViewParams.height = 150;
                    gridImgViewParams.setMargins(10, 10, 10, 10);
                    gridImgViewParams.rowSpec = GridLayout.spec(row);
                    gridImgViewParams.columnSpec = GridLayout.spec(gridCol);
                    gridImgView.setLayoutParams(gridImgViewParams);

                    innerGridLayout.addView(gridImgView);
                    imgViewCounters.add(imgViewCounter);
                    imgViewCounter++;

                }
            }

            // 대표 이미지(썸네일)
            ImageButton clothImgbtn = new ImageButton(this);
            clothImgbtn.setBackgroundColor(Color.parseColor("#00ff0000"));
            clothImgbtn.setId(imgCounter);
            clothImgbtn.setPadding(0,0,0,0);
            clothImgbtn.setScaleType(ImageView.ScaleType.FIT_XY);

            // GridLayout에 레이아웃 매개변수 설정
            GridLayout.LayoutParams paramsImageButton = new GridLayout.LayoutParams();
            paramsImageButton.width = 440;
            paramsImageButton.height = 660;
            paramsImageButton.setMargins(10, 10, 10, 10);
            paramsImageButton.rowSpec = GridLayout.spec(imgRow * 2);
            paramsImageButton.columnSpec = GridLayout.spec(col);
            clothImgbtn.setLayoutParams(paramsImageButton);

            frameLayout.addView(innerGridLayout);
            frameLayout.addView(clothImgbtn);
            gridLayout.addView(frameLayout);
            imgCounters.add(imgCounter);
            imgCounter++;
        }

        return new Pair<>(imgCounters, imgViewCounters);
    }



    private List<Integer> ItemCodyTag(int tagCounter) {

        List<Integer> tagCounters = new ArrayList<>();

        GridLayout gridLayout = findViewById(R.id.gl_cody);

        for (int col = 0; col < 2; col++) {
            // TextView 생성 및 설정
            TextView clothTag = new TextView(this);
            clothTag.setBackgroundColor(Color.parseColor("#ffffff"));
            clothTag.setGravity(Gravity.CENTER);
            clothTag.setId(tagCounter);


            GridLayout.LayoutParams paramsTextView = new GridLayout.LayoutParams();
            paramsTextView.width = 440;
            paramsTextView.height = 75;
            paramsTextView.setMargins(70, 0, -190, 0);
            paramsTextView.rowSpec = GridLayout.spec(tagRow * 2 + 1);
            paramsTextView.columnSpec = GridLayout.spec(col);
            clothTag.setLayoutParams(paramsTextView);

            // GridLayout에 뷰 추가
            gridLayout.addView(clothTag);
            tagCounters.add(tagCounter);
            tagCounter++;
        }

        return tagCounters;
    }
}
