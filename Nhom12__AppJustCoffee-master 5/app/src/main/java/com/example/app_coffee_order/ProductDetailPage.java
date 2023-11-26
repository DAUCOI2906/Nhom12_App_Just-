package com.example.app_coffee_order;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

//---------------<  VIEW ITEM DETAIL - ADD ITEM INFO TO CART DATA FIREBASE  >-----------------
public class ProductDetailPage extends AppCompatActivity {
    ImageView inc, dec;
    TextView quantityNum;
    Button buttonS, buttonM, buttonL, addToCart, buynow;

    ImageView closeDetail;
    private String selectedSize;
    int i = 0;

    String productId;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference productDetail, cartReference;
//    private String productId = "2"; // Biến toàn cục để lưu trữ productId

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        Bundle mybun = intent.getBundleExtra("IDSPP");
        String masp = mybun.getString("IDSP");
        productId = masp;
        Toast.makeText(this, "Product ID: " + productId, Toast.LENGTH_SHORT).show();
        //buy
        buynow = findViewById(R.id.buyBtn);
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent2 = new Intent(ProductDetailPage.this,MainActivity.class);
                Bundle mybundle = new Bundle();
                mybundle.putString("IDSP2",productId);
                myintent2.putExtra("IDSPP2",mybundle);
                startActivity(myintent2,mybundle);
            }
        });


        closeDetail = findViewById(R.id.closeBtn);
        closeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetailPage.this, home.class));
            }
        });

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
                    textViewPrice.setText(String.valueOf(value) + " VNĐ");
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

//        -----------<  ADD TO CART  >------------
        cartReference = FirebaseDatabase.getInstance().getReference("Cart");
        productDetail = FirebaseDatabase.getInstance().getReference("product_detail");

        addToCart = findViewById(R.id.addToCart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCart();
            }
        });
    }
//  ---------------<    ENDING ONCRERATE()    >---------------

    private void AddToCart() {
        // Lấy thông tin sản phẩm từ Firebase
        productDetail.child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Chuyển đổi dataSnapshot thành đối tượng ProductItemDetail
                ProductItemDetail product = dataSnapshot.getValue(ProductItemDetail.class);

                // Thêm sản phẩm vào giỏ hàng trên Firebase
                addProductToFirebaseCart(product);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Lỗi đọc dữ liệu: " + databaseError.getMessage());
            }
        });
    }

    private void addProductToFirebaseCart(ProductItemDetail product) {
        // Tạo một CartItem từ thông tin sản phẩm
        int quantity = Integer.parseInt(quantityNum.getText().toString());
        int price = product.getPrice();

        if (quantity > 1) {
            price *= quantity;
        }
        CartItem cartItem = new CartItem(product.getId(), product.getName(), price, product.getImage(), product.getQuantity(), product.getSize(), product.isCheckbox(), product.getMasp());

        // Chuyển đổi CartItem thành Map
        Map<String, Object> cartItemValues = cartItem.toMap();

        // Thực hiện cập nhật trực tiếp trên node Cart
        DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference("Cart");
        cartReference.child(productId).setValue(cartItemValues)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Product added to cart successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to add product to cart.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateQuantityInFirebaseCart(DataSnapshot dataSnapshot) {
        // Lấy key của node chứa thông tin sản phẩm trong giỏ hàng
        String cartItemId = dataSnapshot.getChildren().iterator().next().getKey();

        // Lấy số lượng hiện tại trong giỏ hàng
        CartItem existingCartItem = dataSnapshot.getChildren().iterator().next().getValue(CartItem.class);
        int currentQuantity = existingCartItem.getQuantity();

        // Cập nhật số lượng trong giỏ hàng
        int newQuantity = currentQuantity + Integer.parseInt(quantityNum.getText().toString());
        cartReference.child(cartItemId).child("quantity").setValue(newQuantity)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProductDetailPage.this, "Product quantity updated in cart successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductDetailPage.this, "Failed to update product quantity in cart.", Toast.LENGTH_SHORT).show();
                    }
                });
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
        saveSize(selectedSize);
    }

    private void resetButtonColors() {
        buttonS.setBackgroundResource(R.drawable.button_default);
        buttonM.setBackgroundResource(R.drawable.button_default);
        buttonL.setBackgroundResource(R.drawable.button_default);
    }

//  ---------------<  Lưu số lượng và size vào Firebase  >----------------
    private void saveQuantity(int quantity) {
        productDetail.child(productId).child("quantity").setValue(quantity)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Quantity updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to update quantity.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveSize(String sizeSelected) {
        Map<String, Object> updateSize = new HashMap<>();
        updateSize.put("size", sizeSelected);

        // Cập nhật giá trị của thuộc tính "size" trong Firebase
        productDetail.child(productId).updateChildren(updateSize)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Size updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error updating size: " + e.getMessage());
                        Toast.makeText(getApplicationContext(), "Failed to update size", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
