package com.example.heart_a_track;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CheckRateActivity extends ActionBarActivity {
	private static final int CAMERA_REQUEST = 1888; 
	private ImageView iv1;
	private Button okBtn;
	private String uname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_rate);

		Intent intent = getIntent();
		uname = intent.getStringExtra("uname");
		
		iv1 = (ImageView) findViewById(R.id.imageView1);
		
		okBtn = (Button) findViewById(R.id.button1);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
				startActivityForResult(cameraIntent, CAMERA_REQUEST); 

			}
		});


		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
			Bitmap photo = (Bitmap) data.getExtras().get("data"); 
			iv1.setImageBitmap(photo);
			
			
			okBtn.setText("View Result");
			okBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int result = getColor();
					Intent intent = new Intent(CheckRateActivity.this, RateResultActivity.class);
					intent.putExtra("result", result);
					intent.putExtra("uname", uname);
					startActivity(intent);					
				}
			});
		} 

	} 

	protected int getColor() {
		final Bitmap bitmap = ((BitmapDrawable)iv1.getDrawable()).getBitmap();

		int pixel = bitmap.getPixel(60,100);

		int r = (pixel >> 16) & 0xFF;
		int g = (pixel >> 8) & 0xFF;
		int b = (pixel >> 0) & 0xFF;
		
		int redVal = 315-r;

		System.out.println("------"+r+"-----------"+g+"--------"+b+"------------"+redVal);
		return redVal;

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_rate, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_check_rate,
					container, false);
			return rootView;
		}
	}

}
