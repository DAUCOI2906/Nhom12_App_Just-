package com.example.app_coffee_order;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends Activity {

    private RecyclerView orderRecyclerView;
    private OrderDetailAdapter orderAdapter;
    private List<OrderItem> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        // Lấy ORDER_ID từ Intent
        String orderId = getIntent().getStringExtra("ORDER_ID");

        //String orderId = "15";

        getOrderItemsForOrderId(orderId);

        initializeViews();

    }

    private void initializeViews() {
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        orderItems = new ArrayList<>();
        orderAdapter = new OrderDetailAdapter(this, orderItems);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setAdapter(orderAdapter);
    }

    private void getOrderItemsForOrderId(String orderId) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("OrderDetail");
        Query query = myRef.orderByKey().equalTo(orderId);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (orderItems != null) {
                    orderItems.clear();
                }

                // Loop through each child snapshot
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    try {
                        OrderItem orderItem = childSnapshot.getValue(OrderItem.class);
                        orderItems.add(orderItem);
                    } catch (Exception e) {
                        Toast.makeText(OrderDetailActivity.this, orderId, Toast.LENGTH_SHORT).show();
                    }
                }

                // Notify the adapter after updating the data
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrderDetailActivity.this, "Error fetching order data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
