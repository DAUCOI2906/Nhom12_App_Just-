package com.example.app_coffee_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private Context context;
    private List<OrderItem> orderItems;

    public OrderDetailAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);

        // Load hình ảnh từ URL bằng Picasso
        try {
            Picasso.get().load(orderItem.getImageResource()).into(holder.productImage);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error loading image", Toast.LENGTH_SHORT).show();
        }

        holder.productName.setText(orderItem.getProductName());
        holder.productPrice.setText(String.valueOf(orderItem.getProductPrice()) + " VNĐ"); // Chuyển giá trị thành chuỗi
        holder.productQuantity.setText("X" + orderItem.getProductQuantity());
        holder.deliveryAddress.setText(orderItem.getDeliveryAddress());
        holder.deliveryPerson.setText(orderItem.getDeliveryPerson());
        holder.deliveryPhoneNumber.setText(orderItem.getDeliveryPhoneNumber());
        holder.orderStatus.setText(orderItem.getOrderStatus());
        double totalPrice = orderItem.getProductPrice() * orderItem.getProductQuantity();
        holder.productTotal.setText(String.valueOf(totalPrice) + " VNĐ");
        holder.totalPayment.setText(String.valueOf(totalPrice) + " VNĐ");
        holder.paymentStatus.setText(orderItem.getPaymentStatus());
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView deliveryAddress;
        TextView deliveryPerson;
        TextView deliveryPhoneNumber;
        TextView orderStatus;
        TextView productName;
        TextView productPrice;
        TextView productQuantity;
        TextView productTotal;
        TextView totalPayment;
        TextView paymentStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            deliveryAddress = itemView.findViewById(R.id.deliveryAddress);
            deliveryPerson = itemView.findViewById(R.id.deliveryPerson);
            deliveryPhoneNumber = itemView.findViewById(R.id.deliveryPhoneNumber);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            productTotal = itemView.findViewById(R.id.productTotal);
            totalPayment = itemView.findViewById(R.id.totalPayment);
            paymentStatus = itemView.findViewById(R.id.paymentStatus);
        }
    }
}
