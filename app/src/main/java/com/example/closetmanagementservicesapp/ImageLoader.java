package com.example.closetmanagementservicesapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;


class ImageLoader {

    private Activity activity;
    private ImageView imageView;

    public ImageLoader(Activity activity, ImageView imageView) {
        this.activity = activity;
        this.imageView = imageView;
    }

    // 이미지를 선택하는 메서드
    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, 2);
    }

    // onActivityResult에서 호출되는 메서드
    public void loadImageFromResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    ContentResolver resolver = activity.getContentResolver();
                    InputStream inputStream = resolver.openInputStream(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();

                    imageView.setImageBitmap(bitmap);
                    Toast.makeText(activity.getApplicationContext(), "이미지를 성공적으로 로드했습니다.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(activity.getApplicationContext(), "이미지 로드에 실패했습니다.", Toast.LENGTH_LONG).show();
                    Log.e("ImageLoader", "Error loading image", e);
                }
            }
        }
    }
}