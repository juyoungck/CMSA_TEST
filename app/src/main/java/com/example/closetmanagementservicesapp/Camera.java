package com.example.closetmanagementservicesapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.OutputStream;
import java.util.List;

public class Camera extends AppCompatActivity {
    private String imageFilePath;
    ImageView iv1;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_after_remove);

        iv1 = findViewById(R.id.iv);
        btn1 = findViewById(R.id.btn_capture);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraPermission();
            }
        });
    }

    private void requestCameraPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                // 권한 거부 시 실행할 코드
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("카메라 권한이 필요합니다. [설정] > [권한]에서 권한을 허용해주세요.")
                .setPermissions(Manifest.permission.CAMERA)
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getParcelableExtra("data");

            // 저장할 위치와 파일 이름 설정
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "my_image_" + System.currentTimeMillis() + ".jpg");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

            // Pictures/Clothes 디렉토리에 저장
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Clothes");

            // ContentResolver를 통해 이미지 저장
            ContentResolver resolver = getContentResolver();
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            try {
                if (imageUri != null) {
                    // 이미지 저장
                    OutputStream outputStream = resolver.openOutputStream(imageUri);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    if (outputStream != null) {
                        outputStream.close();
                    }

                    // 저장된 이미지 URI로 ImageView에 이미지 설정
                    iv1.setImageURI(imageUri);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
