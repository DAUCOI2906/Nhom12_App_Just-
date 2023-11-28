package com.example.app_coffee_order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    Button btnimgcart,btnhome;
    //public static ArrayList<item> mangsp;
    TextView txtlsdonhang,txtdsdonhang,txtcanhan;
    RelativeLayout layoutprofile;
    TextView txtname;
    ImageView Imvedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);

        btnimgcart=(Button) findViewById(R.id.btncart);
        btnhome=(Button) findViewById(R.id.btnhome);
        txtlsdonhang= (TextView) findViewById(R.id.txtlichsu);
        txtdsdonhang=(TextView) findViewById(R.id.txtorderlist);
        txtcanhan=(TextView) findViewById(R.id.txtaboutus);
        txtname=(TextView) findViewById(R.id.txtnameken);
        Imvedit=(ImageView) findViewById(R.id.img_edituser);
        getdata();
        Imvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Information.class);
                startActivity(intent);
            }
        });
        txtcanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Aboutus.class);
                startActivity(intent);
            }
        });

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
    private void getdata(){
//        SharedPreferences pp = getSharedPreferences("myaccount", MODE_PRIVATE);
//        SharedPreferences.Editor edt= pp.edit();
//        edt.putString("UserIDNAME", tendangnhap);
        SharedPreferences sharedPreferences2=getSharedPreferences("myaccount",MODE_PRIVATE);
        String iduser=sharedPreferences2.getString("UserIDNAME","");
        String path="User/"+iduser+"/name";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value=snapshot.getValue();
                txtname.setText(value+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Doc that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }
}