package com.example.app_coffee_order;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class product_detail extends AppCompatActivity {
    ImageView inc, dec;
    TextView quantityNum;
    Button buttonS, buttonM, buttonL;

    ImageView close;
    private String selectedSize;
    int i = 0;

    String productId;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference productDetail;
//    private String productId = "2"; // Biến toàn cục để lưu trữ productId

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle mybun = intent.getBundleExtra("IDSPP");
        String masp = mybun.getString("IDSP");
        productId = masp;
        Toast.makeText(this, "Product ID: " + productId, Toast.LENGTH_SHORT).show();



        quantityNum = findViewById(R.id.quantityNums);
        inc = findViewById(R.id.plusBtnHung);
        dec = findViewById(R.id.minusBtnHung);

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                quantityNum.setText(String.valueOf(i));
                saveQuantity(i);
            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i > 0) {
                    i--;
                    quantityNum.setText(String.valueOf(i));
                    saveQuantity(i);
                }
            }
        });

        // Ánh xạ và thêm sự kiện cho nút Size S
        buttonS = findViewById(R.id.buttonS);
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSizeSelection("S");
            }
        });

        // Ánh xạ và thêm sự kiện cho nút Size M
        buttonM = findViewById(R.id.buttonM);
        buttonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSizeSelection("M");
            }
        });

        // Ánh xạ và thêm sự kiện cho nút Size L
        buttonL = findViewById(R.id.buttonL);
        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSizeSelection("L");
            }
        });

















        // --------------    Firebase    ----------------
        // --------------    Firebase    ----------------
        firebaseDatabase = FirebaseDatabase.getInstance();
        productDetail = firebaseDatabase.getReference("product_detail");
        //layten
        productDetail.child(productId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Kiểm tra xem có dữ liệu hay không
                if (dataSnapshot.exists()) {
                    // Lấy giá trị của phần tử con
                    String value = dataSnapshot.getValue(String.class);
                    TextView textViewName = findViewById(R.id.productName);
                    textViewName.setText(value);
                    Log.d("FirebaseExample", "Giá trị của phần tử con: " + value);
                } else {
                    Log.d("FirebaseExample", "Phần tử con không tồn tại.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseExample", "Lỗi: " + databaseError.getMessage());
            }
        });
        //lay hinh
        productDetail.child(productId).child("image").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Kiểm tra xem có dữ liệu hay không
                if (dataSnapshot.exists()) {
                    // Lấy giá trị của phần tử con
                    String value = dataSnapshot.getValue(String.class);
                    // Hiển thị thông tin sản phẩm trong giao diện người dùng
                    ImageView imageView = findViewById(R.id.productImg);
//                  // Load hình ảnh từ URL (cần sử dụng thư viện Picasso hoặc Glide)
                    Picasso.get().load(value).into(imageView);
                    Log.d("FirebaseExample", "Giá trị của phần tử con: " + value);
                } else {
                    Log.d("FirebaseExample", "Phần tử con không tồn tại.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseExample", "Lỗi: " + databaseError.getMessage());
            }
        });
        // lay gia
        productDetail.child(productId).child("price").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Kiểm tra xem có dữ liệu hay không
                if (dataSnapshot.exists()) {
                    // Lấy giá trị của phần tử con
                    int value = dataSnapshot.getValue(int.class);
                    TextView textViewPrice = findViewById(R.id.productPrice);
                    textViewPrice.setText(String.valueOf(value));
                    Log.d("FirebaseExample", "Giá trị của phần tử con: " + value);
                } else {
                    Log.d("FirebaseExample", "Phần tử con không tồn tại.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseExample", "Lỗi: " + databaseError.getMessage());
            }
        });
        //lay mota
        productDetail.child(productId).child("mota").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Kiểm tra xem có dữ liệu hay không
                if (dataSnapshot.exists()) {
                    // Lấy giá trị của phần tử con
                    String value = dataSnapshot.getValue(String.class).toString();
                    TextView textViewMota = findViewById(R.id.mota_hung);
                    textViewMota.setText(value);
                    Log.d("FirebaseExample", "Giá trị của phần tử con: " + value);
                } else {
                    Log.d("FirebaseExample", "Phần tử con không tồn tại.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseExample", "Lỗi: " + databaseError.getMessage());
            }
        });




        // Tiếp tục với việc hiển thị thông tin sản phẩm sử dụng ID này

    }













    // ----------------------- Size button   ----------------------------
    private void handleSizeSelection(String selectedSize) {
        // Hiển thị thông báo hoặc thực hiện các thao tác khác với kích thước đã chọn
        Toast.makeText(getApplicationContext(), "Selected Size: " + selectedSize, Toast.LENGTH_SHORT).show();
        // Có thể thực hiện các thao tác khác tại đây

        // Cập nhật màu sắc của nút
        resetButtonColors();
        switch (selectedSize) {
            case "S":
                buttonS.setBackgroundResource(R.drawable.button_pressed);
                break;
            case "M":
                buttonM.setBackgroundResource(R.drawable.button_pressed);
                break;
            case "L":
                buttonL.setBackgroundResource(R.drawable.button_pressed);
                break;
        }

        // Lưu thông tin vào Firebase
    }

    private void resetButtonColors() {
        buttonS.setBackgroundResource(R.drawable.button_default);
        buttonM.setBackgroundResource(R.drawable.button_default);
        buttonL.setBackgroundResource(R.drawable.button_default);
    }

    // ----------------------------------------------------------------------------


//    private void displayProductDetails(ProductItemDetail product) {
//        if (product != null) {
//            // Hiển thị thông tin sản phẩm trong giao diện người dùng
//            ImageView imageView = findViewById(R.id.productImg);
//            // Load hình ảnh từ URL (cần sử dụng thư viện Picasso hoặc Glide)
//            Picasso.get().load(product.getImage()).into(imageView);
//
//            TextView textViewName = findViewById(R.id.productName);
//            TextView textViewMota = findViewById(R.id.mota_hung);
//            TextView textViewPrice = findViewById(R.id.productPrice);
//
//            textViewName.setText(product.getName());
//            textViewMota.setText(product.getMota());
//            textViewPrice.setText(String.valueOf(product.getPrice())+"đ");
//
//            // Lưu dữ liệu vào Firebase khi hiển thị chi tiết sản phẩm
////            saveDataToFirebase(product.getSize(), "1"); // 1 là số lượng mặc định
//        }
//    }

//    --------------<   Lưu dữ liệu vào Firebase    >-----------------



    //  ---------------<  Lưu số lượng và size vào Firebase  >----------------
    private void saveQuantity(int quantity) {
        productDetail.child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductItemDetail product = snapshot.getValue(ProductItemDetail.class);
                if (product!=null) {
                    product.setQuantity(Integer.parseInt(String.valueOf(quantity)));
                    productDetail.child(productId).setValue(product);
                    Toast.makeText(getApplicationContext(), "Quantity updated sucessfully!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveSize(String sizeSelected) {
        productDetail.child(productId).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductItemDetail product = snapshot.getValue(ProductItemDetail.class);
                if (product!=null) {
                    product.setSize(selectedSize);
                    productDetail.child(productId).setValue(product);
                    Toast.makeText(getApplicationContext(), "Size updated sucessfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}