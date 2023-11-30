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

public class lscfAdapter extends RecyclerView.Adapter<lscfAdapter.OrderItemViewHolder>{
    private Context context;
    private  List<OrderItem> morderItemsList;

    public lscfAdapter(Context context,List<OrderItem> morderItemsList) {
        this.context = context;
        this.morderItemsList = morderItemsList;

    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listviewitem1, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        OrderItem orderItem = morderItemsList.get(position);
        if (orderItem == null){
            return;
        }
        Picasso.get().load(orderItem.getImageResource()).into(holder.imgcf1);
        holder.tvtencf1.setText("   "+orderItem.getProductName());
        holder.tvdongia1.setText("   "+orderItem.getProductPrice() + " VNĐ");
        holder.tvkhachhang1.setText("Mr :"+orderItem.getDeliveryPerson());
        holder.tvdiachi1.setText("Add : "+orderItem.getDeliveryAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 Lấy ID từ Firebase và hiển thị nó
                String oderItemID = morderItemsList.get(position).getOrderId().toString();
                Toast.makeText(context, oderItemID,Toast.LENGTH_SHORT).show();

                Toast.makeText(view.getContext(), "ID: " +oderItemID, Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy ID từ Firebase và hiển thị nó
                String oderItemID = morderItemsList.get(position).getOrderId().toString();

                // Create an Intent using the correct context
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("ORDER_ID", oderItemID);
                context.startActivity(intent);

                Toast.makeText(context, oderItemID, Toast.LENGTH_SHORT).show();
                Toast.makeText(view.getContext(), "ID: " + oderItemID, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (morderItemsList != null) {
            return morderItemsList.size();
        }
        return 0;
    }

    public  class OrderItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgcf1;
        private TextView tvtencf1, tvdongia1,tvkhachhang1, tvdiachi1;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgcf1 = itemView.findViewById(R.id.img_cf1);
            tvtencf1 = itemView.findViewById(R.id.tvtencf1);
            tvdongia1 = itemView.findViewById(R.id.tvgia1);
            tvkhachhang1 = itemView.findViewById(R.id.tvMr1);
            tvdiachi1 = itemView.findViewById(R.id.tvAdd1);

        }
    }

    public void setData(List<OrderItem> morderItemsList) {
        this.morderItemsList = morderItemsList;
    }
}
