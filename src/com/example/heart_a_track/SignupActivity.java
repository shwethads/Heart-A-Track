package com.example.heart_a_track;

import util.Util;
import DBLayout.DatabaseHandler;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends ActionBarActivity {
	Util util = new Util();
	DatabaseHandler db = new DatabaseHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		Button okBtn = (Button) findViewById(R.id.button1);
		okBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText uname = (EditText) findViewById(R.id.editText1);
				EditText pass = (EditText) findViewById(R.id.editText2);
				
				String username = uname.getText().toString();
				String pwd = pass.getText().toString();
				
				if(username.equalsIgnoreCase("") || pwd.equalsIgnoreCase("")) {
					Toast.makeText(getApplicationContext(), "Please enter values", 
							   Toast.LENGTH_LONG).show();
				}
				else {
					if(util.checkUname(db, username)) {
						Toast.makeText(getApplicationContext(), "User exixts", 
								   Toast.LENGTH_LONG).show();
					}
					else {
						db.addUser(username, pwd);
					}
				}
				
			}
		});

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_signup,
					container, false);
			return rootView;
		}
	}

}
