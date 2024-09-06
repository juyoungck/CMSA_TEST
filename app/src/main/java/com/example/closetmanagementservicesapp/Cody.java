package com.example.closetmanagementservicesapp;

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
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.view.View;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cody);

        // DB OPEN
        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();

        gridLayout = findViewById(R.id.gl_cody);

        // 코디 위치 스피너 출력
        fillSpinner_cod_loc();

        List<Integer> imgCounterList = ItemCodyImgBtn(imgCounter);

        List<Integer> tagCounterList = ItemCodyTag(tagCounter);


        /**
        int initialImgCounter = 3001;
        int initialTagCounter = 4001;
        int initialImgViewCounter = 5001;

        int imgCounter = initialImgCounter + i; // ImageButton의 ID
        int tagCounter = initialTagCounter + i; // TextView의 ID
        int imgViewCounter = initialImgViewCounter + i;

        ImageButton imageButton = (ImageButton) findViewById(imgCounter);
        TextView textView = (TextView) findViewById(tagCounter);
        ImageView imageView = (ImageView) findViewById(imgViewCounter);

        if ((imgCounter - 3000) % 2 == 0) {
            imgRow++;
            imgCounter++;
            List<Integer> imgCounterList = ItemCodyImgBtn(imgCounter);
            imgCounter += imgCounterList.size();
        }

        if ((tagCounter - 4000) % 2 == 0) {
            tagRow++;
            tagCounter++;
            List<Integer> tagCounterList = ItemCodyTag(tagCounter);
            tagCounter += tagCounterList.size();
        }

        // 내부 이미지뷰(2X4) 코드 ******5001번 부터 시작********

        switch (imgViewCounter - 5000) % 8) {
            case 1:
                상의
                break;
            case 2:
                하의
                break;
            case 3:
                신발
                break;
            case 4:
                외투
                break;
            case 5:
                속옷
                break;
            case 6:
                양말
                break;
            case 7:
                악세사리
                break;
            case 0:    // 8로 처리
                가방
                break;
        }
        */


        // 하단 등록 버튼 이동
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
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


        // 메인 탭 이동
        Button btnCloset = (Button) findViewById(R.id.btnCloset);
        btnCloset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
                TabModify tabModify = new TabModify();

                tabModify.modifyButton(modifyView);
            }
        });
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

    private List<Integer> ItemCodyImgBtn(int imgCounter){

        List<Integer> imgCounters = new ArrayList<>();

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
                    // GridLayout 내부에 ImageButton 추가
                    ImageView gridImgView = new ImageView(this);
                    gridImgView.setBackgroundColor(Color.parseColor("#000000"));

                    gridImgView.setPadding(10, 10, 10, 10);

                    gridImgView.setId(imgViewCounter);

                    GridLayout.LayoutParams gridImgViewParams = new GridLayout.LayoutParams();
                    gridImgViewParams.width = 210;
                    gridImgViewParams.height = 150;
                    gridImgViewParams.setMargins(10, 10, 10, 10);
                    gridImgViewParams.rowSpec = GridLayout.spec(row);
                    gridImgViewParams.columnSpec = GridLayout.spec(gridCol);
                    gridImgView.setLayoutParams(gridImgViewParams);

                    innerGridLayout.addView(gridImgView);
                    imgCounters.add(imgViewCounter);
                    imgViewCounter++;
                }
            }

            // 이미지 버튼
            ImageButton clothImgbtn = new ImageButton(this);
            clothImgbtn.setBackgroundColor(Color.parseColor("#00ff0000"));
            clothImgbtn.setPadding(10,10,10,10);
            clothImgbtn.setId(imgCounter);

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

        return imgCounters;
    }

    private List<Integer> ItemCodyTag(int tagCounter) {

        List<Integer> tagCounters = new ArrayList<>();

        GridLayout gridLayout = findViewById(R.id.gl_cody);

        for (int col = 0; col < 2; col++) {
            // TextView 생성 및 설정
            TextView clothTag = new TextView(this);
            clothTag.setBackgroundColor(Color.parseColor("#00ff0000"));
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
