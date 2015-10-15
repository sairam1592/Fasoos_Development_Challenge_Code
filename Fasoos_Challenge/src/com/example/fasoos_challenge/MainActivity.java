package com.example.fasoos_challenge;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//MainActivity- Main class which based on button click, populates listview from URL and shows the count of items present

public class MainActivity extends Activity {

	// URL to get JSON Array
	private static String url = "http://faasos.0x10.info/api/faasos?type=json&query=list_menu";

	// JSON Node Names
	private static final String TAG_MENU = "menu";
	private static final String TAG_NAME = "name";
	private static final String TAG_IMAGEURL = "image";
	private static final String TAG_CATEGORY = "category";
	private static final String TAG_SPICEMETER = "spice_meter";
	private static final String TAG_DESC = "description";
	private static final String TAG_RATING = "rating";
	private static final String TAG_PRICE = "price";
	private static final String TAG_ISVEG = "is_veg";

	ArrayList<HashMap<String, String>> menu;
	ArrayList<String> details = null;
	ListView lv = null;
	Intent intent;
	TextView menuCount, menuLabel;
	Button butt;
	JSONArray user = null;
	AlertDialog.Builder alert;
	AlertDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		menuLabel = (TextView) findViewById(R.id.textView2);
		menuCount = (TextView) findViewById(R.id.textView_count);
		menuLabel.setVisibility(View.INVISIBLE);
		menuCount.setVisibility(View.INVISIBLE);
		Intent i = getIntent();
		Toast.makeText(MainActivity.this, i.getStringExtra("food_select_page"),
				Toast.LENGTH_LONG).show();
		showAlert();
		menu = new ArrayList<HashMap<String, String>>();

		lv = (ListView) findViewById(R.id.listView1);
		butt=(Button) findViewById(R.id.button1);
		((Button) findViewById(R.id.button1))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// TODO Auto-generated method stub
						fetchButtonOnClick();
					}
				});
		listViewItemSelect(menu);
	}

	//Method to show an alert to click on Fetch List button-Arun Sairam-Sep27-Starts
	public void showAlert(){
		final Context context=this;
		alert = new AlertDialog.Builder(context);
		dialog = alert.create();
		dialog.setTitle("NOTE");
		dialog.setMessage("Kindly click on --Fetch List-- button to retrieve food items!");
		dialog.setButton(Dialog.BUTTON_NEGATIVE, "Close",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
		dialog.show();
	}
	//Method to show an alert to click on Fetch List button-Arun Sairam-Sep27-Ends
	
	//Method to call the asynctask class -Arun Sairam-Sep27-Starts
	public void fetchButtonOnClick() {
		new MyClientTask(url.toString(), MainActivity.this).execute();
	}
	//Method to call the asynctask class -Arun Sairam-Sep27-Ends
	

	//Method to get the selected item from list view and push it to ProductDetailClass Activity-Arun Sairam-Sep27-Starts
	public void listViewItemSelect(final ArrayList<HashMap<String, String>> menu) {

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// String item =(String) (lv.getItemAtPosition(position));
				HashMap<String, String> map = (HashMap<String, String>) lv
						.getItemAtPosition(position);
				String name = map.get(TAG_NAME);
				intent = new Intent(MainActivity.this, ProductDetailClass.class);
				intent.putExtra("productName", name);
				intent.putExtra("MenuList", menu);
				startActivity(intent);
			}
		});
	}
	//Method to get the selected item from list view and push it to ProductDetailClass Activity-Arun Sairam-Sep27-Ends
	
	
	//MyClientTask async task class which perform background operation to retrieve JSON details and populate listview-Arun Sairam-Sep27-Starts
	private class MyClientTask extends AsyncTask<Void, Void, Void> {

		String url;
		private ProgressDialog dialog;
		Context context = null;

		MyClientTask(String myUrl, Context contex) {
			url = myUrl;
			context = contex;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(context);
			dialog.setCancelable(false);
			dialog.setMessage("Fetching in progress...");
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub

			JSONParser jParser = new JSONParser();
			JSONObject json = jParser.getJSONFromUrl(url);
			Log.i("MainActivity","Json returns:"+json);
			try {
				user = json.getJSONArray(TAG_MENU);

				for (int i = 0; i < user.length(); i++) {

					JSONObject c = user.getJSONObject(i);
					String name = c.getString(TAG_NAME);
					String imageUrl = c.getString(TAG_IMAGEURL);
					String category = c.getString(TAG_CATEGORY);
					String spice_meter = c.getString(TAG_SPICEMETER);
					String desc = c.getString(TAG_DESC);
					String rating = c.getString(TAG_RATING);
					String price = c.getString(TAG_PRICE);
					String is_veg = c.getString(TAG_ISVEG);

					HashMap<String, String> details = new HashMap<String, String>();
					details.put(TAG_NAME, name);
					details.put(TAG_IMAGEURL, imageUrl);
					details.put(TAG_CATEGORY, category);
					details.put(TAG_SPICEMETER, spice_meter);
					details.put(TAG_DESC, desc);
					details.put(TAG_RATING, rating);
					details.put(TAG_PRICE, price);
					details.put(TAG_ISVEG, is_veg);

					menu.add(details);

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void result) {

			dialog.dismiss();
			ListAdapter adapter = new SimpleAdapter(MainActivity.this, menu,
					R.layout.list_item,
					new String[] { TAG_NAME, TAG_CATEGORY }, new int[] {
							R.id.itemName, R.id.itemCategory });

			lv.setAdapter(adapter);
			menuLabel.setVisibility(View.VISIBLE);
			menuCount.setVisibility(View.VISIBLE);
			menuCount.setText(String.valueOf(menu.size()));
			butt.setEnabled(false);
		}

	}
	//MyClientTask async task class which perform background operation to retrieve JSON details and populate listview-Arun Sairam-Sep27-Ends

}
