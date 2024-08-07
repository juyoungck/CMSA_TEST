package com.example.closetmanagementservicesapp;

import android.app.Application;

public class MyApplication extends Application {
    private static DBHelper dbHelper;

    public void onCreate() {
        super.onCreate();

        // DBHelper 생성
        dbHelper = DBHelper.getInstance(this);
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    // 앱이 사용자에 의해 정상적으로 종료될 때 호출 되는 메서드
    @Override
    public void onTerminate() {
        super.onTerminate();

        // 앱 종료 시 db 파일 닫음
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
