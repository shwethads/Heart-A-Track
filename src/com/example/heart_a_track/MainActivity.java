package com.example.heart_a_track;

import java.util.ArrayList;

import util.Util;
import entities.User;
import DBLayout.DatabaseHandler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
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
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	DatabaseHandler db = new DatabaseHandler(this);
	Util util = new Util();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView tvForgotPwd = (TextView) findViewById(R.id.textView4);
        tvForgotPwd.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ForgotPassActivity.class);
				startActivity(intent);				
			}
		});
        
        TextView tvSignup = (TextView) findViewById(R.id.textView5);
        tvSignup.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SignupActivity.class);
				startActivity(intent);
			}
		});
        
        
        Button okBtn = (Button) findViewById(R.id.button1);
        okBtn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				EditText username = (EditText) findViewById(R.id.editText1);
		        EditText pwd = (EditText) findViewById(R.id.editText2);		        
		        String uname = username.getText().toString();
		        String pass = pwd.getText().toString();
		        System.out.println(uname);
		        
				// check if input values are empty
				if(uname.equals("") || pass.equals("")) {
					Toast.makeText(getApplicationContext(), "Please enter values", 
							   Toast.LENGTH_LONG).show();
				}
				
				
				//Check if input values are correct
				if(util.checkCredentials(db, uname, pass)){
					//If input is correct
					Intent intent = new Intent(MainActivity.this, HomeActivity.class);
					//set user identifier
					startActivity(intent);
				}
				else {
					Toast.makeText(getApplicationContext(), "Incorrect unsername/password", 
							   Toast.LENGTH_LONG).show();
				}			
			}
		});

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
