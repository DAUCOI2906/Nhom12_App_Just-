package com.example.app_coffee_order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = productList.get(position);
        Picasso.get().load(product.getImage()).into(holder.productImage);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice() + " VNĐ");
        // Add other fields as needed

//        Get each item id
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////                 Lấy ID từ Firebase và hiển thị nó
                String productId = productList.get(position).getMasp().toString();
                Toast.makeText(context,productId, Toast.LENGTH_SHORT).show();
                Intent myintent = new Intent(context,ProductDetailPage.class);
                Bundle mybundle = new Bundle();
                mybundle.putString("IDSP",productId);
                myintent.putExtra("IDSPP",mybundle);
                context.startActivity(myintent,mybundle);

                Toast.makeText(v.getContext(), "Product ID: " + productId, Toast.LENGTH_SHORT).show();

                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productName;
        public TextView productPrice;
        // Add other fields as needed

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            // Initialize other fields
        }
    }

    public void setData(List<Product> productList) {
        this.productList = productList;
    }
}
