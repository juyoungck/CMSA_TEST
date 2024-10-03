package com.example.closetmanagementservicesapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Post extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private List<Integer> c_loc_value;
    private ImageLoader_Closet imageLoader;
    private CameraUtil_Closet cameraUtil;
    private static final int CAMERA_REQUEST_CODE = 1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        // DB OPEN
        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();

        ImageButton c_img_post = (ImageButton) findViewById(R.id.c_img_post);

        c_img_post.setOnClickListener(v -> showImageOptionsDialog());

        // 닫기 버튼
        CloseButton();

        fillSpinner_location();                                             // 옷장 위치 값 호출

        weatherSelect(); // 태그(계절) 함수 호출

        EditText c_name_post = (EditText) findViewById(R.id.c_name_post);   // 옷 이름 호출

        EditText c_brand_post = (EditText) findViewById(R.id.c_brand_post); // 옷 브랜드 호출

        Spinner c_type_spinner = (Spinner) findViewById(R.id.c_type_post);  // 옷 종류 호출
        ArrayAdapter<CharSequence> c_type_adapter = ArrayAdapter.createFromResource(this, R.array.c_type_array, android.R.layout.simple_spinner_item);
        c_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_type_spinner.setAdapter(c_type_adapter);
        EditText c_type_post_add = (EditText) findViewById(R.id.c_type_post_add);   // 옷 종류(직접입력) 호출

        //카메라
        cameraUtil = new CameraUtil_Closet(this, c_img_post); //화면, 이미지뷰
        imageLoader = new ImageLoader_Closet(this, c_img_post);
        Intent intent = getIntent();
        String fileName = intent.getStringExtra("fileName");
        Boolean fileUpload = intent.getBooleanExtra("fileUpload", false);
        if (fileUpload) {
            String imagePath = "/data/user/0/com.example.closetmanagementservicesapp/files/images/" + fileName;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            c_img_post.setImageBitmap(bitmap);
        }

        c_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String c_type = c_type_spinner.getSelectedItem().toString();

                if (c_type.equals("직접입력")) {
                    c_type_post_add.setVisibility(View.VISIBLE);
                } else {
                    c_type_post_add.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner c_size_spinner = (Spinner) findViewById(R.id.c_size_post);  // 옷 사이즈 호출
        ArrayAdapter<CharSequence> c_size_adapter = ArrayAdapter.createFromResource(this, R.array.c_size_array, android.R.layout.simple_spinner_item);
        c_size_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_size_spinner.setAdapter(c_size_adapter);
        EditText c_size_post_add = (EditText) findViewById(R.id.c_size_post_add);   // 옷 사이즈((직접입력) 호출

        c_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String c_size = c_size_spinner.getSelectedItem().toString();

                if (c_size.equals("직접입력")) {
                    c_size_post_add.setVisibility(View.VISIBLE);
                } else if (c_size.equals("선택안함")) {
                    c_size_post_add.setVisibility(View.INVISIBLE);
                    c_size_post_add.setText("");
                } else {
                    c_size_post_add.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        EditText c_memo_post = (EditText) findViewById(R.id.c_memo_post);   // 메모 호출

        Button save = (Button) findViewById(R.id.cloth_post);               // 등록 버튼

        // 등록 버튼을 누르면 실행되는 함수
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String fileName = intent.getStringExtra("fileName");


                Cursor cursor = db.rawQuery("SELECT MAX(c_id) FROM Main_Closet", null);
                int cId = 0;

                if (cursor != null && cursor.moveToFirst()) {
                    cId = cursor.getInt(0);
                    cId++;
                    cursor.close();
                }

                String fileNameCheck = "image_" + cId + ".png";

                int selectedLocIndex = ((Spinner) findViewById(R.id.c_loc_post)).getSelectedItemPosition();
                Integer c_loc = c_loc_value.get(selectedLocIndex);
                String c_name = c_name_post.getText().toString();
                String c_type = c_type_spinner.getSelectedItem().toString();
                String c_type_add = c_type_post_add.getText().toString();
                String c_size = c_size_spinner.getSelectedItem().toString();
                String c_size_add = c_size_post_add.getText().toString();
                String c_brand = c_brand_post.getText().toString();
                String c_memo = c_memo_post.getText().toString();

                if (!fileNameCheck.equals(fileName)) {
                    Toast.makeText(getApplicationContext(), "사진 파일이 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else if (c_name.equals("")) {
                    Toast.makeText(getApplicationContext(), "옷 이름이 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else if (c_type.equals("직접입력") && c_type_add.equals("")) {
                    Toast.makeText(getApplicationContext(), "옷 종류가 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Post.this);
                    builder.setTitle("옷 등록")        // 제목 설정
                            .setMessage("해당 설정으로 옷을 등록하시겠습니까?")        // 메세지 설정
                            .setCancelable(false)                               // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    db.beginTransaction();
                                    try {
                                        ContentValues values = new ContentValues();

                                        values.put("c_img", "/data/user/0/com.example.closetmanagementservicesapp/files/images/" + fileName);
                                        values.put("c_loc", c_loc);
                                        values.put("c_name", c_name);

                                        if (c_type.equals("직접입력")) {
                                            values.put("c_type", c_type_add);
                                        } else {
                                            values.put("c_type", c_type);
                                        }

                                        if (c_size.equals("직접입력")) {
                                            values.put("c_size", c_size_add);
                                        } else if (c_size.equals("선택안함")) {
                                            values.put("c_size", "");
                                        } else {
                                            values.put("c_size", c_size);
                                        }

                                        values.put("c_brand", c_brand);
                                        values.put("c_tag", getTag());
                                        values.put("c_memo", c_memo);
                                        values.put("c_date", getToday());
                                        values.put("c_stack", 0);

                                        db.insert("Main_Closet", null, values);
                                        db.setTransactionSuccessful();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        db.endTransaction();
                                    }

                                    Toast.makeText(getApplicationContext(), "옷 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Post.this, MainActivity.class);
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
                            imageLoader.selectImage();
                        }
                    }
                });
        builder.show();
    }

    private void fillSpinner_location() {
        Spinner c_loc = findViewById(R.id.c_loc_post);

        List<String> locations = new ArrayList<>();
        c_loc_value = new ArrayList<>();    // c_loc 값을 저장할 리스트 초기화
        Cursor cursor = db.rawQuery("SELECT c_loc, c_loc_name FROM Closet_Location ORDER BY c_loc ASC", null);

        while (cursor.moveToNext()) {
            locations.add(cursor.getString(cursor.getColumnIndex("c_loc_name")));
            c_loc_value.add(cursor.getInt(cursor.getColumnIndex("c_loc")));
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_loc.setAdapter(adapter);
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

    private void CloseButton() {
        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack_post);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Post.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //카메라 권한
    private void requestCameraPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                cameraUtil.openCameraForResult(CAMERA_REQUEST_CODE);

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(Post.this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
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
            System.out.println("되는듯");
            cameraUtil.handleCameraResult(requestCode, resultCode, data);
        }
        if(requestCode==2) {
            imageLoader.loadImageFromResult(requestCode, resultCode, data);
        }
    }
}
