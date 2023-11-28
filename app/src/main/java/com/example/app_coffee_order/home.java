package com.example.app_coffee_order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {
    public static ArrayList<item> mangsp;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    List<Product> productListOld;

    EditText searchEditText;

    Button btncart,btnprofile,btndetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btncart = findViewById(R.id.btncart);
        btnprofile = findViewById(R.id.btnuser);
        btndetail= findViewById(R.id.btnlove);
        btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProductDetail = new Intent(home.this, product_detail.class);
                startActivity(toProductDetail);
            }
        });
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, Cart.class);
                startActivity(intent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, Profile.class);
                startActivity(intent);
            }
        });
        AnhXa();

    }
    private void AnhXa() {
        recyclerView = findViewById(R.id.recyclerView);
        productList = new ArrayList<>();
        productListOld = new ArrayList<>();

        searchEditText = findViewById(R.id.editTextSearch);
        setupSearchListener();
        getListProductData();

        productAdapter = new ProductAdapter(this, productList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(productAdapter);
    }

    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed as the text is changing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString();
                searchProduct(searchText);
                setUpClearIcon(searchEditText);
            }
        });
    }

    private void searchProduct(String searchText) {
        List<Product> filteredList = new ArrayList<>();

        for (Product product : productListOld) {
            if (product.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(product);
            }
        }

        // Update the adapter with the filtered list
        productAdapter.setData(filteredList);
        productAdapter.notifyDataSetChanged();
    }

    // Phương thức để thiết lập biểu tượng xóa
    @SuppressLint("ClickableViewAccessibility")
    private void setUpClearIcon(EditText editText) {
        if (editText.getText().length() > 0) {
            // Show the clear icon
            showClearIcon(editText);

            // Set an OnClickListener for the clear icon
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0);
            editText.setOnTouchListener((v, event) -> {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Clear the text when the clear icon is pressed
                        editText.setText("");
                        return true;
                    }
                }
                return false;
            });
        } else {
            // Show the search icon
            showSearchIcon(editText);
        }
    }

    // Hiển thị biểu tượng xóa
    private void showClearIcon(EditText editText) {
        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0);
    }

    // Hiển thị biểu tượng tìm kiếm
    private void showSearchIcon(EditText editText) {
        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.search, 0);
    }

    private void getListProductData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("product_detail");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(home.this, "Data loaded from Firebase", Toast.LENGTH_SHORT).show();
                if (productListOld != null) {
                    productListOld.clear();
                }
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    try {
                        // Code xử lý dữ liệu ở đây
                        Product product = dataSnapshot.getValue(Product.class);
                        productListOld.add(product);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                productAdapter.setData(productListOld);
                productAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(home.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}