package com.example.app_coffee_order;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class product_detail extends AppCompatActivity {
    Button btnorder;
    ImageView imgclose;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        btnorder = findViewById(R.id.btnbuynow);
        imgclose= findViewById(R.id.closeDetail);
        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goorder= new Intent(product_detail.this,MainActivity.class);
                startActivity(goorder);
            }
        });
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome= new Intent(product_detail.this,home.class);
                startActivity(gohome);
            }
        });
    }
}