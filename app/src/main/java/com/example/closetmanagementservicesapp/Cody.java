package com.example.closetmanagementservicesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

public class Cody extends AppCompatActivity implements WeatherDataCallback {
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
    String date = "", time = "";
    String x = "55", y = "127";
    private GpsHelper gpsHelper;
    private ExcelReader excelReader;
    TextView weatherTextView,timeNow;
    ImageView imageViewIcon;
    static String sky = "";
    static String sky_state = "";
    static float temp = 0;

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
                View tabView = LayoutInflater.from(Cody.this).inflate(R.layout.tab_sort_cody, null);

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
                TabSort_Cody tabsort = new TabSort_Cody();

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
                View modifyView = LayoutInflater.from(Cody.this).inflate(R.layout.tab_modify_cody, null);

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
                TabModifyCody tabModifyCody = new TabModifyCody(Cody.this);

                tabModifyCody.modifyButton(modifyView);

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        recreate();  // 액티비티 재생성
                    }
                });
            }
        });

        //날씨 코드
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

                Toast.makeText(Cody.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
                System.out.println("격자값 x: " + x + ", y: " + y);
                //날씨 재동기화
                weatherTextView = findViewById(R.id.weatherDegree);
                imageViewIcon = findViewById(R.id.btnWeather);
                WeatherData wd = new WeatherData(weatherTextView,imageViewIcon, Cody.this);
                wd.fetchWeather(getDate, getTime, x, y);  // 비동기적으로 날씨 데이터를 가져옴
            }
        });
        weatherTextView = findViewById(R.id.weatherDegree);
        imageViewIcon = findViewById(R.id.btnWeather);
        WeatherData wd = new WeatherData(weatherTextView,imageViewIcon, this);
        wd.fetchWeather(getDate, getTime, x, y);  // 비동기적으로 날씨 데이터를 가져옴

        // 코디 추천 버튼
        ImageButton cod_rec = (ImageButton) findViewById(R.id.cod_rec);
        cod_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> tagArgs = getTagArgs();

                StringBuilder tags_builder = new StringBuilder();
                tags_builder.append("cod_tag IN (");
                for (int i = 0; i < tagArgs.size(); i++) {
                    tags_builder.append("?");
                    if (i < tagArgs.size() - 1) {
                        tags_builder.append(", ");
                    }
                }
                tags_builder.append(")");

                Log.d("tagArgs", tags_builder.toString());
                Log.d("tagArgs", String.valueOf(tagArgs));

                Cursor cursor = db.query("Coordy", new String[]{"cod_id", "cod_name"}, tags_builder.toString(), tagArgs.toArray(new String[0]), null, null, null);
                ArrayList<Integer> codIdList = new ArrayList<>();
                ArrayList<String> codNameList = new ArrayList<>();

                if (cursor.moveToFirst()) {
                    do {
                        int codId = cursor.getInt(cursor.getColumnIndex("cod_id"));
                        String codName = cursor.getString(cursor.getColumnIndex("cod_name"));
                        codIdList.add(codId);
                        codNameList.add(codName);
                    } while (cursor.moveToNext());
                }
                cursor.close();

                if (!codIdList.isEmpty()) {
                    Random rand = new Random();
                    int randIndex = rand.nextInt(codIdList.size());
                    int randCodId = codIdList.get(randIndex);
                    String randCodName = codNameList.get(randIndex);

                    AlertDialog.Builder builder = new AlertDialog.Builder(Cody.this);
                    builder.setTitle("오늘의 추천 코디");
                    builder.setMessage("추천 코디는 '" + randCodName + "' 코디입니다!\n해당 코디를 보러 가시겠습니까?");

                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new Thread(() -> {
                                Cursor detailCursor = db.query("Coordy", null, "cod_id = ?", new String[]{String.valueOf(randCodId)}, null, null, null);

                                if(detailCursor !=null && detailCursor.moveToFirst()) {
                                    Intent intent = new Intent(Cody.this, DetailCody.class);
                                    intent.putExtra("cod_id", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("cod_id")));
                                    intent.putExtra("cod_img", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_img")));
                                    intent.putExtra("cod_loc", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("cod_loc")));
                                    intent.putExtra("cod_name", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_name")));
                                    intent.putExtra("cod_tag", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("cod_tag")));
                                    intent.putExtra("cod_date", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_date")));
                                    intent.putExtra("cod_stack", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("cod_stack")));

                                    intent.putExtra("cod_index1", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index1")));
                                    intent.putExtra("cod_index2", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index2")));
                                    intent.putExtra("cod_index3", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index3")));
                                    intent.putExtra("cod_index4", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index4")));
                                    intent.putExtra("cod_index5", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index5")));
                                    intent.putExtra("cod_index6", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index6")));
                                    intent.putExtra("cod_index7", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index7")));
                                    intent.putExtra("cod_index8", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index8")));

                                    // 커서 닫기 및 인텐트 실행은 UI 스레드에서 실행
                                    runOnUiThread(() -> {
                                        startActivity(intent);
                                        detailCursor.close(); // 사용 후 커서 닫기
                                    });
                                } else if (detailCursor != null) {
                                    detailCursor.close();
                                }
                            }).start();
                        }
                    });

                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Cody.this);
                    builder.setTitle("코디 추천 불가");
                    builder.setMessage("현재 날씨에 적합한 추천 코디가 없습니다.\n더 많은 코디를 등록해 보세요!\n");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();  // 다이얼로그 닫기
                        }
                    });

                    // 다이얼로그 보여주기
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    @Override
    public void onWeatherDataResult(String sky_date, String sky_state_date, float temp_date) {
        sky = sky_date;
        sky_state = sky_state_date;
        temp = temp_date;
        Log.d("MainActivity", "날씨 상태: " + sky + ", 강수 상태: " + sky_state + ", 온도: " + temp);
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
                String cod_img = cursor.getString(cursor.getColumnIndexOrThrow("cod_img"));

                Bitmap thumbBitmap = BitmapFactory.decodeFile(cod_img);

                Integer[] cod_indices = new Integer[8];
                for (int idx = 0; idx < 8; idx++) {
                    String columnName = "cod_index" + (idx + 1);
                    if (!cursor.isNull(cursor.getColumnIndex(columnName))) {
                        cod_indices[idx] = cursor.getInt(cursor.getColumnIndex(columnName));
                    } else {
                        cod_indices[idx] = null;
                    }
                }

                // 각 cod_index에 해당하는 c_img 경로를 가져와 비트맵 생성
                Bitmap[] bitmaps = new Bitmap[8];
                for (int j = 0; j < 8; j++) {
                    if (cod_indices[j] != null) {
                        // Main_Closet에서 c_id로 c_img 경로 가져오기
                        Cursor closetCursor = db.query("Main_Closet", new String[]{"c_img"},
                                "c_id=?", new String[]{String.valueOf(cod_indices[j])}, null, null, null);
                        if (closetCursor != null && closetCursor.moveToFirst()) {
                            String c_img = closetCursor.getString(closetCursor.getColumnIndexOrThrow("c_img"));
                            Bitmap bitmap = BitmapFactory.decodeFile(c_img);
                            bitmaps[j] = bitmap;
                            closetCursor.close();
                        } else {
                            bitmaps[j] = null;
                        }
                    } else {
                        bitmaps[j] = null;
                    }
                }


                int imgCounter = initialImgCounter + i; // ImageButton의 ID
                int tagCounter = initialTagCounter + i; // TextView의 ID
                int imgViewCounter = initialImgViewCounter + i;

                ImageButton imageButton = (ImageButton) findViewById(imgCounter);
                TextView textView = (TextView) findViewById(tagCounter);
                ImageView imageView = (ImageView) findViewById(imgViewCounter);


                // 유효성 검사 후 문제가 없으면 해당 코드 실행 (현재 오류 발생 중, 추후 수정)
                if (textView != null && imageButton != null) {
                    textView.setText(cod_name);
                    imageButton.setImageBitmap(thumbBitmap);

                    if (textView != null && imageView != null) {
                        textView.setText(cod_name);
                        imageButton.setImageBitmap(thumbBitmap);

                        int totalImages = bitmaps.length; // 전체 이미지 수
                        int batchSize = 8; // 한 번에 처리할 이미지 수

                        for (int batchStart = 0; batchStart < totalImages; batchStart += batchSize) {
                            // 각 배치마다 8개의 이미지를 처리
                            for (int j = 0; j < batchSize; j++) {
                                int index = batchStart + j;
                                if (index >= totalImages) {
                                    break; // 인덱스가 총 이미지 수를 넘으면 종료
                                }
                                int imgViewId = imgViewCounter + index + ((batchSize - 1) * Call);
                                imageView = (ImageView) findViewById(imgViewId);
                                if (imageView != null) {
                                    if (bitmaps[index] != null) {
                                        imageView.setImageBitmap(bitmaps[index]);

                                        // cod_id 값을 태그로 저장 (codIdValues는 cod_id 배열)
                                        imageView.setTag(cod_indices[index]);


                                        Log.d("ImageAssignment", "ImageView ID: " + imgViewId + ", Bitmap Index: " + index);
                                    } else {
                                        imageView.setImageBitmap(null); // 이미지가 없으면 빈 이미지로 설정
                                    }
                                }
                            }
                        }

                        Call++; // 실행 횟수 증가
                    }


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

                    imageButton.setOnClickListener(view -> {
                        new Thread(() -> {
                            Cursor detailCursor = db.query("Coordy", null, null, null, null, null, null);
                            if (detailCursor != null && detailCursor.moveToPosition(finalI)) {
                                Intent getIntent = new Intent(Cody.this, DetailCody.class);
                                getIntent.putExtra("cod_id", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("cod_id")));
                                getIntent.putExtra("cod_img", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_img")));
                                getIntent.putExtra("cod_loc", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("cod_loc")));
                                getIntent.putExtra("cod_name", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_name")));
                                getIntent.putExtra("cod_tag", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("cod_tag")));
                                getIntent.putExtra("cod_date", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_date")));
                                getIntent.putExtra("cod_stack", detailCursor.getInt(detailCursor.getColumnIndexOrThrow("cod_stack")));

                                getIntent.putExtra("cod_index1", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index1")));
                                getIntent.putExtra("cod_index2", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index2")));
                                getIntent.putExtra("cod_index3", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index3")));
                                getIntent.putExtra("cod_index4", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index4")));
                                getIntent.putExtra("cod_index5", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index5")));
                                getIntent.putExtra("cod_index6", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index6")));
                                getIntent.putExtra("cod_index7", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index7")));
                                getIntent.putExtra("cod_index8", detailCursor.getString(detailCursor.getColumnIndexOrThrow("cod_index8")));

                                // 커서 닫기 및 인텐트 실행은 UI 스레드에서 실행
                                runOnUiThread(() -> {
                                    startActivity(getIntent);
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

    private ArrayList<String> getTagArgs() {
        ArrayList<String> tagList = new ArrayList<>();

        if (sky.equals("맑음") && sky_state.equals("없음")) {
            temp -= 0.0;
        } else if ((!sky.equals("맑음") && sky_state.equals("없음")) || (sky.equals("맑음") && (sky_state.equals("비") || sky_state.equals("소나기")))) {
            temp -= 0.5;
        } else if ((!sky.equals("맑음") && sky_state.equals("소나기"))) {
            temp -= 1.0;
        } else if ((!sky.equals("맑음") && sky_state.equals("비")) || (sky.equals("맑음") && (sky_state.equals("비/눈") || sky_state.equals("눈")))) {
            temp -= 1.5;
        } else if ((!sky.equals("맑음") && (sky_state.equals("비/눈") || sky_state.equals("눈")))) {
            temp -= 2.0;
        }

        if (temp <= -10.0) {
            tagList.add("4");
        } else if (temp >= -9.9 && temp <= 0.0) {
            tagList.addAll(Arrays.asList("4", "7", "10", "13", "15"));
        } else if (temp >= 0.1 && temp <= 15.0) {
            tagList.addAll(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"));
        } else if (temp >= 15.1 && temp <= 25.0) {
            tagList.addAll(Arrays.asList("1", "2", "3", "5", "6", "8", "11", "15"));
        } else if (temp >= 25.1) {
            tagList.addAll(Arrays.asList("2", "3", "5", "6", "8", "11"));
        }

        return tagList;
    }

}
