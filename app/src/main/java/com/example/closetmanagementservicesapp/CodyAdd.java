package com.example.closetmanagementservicesapp;

        import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

        import android.annotation.SuppressLint;
        import android.app.ActivityManager;
        import android.app.AlertDialog;
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
        import android.graphics.drawable.VectorDrawable;
        import android.net.Uri;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.Spinner;
        import android.widget.Toast;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import com.gun0912.tedpermission.PermissionListener;
        import com.gun0912.tedpermission.TedPermission;

        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;
        import java.util.TimeZone;

public class CodyAdd extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private List<Integer> cod_loc_value;
    private CameraUtil_Cody[] cameraUtils = new CameraUtil_Cody[9];
    private ImageLoader_Cody[] imageLoaders = new ImageLoader_Cody[9];
    private static final int CAMERA_REQUEST_CODE = 1;
    private static int selectedButton = -1;

    private ImageButton[] detailCodIndices;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.codyadd);

        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();

        fillSpinner_cod_location();

        EditText codyadd_title = (EditText) findViewById(R.id.codyadd_title);

        detailCodIndices = new ImageButton[]{
                findViewById(R.id.add_cod_thumb),
                findViewById(R.id.detail_cod_index1),
                findViewById(R.id.detail_cod_index2),
                findViewById(R.id.detail_cod_index3),
                findViewById(R.id.detail_cod_index4),
                findViewById(R.id.detail_cod_index5),
                findViewById(R.id.detail_cod_index6),
                findViewById(R.id.detail_cod_index7),
                findViewById(R.id.detail_cod_index8)
        };



        // 카메라 유틸리티 및 이미지 로더 초기화
        cameraUtils = new CameraUtil_Cody[9];
        imageLoaders = new ImageLoader_Cody[9];

        for (int i = 0; i < 9; i++) {
            cameraUtils[i] = new CameraUtil_Cody(this, detailCodIndices[i]);
            imageLoaders[i] = new ImageLoader_Cody(this, detailCodIndices[i]);

            final int index = i;
            detailCodIndices[i].setOnClickListener(v -> {
                selectedButton = index;
                showImageOptionsDialog(index);

            });
        }

        Intent intent = getIntent();
        byte[] byteArray = intent.getByteArrayExtra("imageData");
        if (byteArray != null && byteArray.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            if (bitmap != null && selectedButton != -1) { // selectedButton이 유효한 값일 때만 실행
                detailCodIndices[selectedButton].setImageBitmap(bitmap);
            }
        }

        for (int i = 0; i < 9; i++) {
            Drawable drawable = detailCodIndices[i].getDrawable();
            boolean hasImage = drawable instanceof BitmapDrawable && ((BitmapDrawable) drawable).getBitmap() != null;
        }


        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack_codyadd);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(CodyAdd.this, Cody.class);
                startActivity(intent);
                finish();
            }
        });

        Button save = (Button) findViewById(R.id.codyadd_post);               // 등록 버튼

        handleIncomingIntent(getIntent());

        // 등록 버튼을 누르면 실행되는 함수
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String CodyFileName = intent.getStringExtra("codyFileName");

                Cursor cursor = db.rawQuery("SELECT MAX(cod_id) FROM Coordy", null);
                int cId = 0;

                if (cursor != null && cursor.moveToFirst()) {
                    cId = cursor.getInt(0);
                    cId++;
                    cursor.close();
                }
                String fileNameCheck = cId + ".png";

                int selectedLocIndex = ((Spinner) findViewById(R.id.cody_location)).getSelectedItemPosition();
                Integer cod_loc = cod_loc_value.get(selectedLocIndex);
                String cod_name = codyadd_title.getText().toString();

                if (CodyFileName == null || CodyFileName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "사진 파일이 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else if (cod_name.equals("")) {
                    Toast.makeText(getApplicationContext(), "코디 이름이 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CodyAdd.this);
                    builder.setTitle("코디 등록")        // 제목 설정
                            .setMessage("해당 설정으로 옷을 등록하시겠습니까?")        // 메세지 설정
                            .setCancelable(false)                               // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    db.beginTransaction();
                                    try {
                                        ContentValues values = new ContentValues();

                                        ImageButton thumb = (ImageButton) findViewById(R.id.add_cod_thumb);
                                        ImageButton index1 = (ImageButton) findViewById(R.id.detail_cod_index1);
                                        ImageButton index2 = (ImageButton) findViewById(R.id.detail_cod_index2);
                                        ImageButton index3 = (ImageButton) findViewById(R.id.detail_cod_index3);
                                        ImageButton index4 = (ImageButton) findViewById(R.id.detail_cod_index4);
                                        ImageButton index5 = (ImageButton) findViewById(R.id.detail_cod_index5);
                                        ImageButton index6 = (ImageButton) findViewById(R.id.detail_cod_index6);
                                        ImageButton index7 = (ImageButton) findViewById(R.id.detail_cod_index7);
                                        ImageButton index8 = (ImageButton) findViewById(R.id.detail_cod_index8);


                                        if (thumb.getDrawable() instanceof BitmapDrawable && ((BitmapDrawable) thumb.getDrawable()).getBitmap() != null) {
                                            String modifyFileName = CodyFileName;
                                            modifyFileName = "thumb_" + CodyFileName;
                                            values.put("cod_thumbnail", "/data/user/0/com.example.closetmanagementservicesapp/files/images/" + CodyFileName); }

                                        if (index1.getDrawable() instanceof BitmapDrawable && ((BitmapDrawable) index1.getDrawable()).getBitmap() != null) {
                                            String modifyFileName = CodyFileName;
                                            modifyFileName = "index1_" + CodyFileName;
                                            values.put("cod_index1", "/data/user/0/com.example.closetmanagementservicesapp/files/images/" + CodyFileName); }

                                        if (index2.getDrawable() instanceof BitmapDrawable && ((BitmapDrawable) index2.getDrawable()).getBitmap() != null) {
                                            String modifyFileName = CodyFileName;
                                            modifyFileName = "index2_" + CodyFileName;
                                            values.put("cod_index2", "/data/user/0/com.example.closetmanagementservicesapp/files/images/" + CodyFileName); }

                                        if (index3.getDrawable() instanceof BitmapDrawable && ((BitmapDrawable) index3.getDrawable()).getBitmap() != null) {
                                            String modifyFileName = CodyFileName;
                                            modifyFileName = "index3_" + CodyFileName;
                                            values.put("cod_index3", "/data/user/0/com.example.closetmanagementservicesapp/files/images/" + CodyFileName); }

                                        if (index4.getDrawable() instanceof BitmapDrawable && ((BitmapDrawable) index4.getDrawable()).getBitmap() != null) {
                                            String modifyFileName = CodyFileName;
                                            modifyFileName = "index4_" + CodyFileName;
                                            values.put("cod_index4", "/data/user/0/com.example.closetmanagementservicesapp/files/images/" + CodyFileName); }

                                        if (index5.getDrawable() instanceof BitmapDrawable && ((BitmapDrawable) index5.getDrawable()).getBitmap() != null) {
                                            String modifyFileName = CodyFileName;
                                            modifyFileName = "index5_" + CodyFileName;
                                            values.put("cod_index5", "/data/user/0/com.example.closetmanagementservicesapp/files/images/" + CodyFileName); }

                                        if (index6.getDrawable() instanceof BitmapDrawable && ((BitmapDrawable) index6.getDrawable()).getBitmap() != null) {
                                            String modifyFileName = CodyFileName;
                                            modifyFileName = "index6_" + CodyFileName;
                                            values.put("cod_index6", "/data/user/0/com.example.closetmanagementservicesapp/files/images/" + CodyFileName); }

                                        if (index7.getDrawable() instanceof BitmapDrawable && ((BitmapDrawable) index7.getDrawable()).getBitmap() != null) {
                                            String modifyFileName = CodyFileName;
                                            modifyFileName = "index7_" + CodyFileName;
                                            values.put("cod_index7", "/data/user/0/com.example.closetmanagementservicesapp/files/images/" + CodyFileName); }

                                        if (index8.getDrawable() instanceof BitmapDrawable && ((BitmapDrawable) index8.getDrawable()).getBitmap() != null) {
                                            String modifyFileName = CodyFileName;
                                            modifyFileName = "index8_" + CodyFileName;
                                            values.put("cod_index8", "/data/user/0/com.example.closetmanagementservicesapp/files/images/" + CodyFileName); }


                                        values.put("cod_loc", cod_loc);
                                        values.put("cod_name", cod_name);
                                        values.put("cod_date", getToday());
                                        values.put("cod_tag", getTag());
                                        values.put("cod_stack", 0);

                                        db.insert("Coordy", null, values);
                                        db.setTransactionSuccessful();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        db.endTransaction();
                                    }

                                    Toast.makeText(getApplicationContext(), "옷 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CodyAdd.this, Cody.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //원하는 클릭 이벤트를 넣으시면 됩니다.
                                }
                            });

                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }
            }
        });

        weatherSelect(); // 태그(계절) 함수 호출
    }

    private String getToday() {
        DateFormat Today = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN);
        TimeZone KoreaTime = TimeZone.getTimeZone("Asia/Seoul");
        Today.setTimeZone(KoreaTime);

        Date date = new Date();

        return Today.format(date);
    }

    protected void weatherSelect() {
        // 정렬 버튼 (계절)
        CheckBox weatherSelect_spring = (CheckBox) findViewById(R.id.weatherSelect_spring);
        CheckBox weatherSelect_summer = (CheckBox) findViewById(R.id.weatherSelect_summer);
        CheckBox weatherSelect_fall = (CheckBox) findViewById(R.id.weatherSelect_fall);
        CheckBox weatherSelect_winter = (CheckBox) findViewById(R.id.weatherSelect_winter);
        CheckBox weatherSelect_communal = (CheckBox) findViewById(R.id.weatherSelect_communal);

        weatherSelect_spring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_summer.isChecked() && weatherSelect_fall.isChecked()
                            && weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if (weatherSelect_communal.isChecked()) {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(true);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(false);
                    }
                }
                weatherButtonBase();
            }
        });

        weatherSelect_summer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_spring.isChecked() && weatherSelect_fall.isChecked()
                            && weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if (weatherSelect_communal.isChecked()) {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(true);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(false);
                    }
                }
                weatherButtonBase();
            }
        });

        weatherSelect_fall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_spring.isChecked() && weatherSelect_summer.isChecked()
                            && weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if (weatherSelect_communal.isChecked()) {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(true);
                        weatherSelect_winter.setChecked(false);
                    }
                }
                weatherButtonBase();
            }
        });

        weatherSelect_winter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_spring.isChecked() && weatherSelect_summer.isChecked()
                            && weatherSelect_fall.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if (weatherSelect_communal.isChecked()) {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(true);
                    }
                }
                weatherButtonBase();
            }
        });

        // 정렬 버튼 (계절 - 전체 선택)
        weatherSelect_communal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 전체 선택 버튼이 선택된 경우
                    weatherSelect_spring.setChecked(true);
                    weatherSelect_summer.setChecked(true);
                    weatherSelect_fall.setChecked(true);
                    weatherSelect_winter.setChecked(true);

                } else {
                    if (!weatherSelect_spring.isChecked() || !weatherSelect_summer.isChecked()
                            || !weatherSelect_fall.isChecked() || !weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(false);
                    } else {
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(false);
                    }
                    weatherButtonBase();
                }
            }
        });
    }
    private void weatherButtonBase (){
        CheckBox weatherSelect_spring = (CheckBox) findViewById(R.id.weatherSelect_spring);
        CheckBox weatherSelect_summer = (CheckBox) findViewById(R.id.weatherSelect_summer);
        CheckBox weatherSelect_fall = (CheckBox) findViewById(R.id.weatherSelect_fall);
        CheckBox weatherSelect_winter = (CheckBox) findViewById(R.id.weatherSelect_winter);
        CheckBox weatherSelect_communal = (CheckBox) findViewById(R.id.weatherSelect_communal);
        if (weatherSelect_spring.isChecked()) {
            weatherSelect_spring.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_spring.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_summer.isChecked()) {
            weatherSelect_summer.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_summer.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_fall.isChecked()) {
            weatherSelect_fall.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_fall.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_winter.isChecked()) {
            weatherSelect_winter.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_winter.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_communal.isChecked()) {
            weatherSelect_communal.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_communal.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
    }

    private int getTag() {
        CheckBox Spring = (CheckBox) findViewById(R.id.weatherSelect_spring);
        CheckBox Summer = (CheckBox) findViewById(R.id.weatherSelect_summer);
        CheckBox Fall = (CheckBox) findViewById(R.id.weatherSelect_fall);
        CheckBox Winter = (CheckBox) findViewById(R.id.weatherSelect_winter);
        CheckBox Com = (CheckBox) findViewById(R.id.weatherSelect_communal);

        if (Com.isChecked()) {
            return 15;
        } else if (Spring.isChecked() && !Summer.isChecked() && !Fall.isChecked() && !Winter.isChecked()) {
            return 1;
        } else if (!Spring.isChecked() && Summer.isChecked() && !Fall.isChecked() && !Winter.isChecked()) {
            return 2;
        } else if (!Spring.isChecked() && !Summer.isChecked() && Fall.isChecked() && !Winter.isChecked()) {
            return 3;
        } else if (!Spring.isChecked() && !Summer.isChecked() && !Fall.isChecked() && Winter.isChecked()) {
            return 4;
        } else if (Spring.isChecked() && Summer.isChecked() && !Fall.isChecked() && !Winter.isChecked()) {
            return 5;
        } else if (Spring.isChecked() && !Summer.isChecked() && Fall.isChecked() && !Winter.isChecked()) {
            return 6;
        } else if (Spring.isChecked() && !Summer.isChecked() && !Fall.isChecked() && Winter.isChecked()) {
            return 7;
        } else if (!Spring.isChecked() && Summer.isChecked() && Fall.isChecked() && !Winter.isChecked()) {
            return 8;
        } else if (!Spring.isChecked() && Summer.isChecked() && !Fall.isChecked() && Winter.isChecked()) {
            return 9;
        } else if (!Spring.isChecked() && !Summer.isChecked() && Fall.isChecked() && Winter.isChecked()) {
            return 10;
        } else if (Spring.isChecked() && Summer.isChecked() && Fall.isChecked() && !Winter.isChecked()) {
            return 11;
        } else if (Spring.isChecked() && Summer.isChecked() && !Fall.isChecked() && Winter.isChecked()) {
            return 12;
        } else if (Spring.isChecked() && !Summer.isChecked() && Fall.isChecked() && Winter.isChecked()) {
            return 13;
        } else if (!Spring.isChecked() && Summer.isChecked() && Fall.isChecked() && Winter.isChecked()) {
            return 14;
        }

        return 15;
    }

    private void fillSpinner_cod_location() {
        Spinner cod_loc = findViewById(R.id.cody_location);

        List<String> locations = new ArrayList<>();
        cod_loc_value = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT cod_loc, cod_loc_name FROM Coordy_Location ORDER BY cod_loc ASC", null);

        while (cursor.moveToNext()) {
            locations.add(cursor.getString(cursor.getColumnIndex("cod_loc_name")));
            cod_loc_value.add(cursor.getInt(cursor.getColumnIndex("cod_loc")));
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cod_loc.setAdapter(adapter);
    }

    private void showImageOptionsDialog(int index) {
        String[] options = {"촬영", "파일에서 가져오기"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("옵션 선택")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            requestCameraPermission(index);

                        } else if (which == 1) {
                            imageLoaders[index].selectImage();
                        }
                    }
                });
        builder.show();
    }

    private void requestCameraPermission(int index) {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                cameraUtils[index].openCameraForResult(CAMERA_REQUEST_CODE);

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(CodyAdd.this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
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
        if (resultCode == RESULT_OK) {
            if (selectedButton >= 0) {
                if (requestCode == CAMERA_REQUEST_CODE) {
                    cameraUtils[selectedButton].handleCameraResult(requestCode, resultCode, data);
                } else if (requestCode == 2) {
                    imageLoaders[selectedButton].loadImageFromResult(requestCode, resultCode, data);
                }
            }
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 새로운 Intent 데이터 처리 (FLAG_ACTIVITY_REORDER_TO_FRONT로 인해 액티비티가 다시 호출될 때)
        setIntent(intent);
        handleIncomingIntent(intent);
    }

    private void handleIncomingIntent(Intent intent) {
        if (intent != null && intent.hasExtra("imageData")) {
            byte[] byteArray = intent.getByteArrayExtra("imageData");
            if (byteArray != null && byteArray.length > 0) {
                Bitmap newBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                if (newBitmap != null && selectedButton != -1) { // selectedButton이 유효한 값일 때만 실행
                    // 선택된 버튼에 이미지를 설정
                    detailCodIndices[selectedButton].setImageBitmap(newBitmap);
                }
            }
        }
    }


}
