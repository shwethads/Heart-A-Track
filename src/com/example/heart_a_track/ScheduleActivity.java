package com.example.heart_a_track;

import java.util.ArrayList;

import util.DisplayAdapter;

import DBLayout.DatabaseHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
/**
 * activity to display all schedule records from SQLite database

 */
public class ScheduleActivity extends Activity {

	private DatabaseHandler mHelper;
	private SQLiteDatabase dataBase;

	private ArrayList<String> sID = new ArrayList<String>();
	private ArrayList<String> date = new ArrayList<String>();
	private ArrayList<String> description = new ArrayList<String>();

	private ListView userList;
	private AlertDialog.Builder build;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		userList = (ListView) findViewById(R.id.listview);

		mHelper = new DatabaseHandler(this);
		
		//add new record
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(),
						NewScheduleActivity.class);
				i.putExtra("update", false);
				startActivity(i);

			}
		});
		
		//click to update data
		userList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent i = new Intent(getApplicationContext(),
						NewScheduleActivity.class);
				i.putExtra("date", date.get(arg2));
				i.putExtra("description", description.get(arg2));
				i.putExtra("ID", sID.get(arg2));
				i.putExtra("update", true);
				startActivity(i);

			}
		});
		
		//long click to delete data
		userList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				build = new AlertDialog.Builder(ScheduleActivity.this);
				build.setTitle("Delete " + date.get(arg2) + " "
						+ description.get(arg2));
				build.setMessage("Do you want to delete ?");
				build.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								Toast.makeText(
										getApplicationContext(),
										date.get(arg2) + " "
												+ description.get(arg2)
												+ " is deleted.", Toast.LENGTH_LONG).show();

								dataBase.delete(
										DatabaseHandler.TABLE3,
										DatabaseHandler.KEY_ID + "="
												+ sID.get(arg2), null);
								displayData();
								dialog.cancel();
							}
						});

				build.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				AlertDialog alert = build.create();
				alert.show();

				return true;
			}
		});
	}

	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}

	/**
	 * displays data from SQLite
	 */
	private void displayData() {
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ DatabaseHandler.TABLE3, null);

		sID.clear();
		date.clear();
		description.clear();
		if (mCursor.moveToFirst()) {
			do {
				sID.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_ID)));
				date.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_DATE)));
				description.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_DESCRIPTION)));

			} while (mCursor.moveToNext());
		}
		DisplayAdapter disadpt = new DisplayAdapter(ScheduleActivity.this,sID, date, description);
		userList.setAdapter(disadpt);
		mCursor.close();
	}

	

}
