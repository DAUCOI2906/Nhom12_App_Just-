package com.example.app_coffee_order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText username, pass;

    CheckBox save_account;
    // WebView my_web;
    Button forgot_pass, login, facebook, twitter, instagram, google, signup;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String tendangnhap, matkhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Edittext
        username = findViewById(R.id.edtusername);
        pass = findViewById(R.id.edtpass);
        //Checkbox
        save_account = findViewById(R.id.ckbsaveaccount);
        //Button
        forgot_pass = findViewById(R.id.btnforgotpass);
        login = findViewById(R.id.btn_login);
        facebook = findViewById(R.id.btnfacebook);
        twitter = findViewById(R.id.btntwiter);
        instagram = findViewById(R.id.btninstagram);
        google = findViewById(R.id.btngoogle);
        signup = findViewById(R.id.btnSingup);


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_face = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
                startActivity(intent_face);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_twitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"));
                startActivity(intent_twitter);
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_ins = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"));
                startActivity(intent_ins);
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_google = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
                startActivity(intent_google);
            }
        });
        //quên mật khẩu
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forget_pass = new Intent(Login.this, forgot_pass.class);
                startActivity(forget_pass);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gosign = new Intent(Login.this, RegisterActivity.class);
                startActivity(gosign);
            }
        });






        ////////LOGIN

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference().child("User");
                String tenKH = username.getText().toString();
                String MK = pass.getText().toString();
                if(tenKH.isEmpty() || MK.isEmpty())
                {
                    Toast.makeText(Login.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child(tenKH).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Nút con tồn tại
                                // Thực hiện các công việc cần thiết ở đây
                                DatabaseReference df = firebaseDatabase.getReference().child("User").child(tenKH);

                                //laymatkhau
                                df.child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        // Kiểm tra xem có dữ liệu hay không
                                        if (snapshot.exists()) {
                                            // Lấy giá trị của phần tử con
                                            String value = snapshot.getValue(String.class);
                                            matkhau = value;
                                            if(matkhau.equals(MK))
                                            {
                                                Intent gohomehuy2 = new Intent(Login.this,home.class);
                                                startActivity(gohomehuy2);
                                            }
                                            else {
                                                Toast.makeText(Login.this, "Không tồn tại tài khoản", Toast.LENGTH_SHORT).show();
                                            }
                                            Log.d("FirebaseExample", "Giá trị của phần tử con: " + value);
                                        } else {
                                            Log.d("FirebaseExample", "Phần tử con không tồn tại.");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e("FirebaseExample", "Lỗi: " + error.getMessage());
                                    }
                                });
                                df.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        // Kiểm tra xem có dữ liệu hay không
                                        if (snapshot.exists()) {
                                            // Lấy giá trị của phần tử con
                                            String value = snapshot.getValue(String.class);
                                            tendangnhap = value;

                                            Log.d("FirebaseExample", "Giá trị của phần tử con: " + value);
                                        } else {
                                            Log.d("FirebaseExample", "Phần tử con không tồn tại.");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e("FirebaseExample", "Lỗi: " + error.getMessage());
                                    }
                                });


                            } else {
                                // Nút con không tồn tại
                                Toast.makeText(Login.this, "Không tồn tại tài khoản", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("FirebaseExample", "Lỗi: " + error.getMessage());
                        }
                    });

                }

            }
        });


    }
    //start onPause() nap data
    @Override
    protected void onPause() {
        super.onPause();
        // 4 buoc luu du lieu
        SharedPreferences pp = getSharedPreferences("myaccount", MODE_PRIVATE);
        SharedPreferences.Editor edt= pp.edit();
        edt.putString("UserIDNAME", tendangnhap);
        edt.putString("UserPassword",matkhau);
        edt.commit();
    }
}


