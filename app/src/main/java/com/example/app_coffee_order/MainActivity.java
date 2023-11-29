package com.example.app_coffee_order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button back,list_bar,change_address,plus,minus,size_l,size_m,size_s,buy,home,love,cart,user,save;
    TextView address_detail,name_product,number_buy,price,ship,total_cost;
    ImageView image_product;
    EditText address;
    Boolean size_s_click,size_m_click,size_l_click;
    String tentaikhoan,diachi,sdt,anhsp,idorder,trangthai1,trangthai2,tensp;
    int gia,soluong;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //edittext
        address = findViewById(R.id.edtaddress);
        //get button
        back = findViewById(R.id.btnback);
        list_bar = findViewById(R.id.btnlistbar);
        change_address = findViewById(R.id.btnchangeaddress);
        plus = findViewById(R.id.btnplus);
        minus = findViewById(R.id.btnminus);
        size_l= findViewById(R.id.btnsizel);
        size_m = findViewById(R.id.btnsizem);
        size_s = findViewById(R.id.btnsizes);
        buy = findViewById(R.id.btnbuy);
        home = findViewById(R.id.btnhome);
        cart = findViewById(R.id.btncart);
        love = findViewById(R.id.btnlove);
        user = findViewById(R.id.btnuser);
        save = findViewById(R.id.btnsave);
        //text view
        address_detail = findViewById(R.id.txtaddress_detail);
        name_product = findViewById(R.id.txtnameproduct);
        number_buy = findViewById(R.id.txtnumberbuy);
        price = findViewById(R.id.txtprice);
        ship = findViewById(R.id.txtship);
        total_cost = findViewById(R.id.txttotalcost);
        //image
        image_product = findViewById(R.id.imgproduct);


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count;
                count = Integer.parseInt(number_buy.getText().toString());
                count =  count + 1;
                number_buy.setText(count+"");
                double dgia=0,phiship=0;
                phiship= Double.parseDouble(ship.getText().toString());
                //gia = count*Double.parseDouble(price.getText().toString());
                dgia = count*gia;
                price.setText(dgia+"");
                double total = dgia +phiship;
                total_cost.setText(total+"");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count;
                count = Integer.parseInt(number_buy.getText().toString());
                if(count == 1)
                {
                    count=1;
                    number_buy.setText(count+"");
                    double dgia=0,phiship=0;
                    phiship= Double.parseDouble(ship.getText().toString());
                    //gia = count*Double.parseDouble(price.getText().toString());
                    dgia = count*gia;
                    price.setText(dgia+"");
                    double total = dgia +phiship;
                    total_cost.setText(total+"");
                }
                else{
                    count =count-1;
                    number_buy.setText(count+"");
                    double dgia=0,phiship=0;
                    phiship= Double.parseDouble(ship.getText().toString());
                    //gia = count*Double.parseDouble(price.getText().toString());
                    dgia = count*gia;
                    price.setText(dgia+"");
                    double total = dgia +phiship;
                    total_cost.setText(total+"");
                }
            }
        });
        //css size click
        size_s_click=false;
        size_l_click= false;
        size_m_click=false;
        size_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!size_s_click) {
                    // Button chưa được click
                    size_s_click=true;
                    size_s.setBackgroundColor(getResources().getColor(R.color.colorsize));
                    size_l.setBackgroundColor(getResources().getColor(R.color.returncolor));
                    size_m.setBackgroundColor(getResources().getColor(R.color.returncolor));

                } else {
                    size_s.setBackgroundColor(getResources().getColor(R.color.returncolor));
                    size_s_click=false;
                }
            }
        });
        size_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!size_m_click) {
                    // Button chưa được click
                    size_m_click=true;
                    size_m.setBackgroundColor(getResources().getColor(R.color.colorsize));
                    size_s.setBackgroundColor(getResources().getColor(R.color.returncolor));
                    size_l.setBackgroundColor(getResources().getColor(R.color.returncolor));

                } else {
                    size_m.setBackgroundColor(getResources().getColor(R.color.returncolor));
                    size_m_click=false;
                }
            }
        });
        size_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!size_l_click) {
                    // Button chưa được click
                    size_l_click=true;
                    size_l.setBackgroundColor(getResources().getColor(R.color.colorsize));
                    size_s.setBackgroundColor(getResources().getColor(R.color.returncolor));
                    size_m.setBackgroundColor(getResources().getColor(R.color.returncolor));
                } else {
                    size_l.setBackgroundColor(getResources().getColor(R.color.returncolor));
                    size_l_click=false;
                }
            }
        });
        change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address_detail.getVisibility() == View.VISIBLE) {
                    //ẩn textview
                    address_detail.setVisibility(View.GONE);
                    //hiện edittext đê sửa
                    address.setVisibility(View.VISIBLE);
                    address.setText(address_detail.getText().toString());
                    //hiển thị button save
                    save.setVisibility(View.VISIBLE);
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address.getVisibility() == View.VISIBLE) {
                    //ẩn edittext
                    address.setVisibility(View.GONE);
                    //hiện textview
                    address_detail.setText(address.getText().toString());
                    address_detail.setVisibility(View.VISIBLE);
                    //ẩn  button save
                    save.setVisibility(View.GONE);
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohomehuy = new Intent(MainActivity.this,home.class);
                startActivity(gohomehuy);
            }
        });



        ///BACKEND

        //lay khach hang
        //anh xa cac element
        SharedPreferences pp2 = getSharedPreferences("myaccount", MODE_PRIVATE);
        tentaikhoan= pp2.getString("UserIDNAME","");
        //lay id sanpham
        Intent intent = getIntent();
        Bundle mybun = intent.getBundleExtra("IDSPP2");
        String idsp = mybun.getString("IDSP2");

        //ket noi lay data
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
        //lay thong tin tk
        DatabaseReference df = databaseReference.child("User").child(tentaikhoan);
        //diachi
        df.child("address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Lấy giá trị của phần tử con
                    String value = snapshot.getValue(String.class);
                    diachi = value;
                    address_detail.setText(value);
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
        //sdt
        df.child("phone").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Lấy giá trị của phần tử con
                    String value = snapshot.getValue(String.class);
                    sdt = value;
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



        //laythongtin sanpham
        DatabaseReference dff = databaseReference.child("product_detail").child(idsp);
        //layanh
        dff.child("image").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Lấy giá trị của phần tử con
                    String value = snapshot.getValue(String.class);
                    anhsp = value;
                    Picasso.get().load(value).into(image_product);
                    soluong = Integer.parseInt(number_buy.getText().toString());
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
        //tensanpham
        dff.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Lấy giá trị của phần tử con
                    String value = snapshot.getValue(String.class);
                    tensp = value;
                    name_product.setText(value);
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
        //laygia
        dff.child("price").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Lấy giá trị của phần tử con
                    int value = snapshot.getValue(int.class);
                    gia = value;
                    price.setText(String.valueOf(value));
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
        // Hiển thị thông tin sản phẩm trong giao diện người dùng
        // Load hình ảnh từ URL (cần sử dụng thư viện Picasso hoặc Glide)
        soluong= Integer.parseInt(number_buy.getText().toString());







        //intent buy
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OrderItem item = new OrderItem(address_detail.getText().toString(),tentaikhoan,sdt,anhsp,"1","Đã giao hàng","Đã thanh toán",tensp,gia,soluong);
                DatabaseReference dfff = firebaseDatabase.getReference().child("OrderDetail");
                // Tạo một đối tượng Random
                Random random = new Random();

                // Tạo số ngẫu nhiên trong khoảng từ 1 đến 100
                int randomNumber = random.nextInt(100) + 2;
                dff.child(String.valueOf(randomNumber)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Kiểm tra xem có dữ liệu hay không
                        if (snapshot.exists()) {
                            // Lấy giá trị của phần tử con
                            String value = snapshot.getKey();
                            int khoa = Integer.parseInt(value)+1;
                            // tao nut doi tuong con
                            DatabaseReference chlid = dfff.child(String.valueOf(khoa));
                            chlid.setValue(item);
                            Intent gohomebuy = new Intent(MainActivity.this, Muahangthanhcong.class);
                            startActivity(gohomebuy);
                        } else {
                            DatabaseReference chlid = dfff.child(String.valueOf(randomNumber));
                            chlid.setValue(item);
                            Intent gohomebuy = new Intent(MainActivity.this, Muahangthanhcong.class);
                            startActivity(gohomebuy);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }


}