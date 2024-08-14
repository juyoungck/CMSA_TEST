package com.example.closetmanagementservicesapp;

import android.content.Intent;
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

        ImageView imageView = (ImageView) findViewById(R.id.detail_c_img);
        TextView detail_c_loc = (TextView) findViewById(R.id.detail_c_loc);
        TextView detail_c_name = (TextView) findViewById(R.id.detail_c_name);
        TextView detail_c_type = (TextView) findViewById(R.id.detail_c_type);
        TextView detail_c_size = (TextView) findViewById(R.id.detail_c_size);
        TextView detail_c_brand = (TextView) findViewById(R.id.detail_c_brand);
        TextView detail_c_tag = (TextView) findViewById(R.id.detail_c_tag);
        TextView detail_c_memo = (TextView) findViewById(R.id.detail_c_memo);
        TextView detail_c_date = (TextView) findViewById(R.id.detail_c_date);
        TextView detail_c_stack = (TextView) findViewById(R.id.detail_c_stack);

        Intent intent = getIntent();
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
        imageView.setImageBitmap(bitmap);
        detail_c_loc.setText(String.valueOf(c_loc));
        detail_c_name.setText(c_name);
        detail_c_type.setText(c_type);
        detail_c_size.setText(c_size);
        detail_c_brand.setText(c_brand);
        detail_c_tag.setText(String.valueOf(c_tag));
        detail_c_memo.setText(c_memo);
        detail_c_date.setText(c_date);
        detail_c_stack.setText(String.valueOf(c_stack));
    }
}
