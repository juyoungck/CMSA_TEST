package com.example.closetmanagementservicesapp;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class CameraUtil_Cody extends AppCompatActivity{
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    private ImageButton imageButton;
    private String savedImagePath = "";
    private Bitmap capturedImage;

    public CameraUtil_Cody(Context context, ImageButton imageButton) {
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
                Bitmap resizedBitmap = resizeBitmap(bitmap, 300, 300);
                Cursor cursor = db.rawQuery("SELECT MAX(cod_id) FROM Coordy", null);
                int cId = 1000;

                if (cursor != null && cursor.moveToFirst()) {
                    cId = cursor.getInt(0);
                    cId++;
                    cursor.close();
                }

                String codyFileName = "cody_" + cId + ".png";

                // 이미지 저장
                savedImagePath = saveImageInternalStorage(resizedBitmap, codyFileName);

                // 캡처된 이미지 저장
                this.capturedImage = resizedBitmap;

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Intent intent = new Intent(context, CodyAdd.class);
                intent.putExtra("imageData", byteArray);
                intent.putExtra("codyFileName", codyFileName);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        }
    }

    private String saveImageInternalStorage(Bitmap bitmap, String codyFileName) {
        File directory = new File(context.getFilesDir(), "images");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, codyFileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(context, "이미지가 내부 저장소에 저장되었습니다.", Toast.LENGTH_SHORT).show();

            // 저장된 파일의 경로 반환
            return file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "이미지 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private Bitmap resizeBitmap(Bitmap originalBitmap, int width, int height) {
        return Bitmap.createScaledBitmap(originalBitmap, width, height, true);
    }


}
