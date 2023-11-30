package com.example.app_coffee_order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class lscfAdapter2 extends RecyclerView.Adapter<lscfAdapter2.OrderItemViewHolder>{
    private Context context; // Add context variable
    private List<OrderItem> morderItemsList2;

    public lscfAdapter2(Context context, List<OrderItem> morderItemsList2) {
        this.context = context;
        this.morderItemsList2 = morderItemsList2;
    }


    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem2, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        OrderItem orderItem = morderItemsList2.get(position);
        if (orderItem == null){
            return;
        }
        Picasso.get().load(orderItem.getImageResource()).into(holder.imgcf2);
        holder.tvtencf2.setText("   "+orderItem.getProductName());
        holder.tvdongia2.setText("   "+orderItem.getProductPrice() + " VNĐ");
        holder.tvtrangthai2.setText(" "+orderItem.getPaymentStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderItemID = morderItemsList2.get(position).getOrderId().toString();
                // Create an Intent using the correct context (assuming 'context' is a member variable)
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("ORDER_ID", orderItemID);
                context.startActivity(intent);
                Toast.makeText(view.getContext(), "Clicked on order ID: " + orderItemID, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (morderItemsList2 != null) {
            return morderItemsList2.size();
        }
        return 0;
    }

    public  class OrderItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgcf2;
        private TextView tvtencf2, tvdongia2, tvtrangthai2;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgcf2 = itemView.findViewById(R.id.img_cf2);
            tvtencf2 = itemView.findViewById(R.id.tvtencf2);
            tvdongia2 = itemView.findViewById(R.id.tvgia2);
            tvtrangthai2 = itemView.findViewById(R.id.tvthanhtoan2);

        }
    }
    public void setData(List<OrderItem> morderItemsList2) {
        this.morderItemsList2 = morderItemsList2;
    }

}