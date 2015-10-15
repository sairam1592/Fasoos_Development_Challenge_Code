package com.example.fasoos_challenge;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;

public class First_Activity extends Activity {

	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		((Button) findViewById(R.id.button1))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// TODO Auto-generated method stub
						enterButtonOnClick();
					}
				});

	}

	public void enterButtonOnClick() {

		intent = new Intent(First_Activity.this, MainActivity.class);
		intent.putExtra("food_select_page", "Welcome to food selection page");
		startActivity(intent);
	}

}
