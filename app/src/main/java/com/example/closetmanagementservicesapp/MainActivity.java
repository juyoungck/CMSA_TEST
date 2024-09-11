package com.example.closetmanagementservicesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase db;


    String date = "", time = "";
    String x = "55", y = "127";

    TextView weatherTextView,timeNow;

    ImageView imageViewIcon;

    private GpsHelper gpsHelper;

    private ExcelReader excelReader;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private GridLayout gridLayout;

    private int imgCounter = 1001;
    private int tagCounter = 2001;
    private int imgRow = 0;
    private int tagRow = 0;

    BottomNavigationView bottomNavigationView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB OPEN
        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();

        gridLayout = findViewById(R.id.gl_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                bottomNavigationView.setItemBackgroundResource(android.R.color.transparent);
                if (itemId == R.id.btnCody) {
                    bottomNavigationView.setItemBackgroundResource(android.R.color.transparent);
                    startActivity(new Intent(MainActivity.this, Cody.class));
                    overridePendingTransition(0, 0);
                    return true;
                }

                return false;
            }
        });

        // 하단 등록 버튼(+) 이동
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
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                View tabView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_sort, null);

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
                Dialog dialog = new Dialog(MainActivity.this);
                View modifyView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_modify, null);

                dialog.setContentView(modifyView);
                dialog.show();

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width=WindowManager.LayoutParams.WRAP_CONTENT;
                params.height=1200;
                dialog.getWindow().setAttributes(params);

                ImageButton tabmodifyClose = modifyView.findViewById(R.id.tabmodifyClose);
                tabmodifyClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TabModify tabModify = new TabModify(MainActivity.this);

                tabModify.modifyButton(modifyView);
            }
        });

        // 중앙 이미지 레이아웃과 이미지 호출
        List<Integer> imgCounterList = ItemAddImg(imgCounter);

        // 중앙 태그 레이아웃과 태그 호출
        List<Integer> tagCounterList = ItemAddTag(tagCounter);

        // 기본 옷장, 코디 위치 추가
        basicLocation();

        // 임의 데이터 출력을 위한 메서드
        displayData();

        // 옷장 위치 스피너 출력
        fillSpinner_c_loc();


        //날씨, gps 코드
        /*long now = System.currentTimeMillis();
        Date mDate = new Date(now);*/

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH", Locale.KOREA);
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("HH:mm", Locale.KOREA);
        TimeZone KoreaTime = TimeZone.getTimeZone("Asia/Seoul");
        simpleDateFormat1.setTimeZone(KoreaTime);
        simpleDateFormat2.setTimeZone(KoreaTime);
        simpleDateFormat3.setTimeZone(KoreaTime);
        Date date = new Date();
        Log.d("시간",simpleDateFormat1.format(date));
        Log.d("시간",simpleDateFormat2.format(date));
        Log.d("시간",simpleDateFormat3.format(date));

        String getDate = simpleDateFormat1.format(date);
        String getTime =  simpleDateFormat2.format(date)+ "00";

        gpsHelper = new GpsHelper(this);
        excelReader = new ExcelReader(this);

        gpsHelper.initializeGps();

        final Button textview_address = findViewById(R.id.refresh);

        ImageButton ShowLocationButton = findViewById(R.id.btnLocation);

        timeNow = findViewById(R.id.timeNow);
        morningAfternoon ma = new morningAfternoon(simpleDateFormat3.format(date));
        String abc = ma.asd();
        timeNow.setText(abc);

        ShowLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                double[] location = gpsHelper.getCurrentLocation();
                double latitude = location[0];
                double longitude = location[1];

                String address = gpsHelper.getCurrentAddress(latitude, longitude);
                textview_address.setText("수정구 복정동");//textview_address.setText(address);

                String[] local = address.split(" ");
                String localName = local[3];
                localName ="복정동"; //임시값
                String[] gridCoordinates = excelReader.readExcel(localName);
                String x = gridCoordinates[0];
                String y = gridCoordinates[1];

                Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
                System.out.println("격자값 x: " + x + ", y: " + y);
                //날씨 재동기화
                weatherTextView = findViewById(R.id.weatherDegree);
                imageViewIcon = findViewById(R.id.btnWeather);
                WeatherData wd = new WeatherData(weatherTextView,imageViewIcon);
                wd.fetchWeather(getDate, getTime, x, y);  // 비동기적으로 날씨 데이터를 가져옴
            }
        });
        weatherTextView = findViewById(R.id.weatherDegree);
        imageViewIcon = findViewById(R.id.btnWeather);
        WeatherData wd = new WeatherData(weatherTextView,imageViewIcon);
        wd.fetchWeather(getDate, getTime, x, y);  // 비동기적으로 날씨 데이터를 가져옴
    }


    // 임의 데이터 출력, 추후 출력 코드 작성 시 아래와 비슷하게 작성할 예정
    private void displayData() {
        // Main_Closet 테이블의 모든 값을 불러옴
        Cursor cursor = db.query("Main_Closet", null, null, null, null, null, null);

        int initialImgCounter = 1001;
        int initialTagCounter = 2001;

        // 커서 위치 유효성 검사 후 문제가 없으면 해당 코드 실행
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getCount();

            GridLayout gridLayout = findViewById(R.id.gl_main);

            for (int i = 0; i < count; i++) {
                String c_name = cursor.getString(cursor.getColumnIndexOrThrow("c_name"));
                String c_img = cursor.getString(cursor.getColumnIndexOrThrow("c_img"));
                Bitmap bitmap = BitmapFactory.decodeFile(c_img);

                int imgCounter = initialImgCounter + i; // ImageButton의 ID
                int tagCounter = initialTagCounter + i; // TextView의 ID

                ImageButton imageButton = (ImageButton) findViewById(imgCounter);
                TextView textView = (TextView) findViewById(tagCounter);

                // 유효성 검사 후 문제가 없으면 해당 코드 실행 (현재 오류 발생 중, 추후 수정)
                if (textView != null && imageButton != null) {
                    textView.setText(c_name);
                    imageButton.setImageBitmap(bitmap);

                    if ((imgCounter - 1000) % 3 == 0) {
                        imgRow++;
                        imgCounter++;
                        List<Integer> imgCounterList = ItemAddImg(imgCounter);
                        imgCounter += imgCounterList.size();
                    }

                    if ((tagCounter - 2000) % 3 == 0) {
                        tagRow++;
                        tagCounter++;
                        List<Integer> tagCounterList = ItemAddTag(tagCounter);
                        tagCounter += tagCounterList.size();
                    }

                    int finalI = i;

                    // 이미지 버튼을 클릭하면 해당하는 컬럼의 모든 데이터를 볼 수 있는 탭으로 이동한다. (DetailActivity.java와 activity_detail.xml 파일 참고)
                    imageButton.setOnClickListener(view -> {
                        new Thread(() -> {
                            Cursor detailCursor = db.query("Main_Closet", null, null, null, null, null, null);
                            if (detailCursor != null && detailCursor.moveToPosition(finalI)) {
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("c_id", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("c_id")));
                                intent.putExtra("c_img", detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_img")));
                                intent.putExtra("c_loc", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("c_loc")));
                                intent.putExtra("c_name", detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_name")));
                                intent.putExtra("c_type", detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_type")));
                                intent.putExtra("c_size", detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_size")));
                                intent.putExtra("c_brand", detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_brand")));
                                intent.putExtra("c_tag", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("c_tag")));
                                intent.putExtra("c_memo", detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_memo")));
                                intent.putExtra("c_date", detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_date")));
                                intent.putExtra("c_stack", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("c_stack")));

                                // 커서 닫기 및 인텐트 실행은 UI 스레드에서 실행
                                runOnUiThread(() -> {
                                    startActivity(intent);
                                    detailCursor.close(); // 사용 후 커서 닫기
                                });
                            } else if (detailCursor != null) {
                                detailCursor.close(); // 커서가 유효하지 않을 경우에도 닫기
                            }
                        }).start();
                    });
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    // 옷장 위치 스피너 출력
    private void fillSpinner_c_loc() {
        Spinner spinner = findViewById(R.id.main_c_loc);

        List<String> locations = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT c_loc_name FROM Closet_Location ORDER BY c_loc ASC", null);

        while (cursor.moveToNext()) {
            locations.add(cursor.getString(cursor.getColumnIndex("c_loc_name")));
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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

    private void basicLocation() {
        ContentValues values = new ContentValues();
        values.put("c_loc", 1);
        values.put("c_loc_name", "옷장 1");
        values.put("c_loc_date", getToday());
        db.insert("Closet_Location", null, values);

        values = new ContentValues();
        values.put("cod_loc", 1);
        values.put("cod_loc_name", "코디 1");
        values.put("cod_loc_date", getToday());
        db.insert("Coordy_Location", null, values);
    }

    private String getToday() {
        DateFormat Today = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN);
        TimeZone KoreaTime = TimeZone.getTimeZone("Asia/Seoul");
        Today.setTimeZone(KoreaTime);

        Date date = new Date();

        return Today.format(date);
    }

    //위치 권한 코드
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        gpsHelper.checkRunTimePermission();
    }

    private List<Integer> ItemAddImg(int imgCounter){

        List<Integer> imgCounters = new ArrayList<>();

        GridLayout gridLayout = findViewById(R.id.gl_main);
        gridLayout.setRowCount(50);
        gridLayout.setColumnCount(3);


            for (int col = 0; col < 3; col++) {
                // ImageButton
                ImageButton clothImgbtn = new ImageButton(this);
                clothImgbtn.setBackgroundColor(Color.parseColor("#00ff0000"));
                clothImgbtn.setPadding(150, 150, 150, 150);
                clothImgbtn.setId(imgCounter);

                // GridLayout에 레이아웃 매개변수 설정
                GridLayout.LayoutParams paramsImageButton = new GridLayout.LayoutParams();
                paramsImageButton.width = 300;
                paramsImageButton.height = 300;
                paramsImageButton.setMargins(30, 45, 30, 0);
                paramsImageButton.rowSpec = GridLayout.spec(imgRow * 2);
                paramsImageButton.columnSpec = GridLayout.spec(col);
                clothImgbtn.setLayoutParams(paramsImageButton);

                // GridLayout에 뷰 추가
                gridLayout.addView(clothImgbtn);
                imgCounters.add(imgCounter);
                imgCounter++;
            }


        return imgCounters;
    }

    private List<Integer> ItemAddTag(int tagCounter) {

        List<Integer> tagCounters = new ArrayList<>();

        GridLayout gridLayout = findViewById(R.id.gl_main);
        gridLayout.setRowCount(50);
        gridLayout.setColumnCount(3);


            for (int col = 0; col < 3; col++) {

                // TextView 생성 및 설정
                TextView clothTag = new TextView(this);
                clothTag.setBackgroundColor(Color.parseColor("#00ff0000"));
                clothTag.setGravity(Gravity.CENTER);
                clothTag.setId(tagCounter);

                GridLayout.LayoutParams paramsTextView = new GridLayout.LayoutParams();
                paramsTextView.width = 300;
                paramsTextView.height = 75;
                paramsTextView.setMargins(30, 0, 30, 0);
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

