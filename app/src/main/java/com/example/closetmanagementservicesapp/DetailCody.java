package com.example.closetmanagementservicesapp;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;

public class DetailCody extends AppCompatActivity {

    // Button을 위한 배열 선언
    private ImageButton thumbnailImageButton;
    private Button[] detailButtons = new Button[8];
    private int[] cIdValues = new int[8];
    private Bitmap thumbnailBitmap; // 썸네일 비트맵
    private Bitmap[] bitmaps = new Bitmap[8]; // 각 Button에 할당할 비트맵 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cody_detail);

        // 썸네일 ImageButton 초기화
        thumbnailImageButton = findViewById(R.id.detail_c_img);

        // 8개의 Button 초기화
        detailButtons[0] = findViewById(R.id.detail_cod_top); // 상의 버튼
        detailButtons[1] = findViewById(R.id.detail_cod_bottom); // 하의 버튼
        detailButtons[2] = findViewById(R.id.detail_cod_shoes); // 신발 버튼
        detailButtons[3] = findViewById(R.id.detail_cod_outer); // 외투 버튼
        detailButtons[4] = findViewById(R.id.detail_cod_underwear); // 속옷 버튼
        detailButtons[5] = findViewById(R.id.detail_cod_socks); // 양말 버튼
        detailButtons[6] = findViewById(R.id.detail_cod_accessory); // 액세서리 버튼
        detailButtons[7] = findViewById(R.id.detail_cod_bag); // 가방 버튼

        // Intent에서 전달받은 데이터 가져오기
        Intent intent = getIntent();
        if (intent != null) {
            int cod_id = intent.getIntExtra("cod_id", -1);
            if (cod_id != -1) {
                // 데이터베이스 초기화
                DBHelper dbHelper = DBHelper.getInstance(this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                // Coordy 테이블에서 cod_id에 해당하는 데이터 조회
                Cursor cursor = db.query("Coordy", null, "cod_id = ?", new String[]{String.valueOf(cod_id)}, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    // cod_img 가져오기
                    String cod_img = cursor.getString(cursor.getColumnIndexOrThrow("cod_img"));
                    if (cod_img != null) {
                        thumbnailBitmap = BitmapFactory.decodeFile(cod_img);
                        thumbnailImageButton.setImageBitmap(thumbnailBitmap);
                    }

                    // cod_index1 ~ cod_index8 가져오기
                    for (int i = 0; i < bitmaps.length; i++) {
                        String codIndexColumn = "cod_index" + (i + 1);
                        int c_id = cursor.getInt(cursor.getColumnIndexOrThrow(codIndexColumn));

                        cIdValues[i] = c_id;

                        if (c_id != 0) {
                            // Main_Closet 테이블에서 c_id에 해당하는 c_img 조회
                            Cursor cCursor = db.query("Main_Closet", null, "c_id = ?", new String[]{String.valueOf(c_id)}, null, null, null);
                            if (cCursor != null && cCursor.moveToFirst()) {
                                String c_img = cCursor.getString(cCursor.getColumnIndexOrThrow("c_img"));
                                if (c_img != null) {
                                    bitmaps[i] = BitmapFactory.decodeFile(c_img);
                                }
                                cCursor.close();
                            }
                        }
                    }

                    cursor.close();
                }


                // 각 버튼에 이미지 설정
                for (int i = 0; i < detailButtons.length; i++) {
                    if (bitmaps[i] != null) {
                        Drawable drawable = new BitmapDrawable(getResources(), bitmaps[i]);
                        detailButtons[i].setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);

                        final int index = i;

                        detailButtons[i].setOnClickListener(view -> {
                            int c_id = cIdValues[index];

                            if (c_id != 0) {
                                new Thread(() -> {
                                    // Main_Closet 테이블에서 c_id로 데이터 조회
                                    Cursor detailCursor = db.query("Main_Closet", null, "c_id = ?", new String[]{String.valueOf(c_id)}, null, null, null);

                                    if (detailCursor != null && detailCursor.moveToFirst()) {
                                        // 데이터 가져오기
                                        int c_id_value = detailCursor.getInt(detailCursor.getColumnIndexOrThrow("c_id"));
                                        String c_img = detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_img"));
                                        int c_loc = detailCursor.getInt(detailCursor.getColumnIndexOrThrow("c_loc"));
                                        String c_name = detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_name"));
                                        String c_type = detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_type"));
                                        String c_size = detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_size"));
                                        String c_brand = detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_brand"));
                                        int c_tag = detailCursor.getInt(detailCursor.getColumnIndexOrThrow("c_tag"));
                                        String c_memo = detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_memo"));
                                        String c_date = detailCursor.getString(detailCursor.getColumnIndexOrThrow("c_date"));
                                        int c_stack = detailCursor.getInt(detailCursor.getColumnIndexOrThrow("c_stack"));

                                        detailCursor.close(); // 커서 닫기

                                        // Intent 생성 및 데이터 추가
                                        Intent detailIntent = new Intent(DetailCody.this, DetailActivity.class);
                                        detailIntent.putExtra("c_id", c_id_value);
                                        detailIntent.putExtra("c_img", c_img);
                                        detailIntent.putExtra("c_loc", c_loc);
                                        detailIntent.putExtra("c_name", c_name);
                                        detailIntent.putExtra("c_type", c_type);
                                        detailIntent.putExtra("c_size", c_size);
                                        detailIntent.putExtra("c_brand", c_brand);
                                        detailIntent.putExtra("c_tag", c_tag);
                                        detailIntent.putExtra("c_memo", c_memo);
                                        detailIntent.putExtra("c_date", c_date);
                                        detailIntent.putExtra("c_stack", c_stack);

                                        // UI 스레드에서 인텐트 실행
                                        runOnUiThread(() -> {
                                            startActivity(detailIntent);
                                        });
                                    } else if (detailCursor != null) {
                                        detailCursor.close(); // 커서 닫기
                                    }
                                }).start();
                            }
                        });
                    }
                }
            }
        }

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack_detail);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailCody.this, Cody.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
