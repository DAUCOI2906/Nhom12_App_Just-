package com.example.app_coffee_order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    Button btnimgcart,btnhome;
    //public static ArrayList<item> mangsp;
    TextView txtlsdonhang,txtdsdonhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);

        btnimgcart=(Button) findViewById(R.id.btncart);
        btnhome=(Button) findViewById(R.id.btnhome);
        txtlsdonhang= (TextView) findViewById(R.id.txtlichsu);
        txtdsdonhang=(TextView) findViewById(R.id.txtorderlist);

        btnimgcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Cart.class);
                startActivity(intent);
            }
        });
        txtlsdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Lichsudonhang.class);
                startActivity(intent);
            }
        });

        txtdsdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Danhsachsanpham.class);
                startActivity(intent);
            }
        });

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, home.class);
                startActivity(intent);
            }
        });
    }
}