package com.example.app_coffee_order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Information extends AppCompatActivity {

    String iduser,pwuser;
    TextView edtname,edtsdt,edtaddress,edtemail,txtname;
    Button btncapnhat;
    ImageView imgback;
    private boolean isToastShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Anhxa();
        getdata("name",edtname);
        getdata("email",edtemail);
        getdata("address",edtaddress);
        getdata("phone",edtsdt);
        getdata("name",txtname);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Information.this, Profile.class);
                startActivity(intent);
            }
        });

        btncapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                updatedata("name",edtname);
//                updatedata("email",edtemail);
//                updatedata("address",edtaddress);
//                updatedata("phone",edtsdt);
                updatename();
            }
        });
    }
    private void updatename(){
        SharedPreferences sharedPreferences2=getSharedPreferences("myaccount",MODE_PRIVATE);
        iduser=sharedPreferences2.getString("UserIDNAME","");
        pwuser=sharedPreferences2.getString("UserPassword","");
        String path="User/"+iduser;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        myRef.removeValue();

        UserInfo userInfo = new UserInfo(edtname.getText().toString(), edtsdt.getText().toString(), edtemail.getText().toString(), pwuser, edtaddress.getText().toString());
        String path2="User/"+edtname.getText().toString();
        DatabaseReference myRef1 = database.getReference(path2);
        myRef1.setValue(userInfo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                getdata("name",edtname);
                getdata("email",edtemail);
                getdata("address",edtaddress);
                getdata("phone",edtsdt);
                getdata("name",txtname);
            }
        });

        SharedPreferences pp = getSharedPreferences("myaccount", MODE_PRIVATE);
        SharedPreferences.Editor edt= pp.edit();
        edt.remove("UserIDNAME");
        edt.commit();

        SharedPreferences.Editor editor=pp.edit();
        editor.putString("UserIDNAME",edtname.getText().toString());
        editor.commit();


    }

    private void updatedata(String key, TextView edtdulieu) {
        SharedPreferences sharedPreferences2=getSharedPreferences("myaccount",MODE_PRIVATE);
        iduser=sharedPreferences2.getString("UserIDNAME","");
        String path="User/"+iduser+"/"+key;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        myRef.setValue(edtdulieu.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                if (!isToastShown) {
                    // Cập nhật thành công
                    Toast.makeText(Information.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    isToastShown = true;
                }
            }
        });
    }

    private void Anhxa(){
        edtname=(TextView) findViewById(R.id.edt_nameuser);
        edtsdt=(TextView) findViewById(R.id.edt_phoneuser);
        edtaddress=(TextView) findViewById(R.id.edt_addressuser);
        edtemail=(TextView) findViewById(R.id.edt_emailuser);
        txtname=(TextView) findViewById(R.id.txtnamepro);
        btncapnhat=(Button) findViewById(R.id.btn_capnhat);
        imgback=(ImageView) findViewById(R.id.imgleftken);
    }
    private void getdata(String key, TextView edtdulieu){
        SharedPreferences sharedPreferences2=getSharedPreferences("myaccount",MODE_PRIVATE);
        iduser=sharedPreferences2.getString("UserIDNAME","");
        String path="User/"+iduser+"/"+key;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value=snapshot.getValue();
                edtdulieu.setText(value+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Information.this, "Doc that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }
}