package com.example.da.ourstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater _inflater;
    private List<HashMap<String,Integer>> items; // the list of items to be displayed
    private Context _context;
    private int _position;
    private ArrayList<Integer> deletedImages = new ArrayList<>();
    private TextView totalPrice;
    private Button buy;

    public CustomAdapter(Context context, List<HashMap<String,Integer>> shoesItems, TextView totalTextView, Button buyButtton){
        _inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = shoesItems;
        _context  = context;
        totalPrice = totalTextView;
        totalPrice.setText("Total: "+getSum()+"$");
        buy = buyButtton;
    }
    public int getSum(){
        int priceSum = 0;
        // TODO Auto-generated method stub
        for(int i= 0; i < items.size(); i++){
            priceSum += items.get(i).get("price");
        }
        return priceSum;
    }
    public int getCount() {
        // TODO Auto-generated method stub

        return items.size();
    }
    // Item at a specific location
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position).get("image");
    }
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public ArrayList<Integer> getArrayOfDeletedImages(){
        return deletedImages;
    }

    public void setArrayOfDeletedImages(int img){
        deletedImages.add(img);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View row = _inflater.inflate(R.layout.list_layout, parent, false);
        // make/get id's of text view and button views for the items
        _position = position;
        ImageView img =  row.findViewById(R.id.imageViewItem);
        TextView txtPrice = row.findViewById(R.id.textViewPrice);
        ImageButton btnDelete = row.findViewById(R.id.imageButton);

        img.setImageResource(items.get(position).get("image"));
        txtPrice.setText(items.get(position).get("price") + "");
        btnDelete.setTag(position);
        btnDelete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(v.getId() == R.id.imageButton){

                    setArrayOfDeletedImages(items.get(Integer.parseInt(v.getTag().toString())).get("image"));

                    items.remove(Integer.parseInt(v.getTag().toString()));
                    totalPrice.setText("Total: "+getSum()+"$");
                    if(getCount() == 0){
                        buy.setEnabled(false);
                    }
                    notifyDataSetChanged();// important, notify to update the adapter
                }
            }
        });
        return row;
    }
}