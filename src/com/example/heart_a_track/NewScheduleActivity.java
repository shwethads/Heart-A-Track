package com.example.heart_a_track;

import java.util.Calendar;

import DBLayout.DatabaseHandler;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class NewScheduleActivity extends ActionBarActivity implements OnClickListener{
	
	private String id, date, description;
	private boolean isUpdate;
	
	private EditText editText1;
	private EditText editText2;
	private Button btnChangeDate;
	private Button btnChangeTime;
	private Button btnOK;
	private DatabaseHandler mHelper;
	private SQLiteDatabase dataBase;
	// the time of the new schedule
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	static final int DATE_DIALOG_ID = 999;
	static final int TIME_DIALOG_ID = 998;
	private static final int HELLO_ID = 1;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_schedule);
		
		btnOK=(Button)findViewById(R.id.okButton);
		editText1=(EditText)findViewById(R.id.edittext1);
        editText2=(EditText)findViewById(R.id.edittext2);
		
	    isUpdate=getIntent().getExtras().getBoolean("update");

	         
	    btnOK.setOnClickListener(this);
	    mHelper=new DatabaseHandler(this);
 
		setCurrentDateTimeOnView();
		addListenerOnButton1();
		addListenerOnButton2();
		
	    if(isUpdate)
	    {
	        id=getIntent().getExtras().getString("ID");
	        date=getIntent().getExtras().getString("date");
	        description=getIntent().getExtras().getString("description");
	        editText1.setText(date);
	        editText2.setText(description);	        	
	    }
	}
 
	
    // okButton click event 
	public void onClick(View v) {
		date=editText1.getText().toString();
		description=editText2.getText().toString();
		if(date.length()>0 && description.length()>0)
		{
			saveData();
			
			
			//Trying to add a new alarm when a new schedule is created as the OK button clicked in NewScheduleActivity, seems not working, 
			//please help to examin the code from line 90-106 as well as the AlarmManagerBroadcastReceiver file.
			Calendar cal = Calendar.getInstance();     	 
			// add the schedule time to the alarm
			cal.set(Calendar.YEAR, year);		
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, day);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);
			
			// Setting alarm on device
			Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
		    i.putExtra(AlarmClock.EXTRA_HOUR, hour);
		    i.putExtra(AlarmClock.EXTRA_MINUTES, minute);
		    startActivity(i);
			
		}
		else
		{
			AlertDialog.Builder alertBuilder=new AlertDialog.Builder(NewScheduleActivity.this);
			alertBuilder.setTitle("Invalid Data");
			alertBuilder.setMessage("Please, Enter valid data");
			alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
					
				}
			});
			alertBuilder.create().show();
		}
		
	}

	/**
	 * save data into SQLite
	 */
	private void saveData(){
		dataBase=mHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		
		values.put(DatabaseHandler.KEY_DATE,date);
		values.put(DatabaseHandler.KEY_DESCRIPTION,description );
		
		System.out.println("");
		if(isUpdate)
		{    
			//update database with new data 
			dataBase.update(DatabaseHandler.TABLE3, values, DatabaseHandler.KEY_ID+"="+id, null);
		}
		else
		{
			//insert data into database
			dataBase.insert(DatabaseHandler.TABLE3, null, values);
		}
		//close database
		dataBase.close();
		finish();
		
		
	}
	// display current date
	public void setCurrentDateTimeOnView() {
 
		editText1 = (EditText) findViewById(R.id.edittext1);
 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		// set current date and time
		date = new StringBuilder()
		.append(month + 1).append("-").append(day).append("-")
		.append(year).append("   ").append(pad(hour))
        .append(":").append(pad(minute)).toString();
		editText1.setText(date);
 
	}
 
	public void addListenerOnButton1() {
 
		btnChangeDate = (Button) findViewById(R.id.button1);
 
		btnChangeDate.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
				showDialog(DATE_DIALOG_ID);
 
			}
 
		});
 
	}
	
	public void addListenerOnButton2() {
		 
		btnChangeTime = (Button) findViewById(R.id.button2);
 
		btnChangeTime.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
				showDialog(TIME_DIALOG_ID);
 
			}
 
		});
 
	}
 
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, 
                                        timePickerListener, hour, minute,false);
 
		}
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
 
			// set selected date into textview
			date = new StringBuilder()
			.append(month + 1).append("-").append(day).append("-")
			.append(year).append("   ").append(pad(hour))
	        .append(":").append(pad(minute)).toString();
			editText1.setText(date);
 
		}
	};
	
	private TimePickerDialog.OnTimeSetListener timePickerListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;
 
			// set current time into textview
			date = new StringBuilder()
			.append(month + 1).append("-").append(day).append("-")
			.append(year).append("   ").append(pad(hour))
	        .append(":").append(pad(minute)).toString();
			editText1.setText(date);
 
		}
	};
	
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
 
}