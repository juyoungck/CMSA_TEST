package com.example.closetmanagementservicesapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Main.db"; // 메인 DB에 사용할 이름 설정
    private static final int DB_VERSION = 6; // 메인 DB에 사용할 버전 설정

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION); // 메인 DB 생성
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Closet_Main (_PRODUCT_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name text, Img_Src text)");    // Closet_Main 테이블 생성 (제품ID[기본키], 이름, 이미지주소)
        sqLiteDatabase.execSQL("INSERT INTO Closet_Main VALUES (1, '라운드 반팔티', 'file:///android_asset/image1.png')");
        sqLiteDatabase.execSQL("INSERT INTO Closet_Main VALUES (2, '청바지', 'file:///android_asset/image2.png')");
        sqLiteDatabase.execSQL("INSERT INTO Closet_Main VALUES (3, '?', 'file:///android_asset/image1.png')");
        sqLiteDatabase.execSQL("INSERT INTO Closet_Main VALUES (4, '?', 'file:///android_asset/image1.png')");
                                                //  Closet_Main 테이블 안에 값 추가
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Closet_Main"); // Closet_Main 테이블이 이미 있으면 삭제
        onCreate(sqLiteDatabase);
    } // 테이블 초기화

    public ArrayList<HashMap<String, String>> getTableIndexNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Closet_main", null);
        ArrayList<HashMap<String, String>> resultList = new ArrayList<>();

        while (cursor.moveToNext()) {
            HashMap<String, String> item = new HashMap<>();
            item.put("Name", cursor.getString(cursor.getColumnIndex("Name")));
            item.put("Img_Src", cursor.getString(cursor.getColumnIndex("Img_Src")));
            resultList.add(item);
        }
        cursor.close();
        return resultList;
    } // DB 값 받아서 출력해주는 함수

}
