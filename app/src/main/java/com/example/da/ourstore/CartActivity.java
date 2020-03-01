package com.example.da.ourstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity  extends AppCompatActivity implements View.OnClickListener{
    ArrayList<HashMap<String,Integer>> arrayList;
    CustomAdapter customAdapt;
    ListView board;
    Button backButton, buyButton;
    TextView totalPrice;
    int size = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);
        backButton = findViewById(R.id.buttonBack);
        buyButton = findViewById(R.id.buttonFinalize);
        board = findViewById(R.id.table);
        totalPrice  = findViewById(R.id.textViewTotal);
        backButton.setOnClickListener(this);
        buyButton.setOnClickListener(this);

        setTable();
        try {
            int[] images = getIntent().getExtras().getIntArray("imgs");
            int[] prices = getIntent().getExtras().getIntArray("prices");

            size = prices.length;
            addNewItems(images, prices);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "The Cart Table is Empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void setTable(){
        arrayList=new ArrayList<>();
    }

    private void addNewItems(int[] images, int[] prices){
        for(int i = 0; i < size; i++){
            HashMap<String,Integer> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("image", images[i]);// put image as the key of images[i]
            hashMap.put("price", prices[i]);
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        customAdapt = new CustomAdapter(this, arrayList, totalPrice, buyButton);//sets a custon adapter on this
        //activity and use the data inside arrayList to fill the rows of the listView and add the totalPrice and buyButton in the
        //constructor because each click on a remove imageButton will update the textView and enabling/disabling the button respectively
        board.setAdapter(customAdapt);//sets the adapter for the listView: board
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonBack:
                    Intent i = new Intent(CartActivity.this, MainActivity.class);
                    i.setFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    if(customAdapt.getArrayOfDeletedImages().size() > 0)
                        i.putExtra("toReset", customAdapt.getArrayOfDeletedImages());
                    startActivity(i);
                    finish();
                    break;
             case R.id.buttonFinalize:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

                alertDialog.setTitle("Pay and Exit?");

                alertDialog.setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {

                        Toast.makeText(getBaseContext(), "Thank U", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(CartActivity.this, MainActivity.class);
                        i.setFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        i.putExtra("Close", true);

                        startActivity(i);
                        finish();

                    }
                });
                alertDialog.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                            Intent i = new Intent(CartActivity.this, MainActivity.class);
                            i.setFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                i.putExtra("toReset", customAdapt.getArrayOfDeletedImages());
                            startActivity(i);
                            finish();
                    }
                });

                alertDialog.setCancelable(false);
                alertDialog.show();
                break;
        }
    }

}

/*
README FILE
============
Assignment Android 4
shaysha6@gmail.com , ID 203245816 Shaye Elbaz

Second Year
Computer Science Department- Ashkelon Academic College
================================================

Assignment:
	Build a simple application for the image of online purchases.

Main Goals:
	Write the app for shopping the next way.

Files:
	activity_main.xml
		Write in XML, describes the MainActivity UI.

	gridview_layout
		Write in XML, describes the item on activity_main's GridView.

	MainActivity.java
		Extends AppCompatActivity(https://developer.android.com/reference/android/support/v7/app/AppCompatActivity)
		Responds to the user touches on the items of the gridView()(https://developer.android.com/guide/topics/ui/layout/gridview#java) to add the clicked on items [each item added only once] to the cart by click on Shopping Cart button
		Shows a grid of photos of shirts and their prices using gridview_layout for each item on the grid.

	cart_layout.xml
		Write in XML, describes the CartActivity UI.

	list_layout.xml
		Write in XML, describes the row of the list on cart_layout's listView .

	CustomAdapter.java
		Extends BaseAdapter(https://developer.android.com/reference/android/widget/BaseAdapter)
		set the default methods of the abstract class which adapt from arraylist info to items on the list of CartActivity

	CartActivity.java
		Extends AppCompatActivity
		show a list of an imageView, a textView and an ImageButton via a custon adapter
		Responds to the user touches on the buttons: back to store, finalize aquisition.
		Also remove a row from the list by click on the imageButton of that row and update the textView which shows the total sum.
		On click of back: it'll move to MainActivity.
		On click finalize aquisition : it'll open alertDialog(https://developer.android.com/reference/android/app/AlertDialog) to pay,
		by click buy it'll show a toast 'Thank u' and move to MainActivity through an intent to close the app,
		by click on 'back to menu' on the alertDialog or 'back to store' on the activity you'll move to MainActivity with an extra 'toReset'
		which holds the info for updating the changes in MainSActivity.

	strings.xml
		Write in XML, hold all the strings(texts) of the UI.

	AndroidManifest.xml
		write in XML, hold all the info about the app [including the intents] to run it.

Running the program from the Android Studio:
	First you extract the zip file
	Second you need to click on the top-bar File -> New -> Import Project
	then find the extracted folder, click to open the sub-folders and select the Gradle
	click on OK to build the Gradle


The operation:
	After running the application you will see a window with a grid of items, each item has image of shoe and its price.
	Click on an item to enable the button 'shopping cart' and to add the shoe's price to the sum of items so far.
	Click on the 'shopping cart to move the cart window with the data that describes the list of selected shirts.
    Click on the 'finalize aquisition' button if the user want to pay. An aler dialog will show up which is a small window with text and 2 buttons.
    Click on the 'back to menu' button to back to the first window with the changes the user's did in the list.
    Click on the 'buy' button to pay all the selected items and exit.

Algorithms:
	None.
 */
