package com.example.app_coffee_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class cartadapter extends BaseAdapter {

    Context context;
    ArrayList<item> itemArrayList;
    private boolean isAllChecked = false;
    public cartadapter(Context context, ArrayList<item> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;
    }
    @Override
    public int getCount() {
        return itemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler{
        public TextView txttensp,txtgiasp,txtsizesp;
        public ImageView imgsp;
        public Button btnminus,btnvalues,btnplus;
        public CheckBox cbitem;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHoler viewHoler=null;
        if(view ==null){
            viewHoler = new ViewHoler();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dongitem,null);
            viewHoler.txttensp= view.findViewById(R.id.txtnamespken);
            viewHoler.txtgiasp=view.findViewById(R.id.txtpricespken);
            viewHoler.imgsp=view.findViewById(R.id.imgspken);
            viewHoler.btnminus=view.findViewById(R.id.btnminusken);
            viewHoler.btnplus=view.findViewById(R.id.btnplusken);
            viewHoler.btnvalues=view.findViewById(R.id.numner);
            viewHoler.cbitem=view.findViewById(R.id.CBitemken);
            viewHoler.txtsizesp=view.findViewById(R.id.txtsizeken);
            view.setTag(viewHoler);
        }
        else {
            viewHoler=(ViewHoler)view.getTag();
        }


        item Item = (item) getItem(i);
        viewHoler.cbitem.setOnCheckedChangeListener(null);
        viewHoler.cbitem.setTag(i);
        viewHoler.cbitem.setChecked(Item.getCheckbox());
        viewHoler.txttensp.setText(Item.getName());
        viewHoler.txtgiasp.setText(Item.getPrice()+"");
        Picasso.get().load(Item.getImage()).into(viewHoler.imgsp);
        viewHoler.btnvalues.setText(Item.getQuantity()+"");
        viewHoler.txtsizesp.setText(Item.getSize());
        viewHoler.cbitem.setChecked(Item.getCheckbox());

        viewHoler.cbitem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag();
                // Update the checkbox status in the local list
                Item.setCheckbox(isChecked);
                // Update the checkbox status in the Firebase database
                updateCheckboxStatus(Integer.parseInt(Item.getMasp()), isChecked);

                // Check if all checkboxes in the list are checked
                boolean isAllChecked = true;
                for (item currentItem : home.mangsp) {
                    if (!currentItem.getCheckbox()) {
                        isAllChecked = false;
                        break;
                    }
                }

                // Update cbxtotal accordingly, but set userChecked to false to indicate programmatic change
                Cart.userChecked = false;
                Cart.cbxtotal.setChecked(isAllChecked);

                // Calculate the total price
                Cart.calculateTotalPrice();
            }
        });

        int sl= Integer.parseInt(viewHoler.btnvalues.getText().toString());
        if(sl<=1){
            viewHoler.btnminus.setVisibility(view.INVISIBLE);
        }else{
            viewHoler.btnminus.setVisibility(view.VISIBLE);
        }
        ViewHoler finalViewHoler = viewHoler;
        viewHoler.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHoler.btnvalues.getText().toString())+1;
                int slht= home.mangsp.get(i).getQuantity();
                int giaht= home.mangsp.get(i).getPrice();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Cart");
                Item.setQuantity(slmoinhat);


                //Profile.mangsp.get(i).setSoluong(slmoinhat);
                int giamoinhat=(giaht*slmoinhat)/slht;
                Item.setPrice(giamoinhat);
                myRef.child(String.valueOf(Item.getMasp())).updateChildren(Item.toMap());
//                Profile.mangsp.get(i).setGia(giamoinhat);
//
//                finalViewHoler.txtgiasp.setText(giamoinhat+"");
//                Cart.EvenUtil();
                if(slmoinhat>1){
                    finalViewHoler.btnminus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnvalues.setText(String.valueOf(slmoinhat));
                }
                Cart.calculateTotalPrice();
            }
        });

        ViewHoler finalViewHoler1 = viewHoler;
        viewHoler.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHoler1.btnvalues.getText().toString())-1;
                int slht= home.mangsp.get(i).getQuantity();
                int giaht= home.mangsp.get(i).getPrice();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Cart");
                Item.setQuantity(slmoinhat);
//                Profile.mangsp.get(i).setSoluong(slmoinhat);
                int giamoinhat=(giaht*slmoinhat)/slht;
                Item.setPrice(giamoinhat);
                myRef.child(String.valueOf(Item.getMasp())).updateChildren(Item.toMap());
//                Profile.mangsp.get(i).setGia(giamoinhat);

//                finalViewHoler1.txtgiasp.setText(giamoinhat+"");
//                Cart.EvenUtil();
                if(slmoinhat<2){
                    finalViewHoler1.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHoler1.btnvalues.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHoler1.btnminus.setVisibility(View.VISIBLE);
                    finalViewHoler1.btnvalues.setText(String.valueOf(slmoinhat));
                }
                Cart.calculateTotalPrice();
            }
        });
        return view;
    }
    protected void updateCheckboxStatus(int itemId, boolean isChecked) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Cart").child(String.valueOf(itemId));

        Map<String, Object> update = new HashMap<>();
        update.put("checkbox", isChecked);

        myRef.updateChildren(update);
    }
    public void checkSelectAllCheckbox() {
        boolean allChecked = true;
        for (item currentItem : itemArrayList) {
            if (!currentItem.getCheckbox()) {
                allChecked = false;
                break;
            }
        }

        // Notify the Cart activity to update the state of the "Select All" checkbox
        if (context instanceof Cart) {
            ((Cart) context).updateSelectAllCheckbox(allChecked);
        }
    }
}
