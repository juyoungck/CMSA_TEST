package com.example.closetmanagementservicesapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.List;

public class DetailCody extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private ImageLoader_Modify imageLoader_modify;
    private CameraUtil_Modify cameraUtil_modify;
    private static final int CAMERA_REQUEST_CODE = 1;
    private Button[] detailButtons = new Button[8];
    private int[] cIdValues = new int[8];
    private Bitmap thumbnailBitmap;
    private Bitmap[] bitmaps = new Bitmap[8];
    private int selectedButton = -1;
    private int cod_id; // cod_id를 클래스 변수로 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cody_detail);

        // DB OPEN
        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();

        // 수정 체크박스 초기화
        CheckBox detail_Modify = (CheckBox) findViewById(R.id.detail_Modify);
        Button detail_Delete = (Button) findViewById(R.id.detail_Delete);

        // 썸네일 ImageButton 초기화
        ImageButton detail_cod_img = (ImageButton) findViewById(R.id.detail_cod_img);

        // 8개의 Button 초기화
        detailButtons[0] = findViewById(R.id.detail_cod_top);
        detailButtons[1] = findViewById(R.id.detail_cod_bottom);
        detailButtons[2] = findViewById(R.id.detail_cod_shoes);
        detailButtons[3] = findViewById(R.id.detail_cod_outer);
        detailButtons[4] = findViewById(R.id.detail_cod_underwear);
        detailButtons[5] = findViewById(R.id.detail_cod_socks);
        detailButtons[6] = findViewById(R.id.detail_cod_accessory);
        detailButtons[7] = findViewById(R.id.detail_cod_bag);

        cameraUtil_modify = new CameraUtil_Modify(this, detail_cod_img, cod_id); //화면, 이미지뷰
        imageLoader_modify = new ImageLoader_Modify(this, detail_cod_img, cod_id);

        // Intent에서 전달받은 데이터 가져오기
        Intent intent = getIntent();
        if (intent != null) {
            cod_id = intent.getIntExtra("cod_id", -1);
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
                        detail_cod_img.setImageBitmap(thumbnailBitmap);
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

                db.close(); // 사용 후 데이터베이스 닫기

                // 각 버튼에 이미지 설정 및 클릭 리스너 추가
                for (int i = 0; i < detailButtons.length; i++) {
                    if (bitmaps[i] != null) {
                        Drawable drawable = new BitmapDrawable(getResources(), bitmaps[i]);
                        detailButtons[i].setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                    }

                    final int index = i;

                    detailButtons[i].setOnClickListener(view -> {
                        if (detail_Modify.isChecked()) {
                            // 수정 체크박스가 체크되어 있으면 다이얼로그 표시
                            selectedButton = index;
                            showImageSelectionDialog();
                        } else {
                            // 기존 동작 수행
                            int c_id = cIdValues[index];

                            if (c_id != 0) {
                                new Thread(() -> {
                                    // Main_Closet 테이블에서 c_id로 데이터 조회
                                    DBHelper dbHelper1 = DBHelper.getInstance(this);
                                    SQLiteDatabase db1 = dbHelper1.getReadableDatabase();
                                    Cursor detailCursor = db1.query("Main_Closet", null, "c_id = ?", new String[]{String.valueOf(c_id)}, null, null, null);

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
                                        db1.close(); // 데이터베이스 닫기

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
                                        db1.close(); // 데이터베이스 닫기
                                    }
                                }).start();
                            }
                        }
                    });
                }
            }
        }

        // 수정 체크박스의 상태 변화에 따른 동작 설정
        detail_Modify.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                detail_Modify.setText("저장");
                detail_Modify.setBackgroundColor(Color.parseColor("#ff6e6e"));
                detail_cod_img.setOnClickListener(v -> showImageOptionsDialog());

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailCody.this);
                builder.setMessage("코디를 저장하겠습니까?")
                        .setPositiveButton("예", (dialog, id) -> {
                            // 데이터베이스 업데이트 로직 추가
                            saveCodyData();

                            Toast.makeText(getApplicationContext(), "코디 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                            // Intent 생성 및 데이터 전달
                            Intent modifyIntent = new Intent(DetailCody.this, Cody.class);
                            modifyIntent.putExtra("cod_id", cod_id); // 필요 시 cod_id 전달
                            startActivity(modifyIntent);
                            finish();
                        })
                        .setNegativeButton("아니오", (dialog, id) -> {
                            dialog.dismiss();
                        });
                builder.create().show();
            }
        });

        detail_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailCody.this);
                builder.setMessage("옷을 삭제하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.beginTransaction();
                                try {
                                    db.delete("Coordy", "cod_id = ?", new String[]{String.valueOf(cod_id)});

                                    db.setTransactionSuccessful();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.endTransaction();
                                }

                                Toast.makeText(getApplicationContext(), "옷 삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DetailCody.this, Cody.class);
                                startActivity(intent);
                                finish();
                            }

                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack_detail);
        btnBack.setOnClickListener(view -> {
            Intent backIntent = new Intent(DetailCody.this, Cody.class);
            startActivity(backIntent);
            finish();
        });
    }

    // 이미지 선택 다이얼로그를 표시하는 메서드
    private void showImageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("이미지 옵션 선택")
                .setItems(new CharSequence[]{"이미지 제거", "이미지 변경"}, (dialog, which) -> {
                    if (which == 0) {
                        Drawable defaultDrawable = getResources().getDrawable(R.drawable.baseline_add_box_24);
                        detailButtons[selectedButton].setCompoundDrawablesWithIntrinsicBounds(null, defaultDrawable, null, null);
                        Toast.makeText(DetailCody.this, "옷이 제거되었습니다.", Toast.LENGTH_SHORT).show();
                    } else if (which == 1) {
                        Dialog selectDialog = new Dialog(DetailCody.this);
                        View addView = LayoutInflater.from(DetailCody.this).inflate(R.layout.cody_addmenu, null);

                        selectDialog.setContentView(addView);
                        selectDialog.show();

                        WindowManager.LayoutParams params = selectDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = 1200;
                        selectDialog.getWindow().setAttributes(params);

                        GridLayout gridLayout = addView.findViewById(R.id.gl_add);

                        SQLiteDatabase db = DBHelper.getInstance(DetailCody.this).getReadableDatabase();

                        Cursor cursor = db.query("Main_Closet", null, null, null, null, null, null);

                        if (cursor != null && cursor.moveToFirst()) {
                            int columnCount = 3; // Number of columns in GridLayout
                            gridLayout.setColumnCount(columnCount);

                            do {
                                int c_id = cursor.getInt(cursor.getColumnIndexOrThrow("c_id"));
                                String c_img = cursor.getString(cursor.getColumnIndexOrThrow("c_img"));
                                String c_name = cursor.getString(cursor.getColumnIndexOrThrow("c_name"));

                                Bitmap bitmap = BitmapFactory.decodeFile(c_img);

                                // Create a LinearLayout to hold ImageButton and TextView
                                LinearLayout itemLayout = new LinearLayout(DetailCody.this);
                                itemLayout.setOrientation(LinearLayout.VERTICAL);

                                ImageButton imageButton = new ImageButton(DetailCody.this);
                                imageButton.setImageBitmap(bitmap);
                                imageButton.setTag(c_id);
                                imageButton.setBackgroundColor(Color.TRANSPARENT);

                                TextView textView = new TextView(DetailCody.this);
                                textView.setText(c_name);
                                textView.setGravity(Gravity.CENTER);

                                itemLayout.addView(imageButton);
                                itemLayout.addView(textView);

                                // Set LayoutParams for itemLayout
                                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                                layoutParams.width = 0;
                                layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
                                layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                                layoutParams.setMargins(5, 5, 5, 5);

                                gridLayout.addView(itemLayout, layoutParams);

                                // Set OnClickListener for imageButton
                                imageButton.setOnClickListener(v -> {
                                    AlertDialog.Builder Codybuilder = new AlertDialog.Builder(DetailCody.this);
                                    Codybuilder.setTitle("옷 등록")
                                            .setMessage("해당 옷으로 등록하시겠습니까?")
                                            .setCancelable(false)
                                            .setPositiveButton("확인", (dialogInterface, whichButton) -> {
                                                Bitmap selectedBitmap = BitmapFactory.decodeFile(c_img);
                                                if (selectedBitmap != null && selectedButton != -1) {
                                                    Drawable selectedDrawable = new BitmapDrawable(getResources(), selectedBitmap);
                                                    detailButtons[selectedButton].setCompoundDrawablesWithIntrinsicBounds(null, selectedDrawable, null, null);
                                                    cIdValues[selectedButton] = c_id;
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "이미지를 설정할 수 없습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                                Toast.makeText(getApplicationContext(), "옷 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                dialogInterface.dismiss();
                                                selectDialog.dismiss();
                                            })
                                            .setNegativeButton("취소", (dialogInterface, whichButton) -> {
                                                // Do nothing
                                            });
                                    Codybuilder.show();
                                });
                            } while (cursor.moveToNext());
                            cursor.close();
                        }
                    }
                })
                .setNegativeButton("취소", (dialog, which) -> {
                    dialog.dismiss();
                });
        builder.show();
    }

    private void saveCodyData() {
        // 현재 코디의 cod_id를 가져옵니다.
        if (db == null || !db.isOpen()) {
            // dbHelper를 통해 다시 데이터베이스를 엽니다.
            db = dbHelper.getWritableDatabase();
        }

        Intent intent = getIntent();
        int cod_id = intent.getIntExtra("cod_id", -1);
        if (cod_id != -1) {
            // 데이터베이스 업데이트 시작
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                // cod_index1 ~ cod_index8 업데이트
                for (int i = 0; i < cIdValues.length; i++) {
                    String codIndexColumn = "cod_index" + (i + 1);
                    values.put(codIndexColumn, cIdValues[i]);
                }
                // Coordy 테이블 업데이트
                db.update("Coordy", values, "cod_id = ?", new String[]{String.valueOf(cod_id)});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "데이터베이스 업데이트 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            } finally {
                db.endTransaction();
            }
        } else {
            Toast.makeText(this, "유효하지 않은 코디 ID입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showImageOptionsDialog() {
        String[] options = {"촬영", "파일에서 가져오기"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("옵션 선택")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            requestCameraPermission();

                        } else if (which == 1) {
                            imageLoader_modify.selectImage();
                        }
                    }
                });
        builder.show();
    }

    private void requestCameraPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                cameraUtil_modify.openCameraForResult(CAMERA_REQUEST_CODE);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(DetailCody.this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("카메라 권한이 필요합니다. [설정] > [권한]에서 권한을 허용해주세요.")
                .setPermissions(android.Manifest.permission.CAMERA)
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1) {
            cameraUtil_modify.handleCameraResult(requestCode, resultCode, data);
        }
        if(requestCode==2) {
            imageLoader_modify.loadImageFromResult(requestCode, resultCode, data);
        }
    }
}

