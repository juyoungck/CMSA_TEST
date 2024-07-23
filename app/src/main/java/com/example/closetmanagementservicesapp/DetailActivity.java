package com.example.closetmanagementservicesapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// 이미지 버튼을 클릭했을 때 실행되는 java 코드
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        byte[] c_img = getIntent().getByteArrayExtra("c_img");
        int c_loc = getIntent().getIntExtra("c_loc", 0);
        String c_name = getIntent().getStringExtra("c_name");
        String c_size = getIntent().getStringExtra("c_size");
        String c_brand = getIntent().getStringExtra("c_brand");
        int c_tag = getIntent().getIntExtra("c_tag", 0);
        String c_memo = getIntent().getStringExtra("c_memo");
        String c_date = getIntent().getStringExtra("c_date");
        int c_stack = getIntent().getIntExtra("c_stack", 0);

        Bitmap bitmap = BitmapFactory.decodeByteArray(c_img, 0, c_img.length);

        ImageView imageView = findViewById(R.id.detail_clothImg);
        TextView locationTextView = findViewById(R.id.detail_clothLocation);
        TextView nameTextView = findViewById(R.id.detail_clothName);
        TextView sizeTextView = findViewById(R.id.detail_clothSize);
        TextView brandTextView = findViewById(R.id.detail_clothBrand);
        TextView tagTextView = findViewById(R.id.detail_clothTag);
        TextView memoTextView = findViewById(R.id.detail_clothMemo);
        TextView dateTextView = findViewById(R.id.detail_clothDate);
        TextView stackTextView = findViewById(R.id.detail_clothStack);

        imageView.setImageBitmap(bitmap);
        locationTextView.setText(String.valueOf(c_loc));
        nameTextView.setText(c_name);
        sizeTextView.setText(c_size);
        brandTextView.setText(c_brand);
        tagTextView.setText(String.valueOf(c_tag));
        memoTextView.setText(c_memo);
        dateTextView.setText(c_date);
        stackTextView.setText(String.valueOf(c_stack));
    }
}
