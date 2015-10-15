package com.example.fasoos_challenge;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


//ProductDetailClass - used to populate the details and show to customer on selection of particular product in list view -Arun Sairam-Sep 27

public class ProductDetailClass extends Activity {

	ArrayList<HashMap<String, String>> menu;
	String itemName;
	ImageView spiceImage;
	TextView productName, price, isVeg, desc,category;
	RatingBar rBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_layout);
		Intent i = getIntent();
		itemName = i.getStringExtra("productName");
		menu = (ArrayList<HashMap<String, String>>) i
				.getSerializableExtra("MenuList");
		onPageLoad();
		onBackButtonPress();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_share:
            // share action
        	onShareClick();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	
	//Method to load page and fill textview and imageviews dynamically -Arun Sairam-Sep27-Starts
	public void onPageLoad() {

		productName = (TextView) findViewById(R.id.textView_product);
		price = (TextView) findViewById(R.id.textView_price);
		desc = (TextView) findViewById(R.id.textView_description);
		isVeg = (TextView) findViewById(R.id.textView_isveg);
		rBar = (RatingBar) findViewById(R.id.ratingBar1);
		category=(TextView) findViewById(R.id.textView_categ);
		spiceImage = (ImageView) findViewById(R.id.imageView_spice);

		for (int i = 0; i < menu.size(); i++) {
			if (menu.get(i).get("name").toString()
					.contains(itemName.toString())) {

				productName.setText(menu.get(i).get("name").toString());
				price.setText(menu.get(i).get("price").toString());
				desc.setText(menu.get(i).get("description").toString());
				isVeg.setText(menu.get(i).get("is_veg").toString());
				category.setText(menu.get(i).get("category").toString());
				rBar.setRating(Float.valueOf(menu.get(i).get("rating")
						.toString()));
				loadSpiceImage(spiceImage, menu.get(i).get("spice_meter")
						.toString());
				break;
			}
		}

	}
	//Method to load page and fill textview and imageviews dynamically -Arun Sairam-Sep27-Ends
	
	
	//Method to share the product name and description with other applications-Arun Sairam-Sep27-Starts
	public void onShareClick(){
		String prodName="";
		String desc="";
		
		for (int i = 0; i < menu.size(); i++) {
			if (menu.get(i).get("name").toString()
					.contains(itemName.toString())) {
				prodName=menu.get(i).get("name").toString();
				desc=menu.get(i).get("description").toString();
				break;
			}
		}
		
		StringBuilder sb=new StringBuilder();
		sb.append("Hi, try this dish,Name - "+prodName+", Description- "+desc+" and kindly provide your feedback in Fasoos app, if not having, download it, its amazing!!");
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	}
	//Method to share the product name and description with other applications-Arun Sairam-Sep27-Ends
	
	
	//Method to finish the activity and move to previous one-Arun Sairam-Sep27-Starts
	public void onBackButtonPress(){
		((Button) findViewById(R.id.button_back))
		.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	//Method to finish the activity and move to previous one-Arun Sairam-Sep27-Ends

	
	//Method to auto load imageview based on the number of spice_meter fetched for the product-Arun Sairam-Sep27-Starts
	public void loadSpiceImage(View view, String spice_meter) {

		Log.i("PRODUCT LIST", "spice meter isL"+spice_meter);
		if (Integer.parseInt(spice_meter) == 1) {
			view.setBackgroundResource(R.drawable.chilli1);
		} else if (Integer.parseInt(spice_meter) == 2) {
			view.setBackgroundResource(R.drawable.chilli2);
		} else if (Integer.parseInt(spice_meter) == 3) {
			view.setBackgroundResource(R.drawable.chilli3);
		} else if (Integer.parseInt(spice_meter) == 4) {
			view.setBackgroundResource(R.drawable.chilli4);
		} else if (Integer.parseInt(spice_meter) == 5) {
			view.setBackgroundResource(R.drawable.chilli5);
		}

	}
	//Method to auto load imageview based on the number of spice_meter fetched for the product-Arun Sairam-Sep27-Ends

}
