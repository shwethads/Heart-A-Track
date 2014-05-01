package com.example.heart_a_track;

import exception.MyException;
import util.Util;
import DBLayout.DatabaseHandler;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPassActivity extends ActionBarActivity {
	Util util = new Util();
	DatabaseHandler db = new DatabaseHandler(this);
	String uname, pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_pass);

		final EditText unameET = (EditText) findViewById(R.id.edittext1);
		final TextView tvRes = (TextView) findViewById(R.id.textView3);
		Button okBtn = (Button) findViewById(R.id.button1);		
		
		okBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uname = unameET.getText().toString();
				
				try {
					if(uname == "") {
						Intent intent = new Intent(ForgotPassActivity.this, ForgotPassActivity.class);
						startActivity(intent);
						throw new MyException(getApplicationContext(), "Enter username");
					}
					else {
						pass = db.forgotPass(uname);
						tvRes.setText("Password: "+pass);
					}
				} catch(MyException e) {
					e.printStackTrace();
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
		getMenuInflater().inflate(R.menu.forgot_pass, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_forgot_pass,
					container, false);
			return rootView;
		}
	}

}
