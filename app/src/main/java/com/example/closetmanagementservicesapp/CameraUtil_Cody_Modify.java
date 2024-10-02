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

public class CameraUtil_Cody_Modify {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    private ImageButton imageButton;
    private String savedImagePath = "";
    private int cod_id;

    public CameraUtil_Cody_Modify(Context context, ImageButton imageButton, int cod_id) {
        this.context = context;
        this.imageButton = imageButton;
        this.cod_id = cod_id;
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
                Bitmap resizedBitmap = resizeBitmap(bitmap, 500, 500);

                String fileName = "cody_modify" + cod_id + ".png";

                // 이미지 저장
                savedImagePath = saveImageInternalStorage(resizedBitmap, fileName);

                // 저장된 이미지 불러오기
                loadImageFromStorage(savedImagePath);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Cursor cursor = db.rawQuery("SELECT * FROM Coordy WHERE cod_id = " + cod_id, null);
                if (cursor != null && cursor.moveToFirst()) {
                    Intent intent = new Intent(context, DetailCody.class);

                    intent.putExtra("cod_id", cursor.getInt(cursor.getColumnIndexOrThrow("cod_id")));
                    intent.putExtra("cod_img", cursor.getString(cursor.getColumnIndexOrThrow("cod_img")));
                    intent.putExtra("cod_loc", cursor.getInt(cursor.getColumnIndexOrThrow("cod_loc")));
                    intent.putExtra("cod_name", cursor.getString(cursor.getColumnIndexOrThrow("cod_name")));
                    intent.putExtra("cod_tag", cursor.getInt(cursor.getColumnIndexOrThrow("cod_tag")));
                    intent.putExtra("cod_date", cursor.getString(cursor.getColumnIndexOrThrow("cod_date")));
                    intent.putExtra("cod_stack", cursor.getInt(cursor.getColumnIndexOrThrow("cod_stack")));
                    intent.putExtra("cod_img_modify", fileName);
                    intent.putExtra("fileUpload", true);

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
