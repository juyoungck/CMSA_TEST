package com.example.closetmanagementservicesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "CMSA_Main.db";   // 메인 DB 이름 설정
    private static final int DB_VERSION = 1;                // 메인 DB 버전 설정
    private static DBHelper instance;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);   // 싱글톤 패턴을 적용해 메인 DB 생성
    }

    // DBHelper의 인스턴스를 반환하는 메서드
    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 외래 키(FK) 사용 활성화
        db.execSQL("PRAGMA foreign_keys=ON;");

        // 옷장 위치 테이블
        String Closet_Location_TABLE = "CREATE TABLE Closet_Location ("
                + "c_loc INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "c_loc_name TEXT NOT NULL, "
                + "c_loc_date TEXT NOT NULL)";

        // 코디 위치 테이블
        String Coordy_Location_TABLE = "CREATE TABLE Coordy_Location ("
                + "cod_loc INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "cod_loc_name TEXT NOT NULL, "
                + "cod_loc_date TEXT NOT NULL)";

        // 코디 정보 테이블
        String Coordy_TABLE = "CREATE TABLE Coordy ("
                + "cod_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "cod_loc INTEGER NOT NULL, "
                + "cod_name TEXT NOT NULL, "
                + "cod_tag INTEGER NOT NULL, "
                + "cod_date TEXT NOT NULL, "
                + "cod_stack INTEGER NOT NULL, "
                + "cod_img TEXT, "
                + "cod_index1 INTEGER, "
                + "cod_index2 INTEGER, "
                + "cod_index3 INTEGER, "
                + "cod_index4 INTEGER, "
                + "cod_index5 INTEGER, "
                + "cod_index6 INTEGER, "
                + "cod_index7 INTEGER, "
                + "cod_index8 INTEGER, "
                + "FOREIGN KEY(cod_loc) REFERENCES Coordy_Location(cod_loc))";

        // 옷 정보 테이블
        String Main_Closet_TABLE = "CREATE TABLE Main_Closet ("
                + "c_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "c_loc INTEGER NOT NULL, "
                + "c_img TEXT NOT NULL, "
                + "c_name TEXT NOT NULL, "
                + "c_type TEXT NOT NULL, "
                + "c_size TEXT, "
                + "c_brand TEXT, "
                + "c_tag INTEGER NOT NULL, "
                + "c_memo TEXT, "
                + "c_date TEXT NOT NULL, "
                + "c_stack INTEGER NOT NULL, "
                + "FOREIGN KEY(c_loc) REFERENCES Closet_Location(c_loc))";

        // 테이블 생성
        db.execSQL(Closet_Location_TABLE);
        db.execSQL(Coordy_Location_TABLE);
        db.execSQL(Coordy_TABLE);
        db.execSQL(Main_Closet_TABLE);
    }


    // db 버전이 Upgrade 될 때 실행됨
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 백업 테이블 생성
        db.execSQL("ALTER TABLE Closet_Location RENAME TO Closet_Location_backup;");
        db.execSQL("ALTER TABLE Coordy_Location RENAME TO Coordy_Location_backup;");
        db.execSQL("ALTER TABLE Coordy RENAME TO Coordy_backup;");
        db.execSQL("ALTER TABLE Main_Closet RENAME TO Main_Closet_backup;");

        // 기존 테이블 재생성(모든 값 초기화 된 상태)
        onCreate(db);

        // 재생성 된 기존 테이블에 백업 테이블 복사
        db.execSQL("INSERT INTO Closet_Location (c_loc, c_loc_name, c_loc_date) "
                + "SELECT c_loc, c_loc_name, c_loc_date FROM Closet_Location_backup;");

        db.execSQL("INSERT INTO Coordy_Location (cod_loc, cod_loc_name, cod_loc_date) "
                + "SELECT cod_loc, cod_loc_name, cod_loc_date FROM Coordy_Location_backup;");

        db.execSQL("INSERT INTO Coordy (cod_id, cod_loc, cod_name, cod_tag, cod_date, cod_stack, "
                + "cod_img, cod_index1, cod_index2, cod_index3, cod_index4, cod_index5, cod_index6, cod_index7, cod_index8) "
                + "SELECT cod_id, cod_loc, cod_name, cod_tag, cod_date, cod_stack, "
                + "cod_index1, cod_index2, cod_index3, cod_index4, cod_index5, cod_index6, cod_index7, cod_index8 "
                + "FROM Coordy_backup;");

        db.execSQL("INSERT INTO Main_Closet (c_id, c_loc, c_img, c_name, c_type, c_size, c_brand, c_tag, c_memo, c_date, c_stack) "
                + "SELECT c_id, c_loc, c_img, c_name, c_type, c_size, c_brand, c_tag, c_memo, c_date, c_stack "
                + "FROM Main_Closet_backup;");

        // 백업 테이블 삭제
        db.execSQL("DROP TABLE IF EXISTS Closet_Location_backup");
        db.execSQL("DROP TABLE IF EXISTS Coordy_Location_backup");
        db.execSQL("DROP TABLE IF EXISTS Coordy_backup");
        db.execSQL("DROP TABLE IF EXISTS Main_Closet_backup");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        // db 파일이 열릴 때 마다 외래 키(FK) 사용 활성화
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
