package com.example.app_coffee_order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
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
import java.util.Iterator;

public class Cart extends AppCompatActivity {


    static CheckBox cbxtotal;
    ListView lvgiohang;
    Button btnthem,btnimgprofile;
    static TextView txttongtien;
    static boolean userChecked = true;
    static ImageButton imgthungrac;
    static cartadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_main);
        btnthem=(Button) findViewById(R.id.btnpay);

        Anhxa();

        btnimgprofile=(Button) findViewById(R.id.btnuser);
        txttongtien= (TextView) findViewById(R.id.txtmoneyken);
        imgthungrac=(ImageButton) findViewById(R.id.binken);
        imgthungrac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCheckedItems();
            }
        });
        cbxtotal=(CheckBox) findViewById(R.id.cbxall);
        cbxtotal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (userChecked) {
                    // Cập nhật tất cả các ô kiểm trong danh sách
                    for (item currentItem : home.mangsp) {
                        currentItem.setCheckbox(isChecked);
                        adapter.updateCheckboxStatus(Integer.parseInt(currentItem.getMasp()), isChecked);
                    }
                    // Cập nhật adapter để làm mới giao diện người dùng
                    adapter.notifyDataSetChanged();
                    // Tính toán tổng giá
                    calculateTotalPrice();
                }
                // Reset biến sau khi cập nhật xong
                userChecked = true;
            }
        });
        btnimgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this, Profile.class);
                startActivity(intent);
            }
        });
//        calculateTotalPrice();
    }

    private void Anhxa() {
        lvgiohang = (ListView) findViewById(R.id.listviewitem);
        home.mangsp=new ArrayList<>();
        adapter = new cartadapter(Cart.this, home.mangsp);
        getListUserData();
        lvgiohang.setAdapter(adapter);
    }
    private void getListUserData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Cart");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(home.mangsp!=null){
                    home.mangsp.clear();
                }
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    item Item =dataSnapshot.getValue(item.class);
                    home.mangsp.add(Item);
                }
                adapter.notifyDataSetChanged();
                calculateTotalPrice();
                adapter.checkSelectAllCheckbox();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Cart.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isAlertDialogShown = false;

    private void deleteCheckedItems() {
        int itemsToDelete = 0;

        // Count the items to be deleted
        for (item currentItem : home.mangsp) {
            if (currentItem.getCheckbox()) {
                itemsToDelete++;
            }
        }

        // Show the AlertDialog only if there are items to delete
        if (itemsToDelete > 0 && !isAlertDialogShown) {
            new AlertDialog.Builder(this).setTitle(getString(R.string.app_name)).setMessage("Bạn chắc chắn có xóa không?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Reset the flag and counter
                            isAlertDialogShown = false;
                            int deletedItems = 0;

                            Iterator<item> iterator = home.mangsp.iterator();
                            while (iterator.hasNext()) {
                                item currentItem = iterator.next();
                                if (currentItem.getCheckbox()) {
                                    // Delete the item from Firebase
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Cart").child(String.valueOf(currentItem.getMasp()));

                                    myRef.removeValue();

                                    // Remove the item from the list
                                    iterator.remove();
                                    deletedItems++;
                                }
                            }

                            // Show a single Toast indicating the number of deleted items
                            if (deletedItems > 0) {
                                Toast.makeText(Cart.this, deletedItems + " items deleted", Toast.LENGTH_SHORT).show();
                            }

                            // After deletion, recalculate the total price
                            calculateTotalPrice();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Reset the flag if the user cancels the deletion
                            isAlertDialogShown = false;
                        }
                    })
                    .show();

            // Set the flag to prevent showing the dialog multiple times
            isAlertDialogShown = true;
        }
    }


    protected static void calculateTotalPrice() {
        int totalPrice = 0;

        for (item Item : home.mangsp) {
            if (Item.getCheckbox()) {
                totalPrice += Item.getPrice();
            }
        }

        // Update the TextView displaying the total price
        txttongtien.setText(String.valueOf(totalPrice));
    }
    public void updateSelectAllCheckbox(boolean isChecked) {
        // Set userChecked to false to indicate programmatic change
        userChecked = false;
        cbxtotal.setChecked(isChecked);
        // Reset the flag after updating
        userChecked = true;
    }


}