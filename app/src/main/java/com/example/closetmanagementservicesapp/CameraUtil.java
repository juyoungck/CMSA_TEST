package com.example.closetmanagementservicesapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraUtil {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    private ImageButton imageButton;

    private ImageView imageView;
    private String savedImagePath = "";

    public CameraUtil(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    public CameraUtil(Context context, ImageButton imageButton) {
        this.context = context;
        this.imageButton = imageButton;
    }

    public void openCameraForResult(int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((AppCompatActivity) context).startActivityForResult(intent, requestCode);
    }

    public void handleCameraResult(int requestCode, int resultCode, @Nullable Intent data) {
        // DB OPEN
        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();

        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getParcelableExtra("data");

            if (bitmap != null) {
                Cursor cursor = db.rawQuery("SELECT MAX(c_id) FROM Main_Closet", null);
                int cId = 0;

                if (cursor != null && cursor.moveToFirst()) {
                    cId = cursor.getInt(0);
                    cId++;
                    cursor.close();
                }

                String fileName = "image_" + cId + ".png";

                // 이미지 저장
                savedImagePath = saveImageInternalStorage(bitmap, fileName);

                // 저장된 이미지 불러오기
                loadImageFromStorage(savedImagePath);

                Intent intent = new Intent(context, Post.class);
                intent.putExtra("fileName", fileName);
                context.startActivity(intent);
            }
        }
    }

    private String saveImageInternalStorage(Bitmap bitmap, String fileName) {
        File directory = new File(context.getFilesDir(), "images");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(context, "이미지가 내부 저장소에 저장되었습니다.", Toast.LENGTH_SHORT).show();
            Log.d("File Save Path", file.getAbsolutePath());

            // 저장된 파일의 경로 반환
            return file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "이미지 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void loadImageFromStorage(String path) {
        if (path != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, "이미지를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}