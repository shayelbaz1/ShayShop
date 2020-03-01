/**
 * Android 4 – Shopping App

 Student’s Information
 Shay Elbaz, ID 203245816, Email: shaysha6@gmail.com
 Second Year Computer Science Department- Ashkelon Academic College

 Assignment
 Build a Shopping app, that calculate the amount of cost to pay, and remember the chosen products.

 Goal
 Use our new knowledge in Android Studio and implement a basic App which receive a chosen products,
 and presenting the Cart, and giving the option to buy and pay.

 Classes
 * MainActivity - The Front Page contains all the products.
 * CustomAdapter - Contain the list properties in cart.
 * CartActivity - The Cart Page - Checkout Page.

 Running the program
 1. Open Android Studio
 2. Open the Project.
 3. Run The App with a virtual machine or your connected phone.

 Algorithms
 No algorithems were used in this assignment.
 * @author shayelbaz
 */
package com.example.da.ourstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView  total;
    GridView items;
    Button toCartButton;
    int[] pricesToCart;
    int[] imgsToCart;
    int size = 0;
    boolean isNewIntent = false;
    boolean updatedSize = false;

    int[] amounts = new int [12];// automatically initialized with 12 zeros
    // Array of strings storing prices
    String[] prices = new String[] {
            "20$",
            "25$",
            "10$",
            "15$",
            "17$",
            "15$",
            "20$",
            "25$",
            "25$",
            "29$",
            "7$",
            "99$"

    };

    // Array of integers points to images stored in /res/mipmap
    int[] shirts = new int[]{
            R.mipmap.shirt1,
            R.mipmap.shirt2,
            R.mipmap.shirt3,
            R.mipmap.shirt4,
            R.mipmap.shirt5,
            R.mipmap.shirt6,
            R.mipmap.shirt7,
            R.mipmap.shirt8,
            R.mipmap.shirt9,
            R.mipmap.shirt10,
            R.mipmap.shirt11,
            R.mipmap.shirt12
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = findViewById(R.id.gridview);
        total= findViewById(R.id.textViewTotal);
        toCartButton = findViewById(R.id.buttonCart);
        toCartButton.setOnClickListener(this);
        Toast.makeText(this, "welcome  ", Toast.LENGTH_SHORT).show();
        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if(amounts[position] == 0){
                    Toast.makeText(getApplicationContext(),
                            ((TextView) v.findViewById(R.id.txt))
                                    .getText() + ", added to the cart", Toast.LENGTH_SHORT).show();
                    String str = prices[position].substring(0, prices[position].length() -1);
                    int price = Integer.parseInt(str);
                    size++;// to determine the size of the arrays to move to CartActivity
                    ++amounts[position];
                    str = total.getText().toString().substring(0, total.getText().toString().length() -1);
                    int newTotal = Integer.parseInt(str) + price;
                    total.setText(newTotal+"$");
                    if(isNewIntent)
                        updatedSize = true;
                    toCartButton.setEnabled(true);
                }

            }
        });

        // need to add listener
        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<prices.length;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", prices[i]);
            hm.put("shirt", Integer.toString(shirts[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "shirt","txt"};

        // Ids of views in listview_layout
        int[] to = { R.id.shirtImg,R.id.txt};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.gridview_layout, from, to);

        // Getting a reference to gridview of MainActivity
        GridView gridView = findViewById(R.id.gridview);

        // Setting an adapter containing images to the gridview
        gridView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonCart:

                Intent i = new Intent(MainActivity.this, CartActivity.class);

                if(makeArraysToCart()){// no need for If statement, but that's O.K.
                    i.putExtra("imgs", imgsToCart);
                    i.putExtra("prices", pricesToCart);
                }

                startActivity(i);
                break;
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        isNewIntent = true;
        try {
            if (intent.getExtras().getBoolean("Close")) {
                finish();
            }
        } catch (Exception e) {

        }
        try {
            ArrayList<Integer> toResetAmount = intent.getExtras().getIntegerArrayList("toReset");

            int newTotal = Integer.parseInt(total.getText().toString().substring(0, total.getText().toString().length() - 1));
            int j = 0;
            boolean out;
            if(toResetAmount.size() == size){
                toCartButton.setEnabled(false);
            }
            while (j < toResetAmount.size()) {
                out = false;
                for (int i = 0; i < shirts.length && !out; i++) {
                    if (shirts[i] == toResetAmount.get(j).intValue()) {
                        amounts[i] = 0;
                        String str = prices[i].substring(0, prices[i].length() - 1);
                        int price = Integer.parseInt(str);
                        size--;
                        newTotal = newTotal - price;
                        out = true;
                        j++;
                    }
                }
            }
            total.setText(newTotal + "$");
        } catch (Exception e) {
        }
    }
    private boolean makeArraysToCart(){
        if(size > 0 && !updatedSize || updatedSize){
            pricesToCart = new int[size];
            imgsToCart = new int[size];
            int j =0;
            for(int i = 0; i< shirts.length; i++){
                if(amounts[i] > 0) {
                    imgsToCart[j] = shirts[i];
                    pricesToCart[j] = Integer.parseInt(prices[i].substring(0, prices[i].length() -1));
                    j++;
                }
            }
            return true;
        }
        return false;
    }
}
