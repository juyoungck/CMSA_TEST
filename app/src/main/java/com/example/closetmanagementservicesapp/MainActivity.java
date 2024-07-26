package com.example.closetmanagementservicesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // 중앙 하단 등록 버튼 터치 시 등록 버튼 클릭 화면으로 넘어가는 기능
        Button button = (Button) findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), codyAdd.class);
                startActivity(intent);
            }
        });

        String date = "", time = "";

        String x = "55", y= "127";


        String weather = "";
        ImageView iv_weather_back;
        ImageView weather_image;


        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        // 날짜, 시간의 형식 설정
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH");


        // 현재 날짜를 받아오는 형식 설정 ex) 20221121
        String getDate = simpleDateFormat1.format(mDate);
        // 현재 시간를 받아오는 형식 설정, 시간만 가져오고 WeatherData의 timechange()를 사용하기 위해 시간만 가져오고 뒤에 00을 붙임 ex) 02 + "00"
        String getTime = simpleDateFormat2.format(mDate) + "00";

        Log.d("현재날짜",getDate);
        Log.d("현재시간",getTime);


        WeatherData wd = new WeatherData();
        date = getDate;
        time = getTime;
        try {
            weather = wd.lookUpWeather(date, time, x, y);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.out.println("Error");
        } catch (JSONException e) {
            //throw new RuntimeException(e);
            System.out.println("Error");
        }
        Log.d("현재날씨",weather);

        // return한 값을 " " 기준으로 자른 후 배열에 추가
        // array[0] = 구름의 양, array[1] = 강수 확률, array[2] = 기온, array[3] = 풍속, array[4] = 적설량, array[5] = 습도
        String[] weatherarray = weather.split(" ");
        for(int i = 0; i < weatherarray.length; i++) {
            Log.d("weather = ", i + " " + weatherarray[i]);
        }



    }

    // 임의 데이터 입력
    private void insertDummyData() {
        // values 생성 후 Closet_Location 테이블에 임의 값 입력
        ContentValues values = new ContentValues();
        values.put("c_loc", 1);
        values.put("c_loc_name", "옷장1");
        values.put("c_loc_date", "20240723");
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

                        intent.putExtra("c_img", c_img);
                        intent.putExtra("c_loc", cursor.getInt(cursor.getColumnIndexOrThrow("c_loc")));
                        intent.putExtra("c_name", c_name);
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
