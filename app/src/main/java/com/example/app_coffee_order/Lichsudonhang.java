package com.example.app_coffee_order;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Lichsudonhang extends AppCompatActivity {

    Button btnbackK, btnhomeK, btnheartK, btncartK, btnuserK;

    RecyclerView rcv1, rcv2;
    lscfAdapter lscfAdapter;
    lscfAdapter2 lscfAdapter2;
    List<OrderItem> morderItemsList;
    List<OrderItem> morderItemsListOld;

    List<OrderItem> morderItemsList2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lichsudonhang_layout);

        //btn
        btnbackK = findViewById(R.id.btnbackKinh);
        btnhomeK = findViewById(R.id.btnhomeK);
        btnheartK = findViewById(R.id.btnheartK);
        btncartK = findViewById(R.id.btncartK);
        btnuserK = findViewById(R.id.btnprofileK);

        btnbackK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback = new Intent(Lichsudonhang.this,Profile.class);
                startActivity(intentback);
            }
        });
        btnhomeK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenthome = new Intent(Lichsudonhang.this,home.class);
                startActivity(intenthome);
            }
        });
        btncartK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentcart = new Intent(Lichsudonhang.this,Cart.class);
                startActivity(intentcart);
            }
        });

        btnuserK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentuser = new Intent(Lichsudonhang.this,Profile.class);
                startActivity(intentuser);
            }

        });
        addControls();

        //tab1_recycleview1
        //dinh nghia layout recycler
        rcv1 = findViewById(R.id.rcv_1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        rcv1.setLayoutManager(linearLayoutManager1);

//        //duong ke giua cac item
//        DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        rcv1.addItemDecoration(dividerItemDecoration1);

        //Khoi tao mang
        morderItemsList = new ArrayList<>();
        lscfAdapter = new lscfAdapter(this,morderItemsList);
        rcv1.setAdapter(lscfAdapter);




        //tab2_recycleview2
        //dinh nghia layout recycler
        rcv2 = findViewById(R.id.rcv_2);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        rcv2.setLayoutManager(linearLayoutManager2);

        //duong ke giua cac item
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcv2.addItemDecoration(dividerItemDecoration2);

        //Khoi tao mang
        morderItemsList2 = new ArrayList<>();
        lscfAdapter2 = new lscfAdapter2(this,morderItemsList2);
        rcv2.setAdapter(lscfAdapter2);

        getListcf1FromRealtimeDatabase();
        getListcf2FromRealtimeDatabase();

    }
    

    //tabhost
    private void addControls() {
        // Tạo tab đựng
        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        // Tạo tab
        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("Đang giao hàng");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("Đã thanh toán");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setIndicator("Đã hủy");
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);

    }

    //    Firebase List1 : dang thuc hien
    private void getListcf1FromRealtimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("OrderDetail");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(Lichsudonhang.this, "Data loaded from Firebase", Toast.LENGTH_SHORT).show();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    OrderItem orderItem1 = dataSnapshot.getValue(OrderItem.class);
                    morderItemsList.add(orderItem1);
                }
                lscfAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Lichsudonhang.this, "Get list coffee failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //        Firebase List2 : da hoan tat
    private void getListcf2FromRealtimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("OrderDetail");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    OrderItem orderItem2 = dataSnapshot.getValue(OrderItem.class);
                    morderItemsList2.add(orderItem2);
                }
                lscfAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Lichsudonhang.this, "Get list coffee failed", Toast.LENGTH_SHORT).show();

            }
        });
    }


}













//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TabHost;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.ArrayList;
//
//public class Lichsudonhang extends AppCompatActivity {
//
//    //list tab1
//
//
//    int image1[] = {R.drawable.cafemuoi, R.drawable.capuchino1, R.drawable.cafe1, R.drawable.caphetrung, R.drawable.cafedua};
//    String tencaphe1[] = {"1x SALT Coffee", "1x Capuchino", "2x SAiGON Milk Cf", "1x Egg Coffee", "1x Cococut Coffee"};
//    String size1[] = {"Size : M", "Size : L", "Size : M", "Size  M", "Size : M"};
//    String gia1[] = {"$2.95", "$3.5", "$5.5/2", "$3.5", "3.75"};
//    String mr1[] = {"Mr : Quang Anh", "Mrs : Thuy Nguyen", "Mrs : Thanh Nhan", "Mr : Van Cao", "Mrs : Quynh Cherry"};
//    String add1[] = {"Adr : 48 Cao Thang", "Adr : 127 Tran Phu", "Adr : 95 Nguyen Hue", "Adr : 595 Tran Van Do", "Adr : 73 Tran Cao Van"};
//
//    //list tab2
//    int image2[] = {R.drawable.cafe1, R.drawable.cafemuoi, R.drawable.caphetrung};
//    String tencaphe2[] = {"1x The House", "1x SALT Coffee", "1x Egg Coffee"};
//    String size2[] = {"Size : M", "Size : L", "Size : M"};
//    String gia2[] = {"$2.95", "$2.95", "$3.5"};
//    String thanhtoan2[] = {"Đã thanh toán", "Đã thanh toán", "Đã thanh toán"};
//
//    Button btnbackK, btnhomeK, btnheartK, btncartK, btnuserK;
//    ListView lvmh1, lvmh2;
//    ArrayList<cf1> myList1;
//    ArrayList<cf2> myList2;
//    ArrayAdapter1 ArrayAdapter1;
//    ArrayAdapter2 ArrayAdapter2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.lichsudonhang_layout);
//
//        //Button
//        btnbackK = findViewById(R.id.btnbackKinh);
//        btnhomeK = findViewById(R.id.btnhomeK);
//        btnheartK = findViewById(R.id.btnheartK);
//        btncartK = findViewById(R.id.btncartK);
//        btnuserK = findViewById(R.id.btnprofileK);
//
//
//        btnbackK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentback = new Intent(Lichsudonhang.this,Profile.class);
//                startActivity(intentback);
//            }
//        });
//        btnhomeK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intenthome = new Intent(Lichsudonhang.this,home.class);
//                startActivity(intenthome);
//            }
//        });
//        btncartK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentcart = new Intent(Lichsudonhang.this,Cart.class);
//                startActivity(intentcart);
//            }
//        });
//
//        btnuserK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentuser = new Intent(Lichsudonhang.this,Profile.class);
//                startActivity(intentuser);
//            }
//        });
//        addControls();
//
//        //tab1_listview1
//        lvmh1 = findViewById(R.id.lvmanhinh1);
//        myList1 = new ArrayList<>(); // Tạo mới mảng rỗng
//        for (int i = 0; i< tencaphe1.length; i++)
//        {
//            myList1.add(new cf1(image1[i], tencaphe1[i], size1[i], gia1[i], mr1[i], add1[i]));
//        }
//        ArrayAdapter1 = new ArrayAdapter1(Lichsudonhang.this, R.layout.listviewitem1, myList1);
//        lvmh1.setAdapter(ArrayAdapter1);
//
//        //tab1_listview2
//        lvmh2 = findViewById(R.id.lvmanhinh2);
//        myList2 = new ArrayList<>(); // Tạo mới mảng rỗng
//        for (int i = 0; i< tencaphe2.length; i++)
//        {
//            myList2.add(new cf2(image2[i], tencaphe2[i], size2[i], gia2[i], thanhtoan2[i]));
//        }
//        ArrayAdapter2 = new ArrayAdapter2(Lichsudonhang.this, R.layout.listviewitem2, myList2);
//        lvmh2.setAdapter(ArrayAdapter2);
//    }
//
//    private void addControls() {
//        // Tạo tab đựng
//        TabHost tabHost = findViewById(R.id.tabHost);
//        tabHost.setup();
//
//        // Tạo tab
//        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
//        tab1.setIndicator("Đang thực hiện");
//        tab1.setContent(R.id.tab1);
//        tabHost.addTab(tab1);
//
//        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
//        tab2.setIndicator("Đã hoàn tất");
//        tab2.setContent(R.id.tab2);
//        tabHost.addTab(tab2);
//
//        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
//        tab3.setIndicator("Đã hủy");
//        tab3.setContent(R.id.tab3);
//        tabHost.addTab(tab3);
//
//    }
//}