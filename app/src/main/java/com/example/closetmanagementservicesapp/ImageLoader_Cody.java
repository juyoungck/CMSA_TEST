package com.example.closetmanagementservicesapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


class ImageLoader_Cody {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Activity activity;
    private ImageView imageButton;
    private String savedImagePath = "";
    private Bitmap loadedImage;  // 로드된 이미지를 저장할 변수

    public ImageLoader_Cody(Activity activity, ImageView imageButton) {
        this.activity = activity;
        this.imageButton = imageButton;
    }

    // 이미지를 선택하는 메서드
    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, 2);
    }

    // onActivityResult에서 호출되는 메서드
    public void loadImageFromResult(int requestCode, int resultCode, Intent data) {
        // DB OPEN
        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    ContentResolver resolver = activity.getContentResolver();
                    InputStream inputStream = resolver.openInputStream(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();

                    Bitmap resizedBitmap = resizeBitmap(bitmap, 300, 300);
                    Cursor cursor = db.rawQuery("SELECT MAX(cod_id) FROM Coordy", null);
                    int cId = 0;

                    if (cursor != null && cursor.moveToFirst()) {
                        cId = cursor.getInt(0);
                        cId++;
                        cursor.close();
                    }

                    String fileName = "cody_" + cId + ".png";

                    // 이미지 저장
                    savedImagePath = saveImageInternalStorage(resizedBitmap, fileName);

                    // 로드된 이미지 저장
                    this.loadedImage = resizedBitmap;  // 저장된 이미지

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    Intent intent = new Intent(activity, CodyAdd.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra("imageData", byteArray);
                    intent.putExtra("codyFileName", fileName);
                    activity.startActivity(intent);

                    imageButton.setImageBitmap(bitmap);
                    Toast.makeText(activity.getApplicationContext(), "이미지를 성공적으로 로드했습니다.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(activity.getApplicationContext(), "이미지 로드에 실패했습니다.", Toast.LENGTH_LONG).show();
                    Log.e("ImageLoader", "Error loading image", e);
                }
            }
        }
    }

    private String saveImageInternalStorage(Bitmap bitmap, String fileName) {
        File directory = new File(activity.getFilesDir(), "images");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Log.d("File Save Path", file.getAbsolutePath());

            // 저장된 파일의 경로 반환
            return file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "이미지 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private Bitmap resizeBitmap(Bitmap originalBitmap, int width, int height) {
        return Bitmap.createScaledBitmap(originalBitmap, width, height, true);
    }
}
