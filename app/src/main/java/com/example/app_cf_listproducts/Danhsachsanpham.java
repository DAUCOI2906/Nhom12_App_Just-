package com.example.app_cf_listproducts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Danhsachsanpham extends AppCompatActivity {
    Button  btnhomeK, btnheartK, btncartK, btnuserK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachsanpham_layout);

        btnhomeK = findViewById(R.id.btnhomeK);
        btnheartK = findViewById(R.id.btnheartK);
        btncartK = findViewById(R.id.btncartK);
        btnuserK = findViewById(R.id.btnprofileK);

        btnhomeK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenthome = new Intent(Danhsachsanpham.this,home.class);
                startActivity(intenthome);
            }
        });
//        btnheartK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentheart = new Intent(Lichsudonhang.this,Danhsachsanpham.class);
//                startActivity(intentheart);
//            }
//        });
        btncartK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentcart = new Intent(Danhsachsanpham.this,Cart.class);
                startActivity(intentcart);
            }
        });
        btnuserK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentuser = new Intent(Danhsachsanpham.this,Profile.class);
                startActivity(intentuser);
            }
        });
    }
}