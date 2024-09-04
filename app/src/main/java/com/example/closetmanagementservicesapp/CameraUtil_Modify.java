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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class CameraUtil_Modify {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    private ImageButton imageButton;
    private String savedImagePath = "";
    private int c_id;

    public CameraUtil_Modify(Context context, ImageButton imageButton, int c_id) {
        this.context = context;
        this.imageButton = imageButton;
        this.c_id = c_id;
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
                Bitmap resizedBitmap = resizeBitmap(bitmap, 300, 300);

                String fileName = "image_" + c_id + ".png";

                // 이미지 저장
                savedImagePath = saveImageInternalStorage(resizedBitmap, fileName);

                // 저장된 이미지 불러오기
                loadImageFromStorage(savedImagePath);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Cursor cursor = db.rawQuery("SELECT * FROM Main_Closet WHERE c_id = " + c_id, null);
                if (cursor != null && cursor.moveToFirst()) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("c_id", cursor.getInt(cursor.getColumnIndexOrThrow("c_id")));
                    intent.putExtra("c_img", cursor.getString(cursor.getColumnIndexOrThrow("c_img")));
                    intent.putExtra("c_loc", cursor.getInt(cursor.getColumnIndexOrThrow("c_loc")));
                    intent.putExtra("c_name", cursor.getString(cursor.getColumnIndexOrThrow("c_name")));
                    intent.putExtra("c_type", cursor.getString(cursor.getColumnIndexOrThrow("c_type")));
                    intent.putExtra("c_size", cursor.getString(cursor.getColumnIndexOrThrow("c_size")));
                    intent.putExtra("c_brand", cursor.getString(cursor.getColumnIndexOrThrow("c_brand")));
                    intent.putExtra("c_tag", cursor.getInt(cursor.getColumnIndexOrThrow("c_tag")));
                    intent.putExtra("c_memo", cursor.getString(cursor.getColumnIndexOrThrow("c_memo")));
                    intent.putExtra("c_date", cursor.getString(cursor.getColumnIndexOrThrow("c_date")));
                    intent.putExtra("c_stack", cursor.getInt(cursor.getColumnIndexOrThrow("c_stack")));
                    context.startActivity(intent);
                    cursor.close();
                }
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
            imageButton.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, "이미지를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap resizeBitmap(Bitmap originalBitmap, int width, int height) {
        return Bitmap.createScaledBitmap(originalBitmap, width, height, true);
    }
}
