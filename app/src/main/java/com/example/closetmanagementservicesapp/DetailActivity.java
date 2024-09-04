package com.example.closetmanagementservicesapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

// 이미지 버튼을 클릭했을 때 실행되는 java 코드
public class DetailActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private List<Integer> c_loc_value;
    private int c_id;
    private boolean isModified = false;
    private ImageLoader_Modify imageLoader_modify;
    private CameraUtil_Modify cameraUtil_modify;
    private static final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // DB OPEN
        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();

        ImageButton detail_c_img = (ImageButton) findViewById(R.id.detail_c_img);
        Spinner detail_c_loc = (Spinner) findViewById(R.id.detail_c_loc);
        EditText detail_c_name = (EditText) findViewById(R.id.detail_c_name);
        EditText detail_c_brand = (EditText) findViewById(R.id.detail_c_brand);
        Spinner detail_c_type = (Spinner) findViewById(R.id.detail_c_type);
        EditText detail_c_type_add = (EditText) findViewById(R.id.detail_c_type_add);
        Spinner detail_c_size = (Spinner) findViewById(R.id.detail_c_size);
        EditText detail_c_size_add = (EditText) findViewById(R.id.detail_c_size_add);
        CheckBox weatherSelectspring= findViewById(R.id.weatherSelect_spring);
        CheckBox weatherSelectsummer= findViewById(R.id.weatherSelect_summer);
        CheckBox weatherSelectfall= findViewById(R.id.weatherSelect_fall);
        CheckBox weatherSelectwinter= findViewById(R.id.weatherSelect_winter);
        CheckBox weatherSelectcommunal= findViewById(R.id.weatherSelect_communal);
        EditText detail_c_memo = (EditText) findViewById(R.id.detail_c_memo);
        EditText detail_c_date = (EditText) findViewById(R.id.detail_c_date);
        EditText detail_c_stack = (EditText) findViewById(R.id.detail_c_stack);
        Button detailModifyButton = findViewById(R.id.detail_Modify);
        Button deleteButton = findViewById(R.id.detail_delete);

        detail_c_img.setOnClickListener(v -> showImageOptionsDialog());
        detail_c_img.setClickable(false);
        detail_c_loc.setEnabled(false);
        detail_c_loc.setClickable(false);
        detail_c_type.setEnabled(false);
        detail_c_type.setClickable(false);
        detail_c_size.setEnabled(false);
        detail_c_size.setClickable(false);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Iterator<String> iter = b.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            Object value = b.get(key);
            Log.d("TAG", "key : "+key+", value : " + value.toString());
        }

        c_id = intent.getIntExtra("c_id", -1);
        String c_img = intent.getStringExtra("c_img");
        int c_loc = intent.getIntExtra("c_loc", 1);
        String c_name = intent.getStringExtra("c_name");
        String c_type = intent.getStringExtra("c_type");
        String c_size = intent.getStringExtra("c_size");
        String c_brand = intent.getStringExtra("c_brand");
        int c_tag = intent.getIntExtra("c_tag", 1);
        String c_memo = intent.getStringExtra("c_memo");
        String c_date = intent.getStringExtra("c_date");
        int c_stack = intent.getIntExtra("c_stack", 0);

        Bitmap bitmap = BitmapFactory.decodeFile(c_img);
        detail_c_img.setImageBitmap(bitmap);
        detail_c_name.setText(c_name);
        detail_c_brand.setText(c_brand);
        detail_c_memo.setText(c_memo);
        detail_c_date.setText(c_date);
        detail_c_stack.setText(String.valueOf(c_stack));

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack_detail);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fillSpinner_location(c_loc);
        fillSpinner_type(c_type);
        fillSpinner_size(c_size);

        cameraUtil_modify = new CameraUtil_Modify(this, detail_c_img, c_id); //화면, 이미지뷰
        imageLoader_modify = new ImageLoader_Modify(this, detail_c_img, c_id);

        detailModifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isModified) {
                    detailModifyButton.setText("저장");
                    isModified = true;
                    detail_c_img.setClickable(true);
                    detail_c_loc.setEnabled(true);
                    detail_c_loc.setClickable(true);
                    detail_c_name.setEnabled(true);
                    detail_c_brand.setEnabled(true);
                    detail_c_type.setEnabled(true);
                    detail_c_type.setClickable(true);
                    detail_c_type_add.setEnabled(true);
                    detail_c_size.setEnabled(true);
                    detail_c_size.setClickable(true);
                    detail_c_size_add.setEnabled(true);
                    weatherSelectspring.setEnabled(true);
                    weatherSelectsummer.setEnabled(true);
                    weatherSelectfall.setEnabled(true);
                    weatherSelectwinter.setEnabled(true);
                    weatherSelectcommunal.setEnabled(true);
                    detail_c_memo.setEnabled(true);
                } else {
                    if (detail_c_name.equals("")) {
                        Toast.makeText(getApplicationContext(), "옷 이름이 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    } else if (detail_c_type.equals("직접입력") /*&& detail_c_type_add.equals("")*/) {
                        Toast.makeText(getApplicationContext(), "옷 종류가 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 두 번째 클릭: 다이얼로그 표시
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                        builder.setMessage("옷을 저장하겠습니까?")
                                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        int selectedLocIndex = ((Spinner) findViewById(R.id.detail_c_loc)).getSelectedItemPosition();
                                        Integer c_loc = c_loc_value.get(selectedLocIndex);
                                        String c_name = detail_c_name.getText().toString();
                                        String c_type = detail_c_type.getSelectedItem().toString();
                                        String c_type_add = detail_c_type_add.getText().toString();
                                        String c_size = detail_c_size.getSelectedItem().toString();
                                        String c_size_add = detail_c_size_add.getText().toString();
                                        String c_brand = detail_c_brand.getText().toString();
                                        String c_memo = detail_c_memo.getText().toString();

                                        db.beginTransaction();
                                        try {
                                            ContentValues values = new ContentValues();
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
                                            values.put("c_tag", 1);
                                            values.put("c_memo", c_memo);
                                            db.update("Main_Closet", values, "c_id = ?", new String[]{String.valueOf(c_id)});

                                            db.setTransactionSuccessful();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            db.endTransaction();
                                        }

                                        Toast.makeText(getApplicationContext(), "옷 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
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
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setMessage("옷을 삭제하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.beginTransaction();
                                try {
                                    db.delete("Main_Closet", "c_id = ?", new String[]{String.valueOf(c_id)});

                                    db.setTransactionSuccessful();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.endTransaction();
                                }

                                Toast.makeText(getApplicationContext(), "옷 삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
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
    }

    private void fillSpinner_location(int selected_loc) {
        Spinner c_loc = findViewById(R.id.detail_c_loc);

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

        int position = c_loc_value.indexOf(selected_loc);

        if (position >= 0) {
            c_loc.setSelection(position);
        }
    }

    private void fillSpinner_type(String selected_type) {
        Spinner detail_c_type_spinner = (Spinner) findViewById(R.id.detail_c_type);  // 옷 종류 호출
        EditText detail_c_type_add = (EditText) findViewById(R.id.detail_c_type_add);   // 옷 종류(직접입력) 호출

        List<String> c_type_array = Arrays.asList(getResources().getStringArray(R.array.c_type_array));

        if (!c_type_array.contains(selected_type)) {
            detail_c_type_add.setText(selected_type);
            selected_type = "직접입력";
        }

        ArrayAdapter<CharSequence> detail_c_type_adapter = ArrayAdapter.createFromResource(this, R.array.c_type_array, android.R.layout.simple_spinner_item);
        detail_c_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detail_c_type_spinner.setAdapter(detail_c_type_adapter);

        int position = detail_c_type_adapter.getPosition(selected_type);

        if (position >= 0) {
            detail_c_type_spinner.setSelection(position);

            if (position == 12) {
                detail_c_type_add.setVisibility(View.VISIBLE);
            } else {
                detail_c_type_add.setVisibility(View.INVISIBLE);
            }
        }

        detail_c_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String c_type = detail_c_type_spinner.getSelectedItem().toString();

                if (c_type.equals("직접입력")) {
                    detail_c_type_add.setVisibility(View.VISIBLE);
                } else {
                    detail_c_type_add.setVisibility(View.INVISIBLE);
                    detail_c_type_add.setText(""); // 다른 항목이 선택되면 EditText 초기화
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void fillSpinner_size(String selected_size) {
        Spinner detail_c_size_spinner = (Spinner) findViewById(R.id.detail_c_size);  // 옷 사이즈 호출
        EditText detail_c_size_add = (EditText) findViewById(R.id.detail_c_size_add);   // 옷 사이즈((직접입력) 호출

        List<String> c_size_array = Arrays.asList(getResources().getStringArray(R.array.c_size_array));

        if (!c_size_array.contains(selected_size)) {
            if (selected_size.equals("")) {
                selected_size = "선택안함";
            } else {
                detail_c_size_add.setText(selected_size);
                selected_size = "직접입력";
            }
        }

        ArrayAdapter<CharSequence> detail_c_size_adapter = ArrayAdapter.createFromResource(this, R.array.c_size_array, android.R.layout.simple_spinner_item);
        detail_c_size_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detail_c_size_spinner.setAdapter(detail_c_size_adapter);

        int position = detail_c_size_adapter.getPosition(selected_size);

        if (position >= 0) {
            detail_c_size_spinner.setSelection(position);

            if (position == 8) {
                detail_c_size_add.setVisibility(View.VISIBLE);
            } else {
                detail_c_size_add.setVisibility(View.INVISIBLE);
            }
        }

        detail_c_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String c_size = detail_c_size_spinner.getSelectedItem().toString();

                if (c_size.equals("직접입력")) {
                    detail_c_size_add.setVisibility(View.VISIBLE);
                } else {
                    detail_c_size_add.setVisibility(View.INVISIBLE);
                    detail_c_size_add.setText(""); // 다른 항목이 선택되면 EditText 초기화
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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
                Toast.makeText(DetailActivity.this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
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
