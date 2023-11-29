package com.example.app_coffee_order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class forgot_pass extends AppCompatActivity {
    Button savepass;
    CheckBox ckbnotrobot;
    EditText edtmoi,edtmoi2;
    String tendangnhap;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        savepass = findViewById(R.id.btnsavepass);
        ckbnotrobot = findViewById(R.id.ckbnotrobot);
        edtmoi = findViewById(R.id.edtmkmoi);
        edtmoi2= findViewById(R.id.edtmkmoi2);



        // get data SharedPreferences
        //anh xa cac element
        SharedPreferences pp2 = getSharedPreferences("myaccount", MODE_PRIVATE);
        tendangnhap= pp2.getString("UserIDNAME","");

        //FireBase

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User").child(tendangnhap);
        // sua pass



        //add sự kiện button lưu
        savepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = edtmoi.getText().toString();
                String b = edtmoi2.getText().toString();
                if(a.equals(b)==false){
                    //show msg box
                    Toast.makeText(forgot_pass.this,"Vui lòng điền đúng mật khẩu",Toast.LENGTH_SHORT).show();
                }
                else if(ckbnotrobot.isChecked()==false){
                    Toast.makeText(forgot_pass.this,"Ứng dụng không hỗ trợ robot",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    // sua pass
                    databaseReference.child("password").setValue(a);
                    //chuyen trang
                    finish();
                }
            }
        });

    }
}