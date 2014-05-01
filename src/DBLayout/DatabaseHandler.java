package DBLayout;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import entities.HeartRate;
import entities.User;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "heartATrack2";
	private static final String TABLE1 = "users";
	private static final String TABLE2 = "heartRate";
	public static final String TABLE3 = "schedule"; 

	public static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	public static final String KEY_DATE = "date";
	private static final String KEY_HEARTRATE = "heartrate";
	//schedule event id and description 
	public static final String KEY_ID = "id";
	public static final String KEY_DESCRIPTION = "description";
	
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);      
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("DBH onCreate..........");
		String CREATE_TABLE1 = ("CREATE TABLE IF NOT EXISTS "+TABLE1+"("+KEY_USERNAME+
				" STRING PRIMARY KEY,"+KEY_PASSWORD+" STRING"+")");
		String CREATE_TABLE2 = ("CREATE TABLE IF NOT EXISTS "+TABLE2+"("+KEY_USERNAME+
				" STRING PRIMARY KEY,"+KEY_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP,"+ KEY_HEARTRATE+" NUMBER"+")");
		String CREATE_TABLE3 = "CREATE TABLE IF NOT EXISTS "+TABLE3+"("+KEY_ID+
				" INTEGER PRIMARY KEY,"+KEY_DATE+" TEXT,"+
				KEY_DESCRIPTION+" TEXT"+")";
		
		db.execSQL(CREATE_TABLE1);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE2);
		db.execSQL(CREATE_TABLE2);		
		db.execSQL(CREATE_TABLE3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("DBH onUpgrade..........");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE2);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE3);
		onCreate(db);
	}
	
	public String forgotPass(String username) {
		String pass = new String(); 
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		String query = "SELECT "+KEY_PASSWORD+" FROM "+TABLE1 +" WHERE USERNAME="+"'"+username+"'";

	    Cursor cursor = db.rawQuery(query, null);
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	pass = cursor.getString(0);
	        }while(cursor.moveToNext());
	    }
	    return pass;
	}
	
	public void addUser(String username, String password) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);
        
        db.insert(TABLE1, null, values);
	}
	
	public void addHeartRate(String username, int heartRate) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        
        values.put(KEY_USERNAME, username);
//        values.put(KEY_DATE, date.toString());
        values.put(KEY_HEARTRATE, heartRate);
        
        db.insert(TABLE2, null, values);
	}
	
	public ArrayList<User> getUsers() {
		ArrayList<User> userList = new ArrayList<User>();
		String query = "SELECT * FROM "+TABLE1;
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(query, null);
		
	    if (cursor.moveToFirst()) {
	        do {
	        	User user = new User();
	        	user.setUsername(cursor.getString(0));
	        	user.setPassword(cursor.getString(1));
	        	userList.add(user);
	        }while(cursor.moveToNext());
	    }
	    return userList;
	}
	
	public ArrayList<HeartRate> getHeartRate() {
		ArrayList<HeartRate> rateList = new ArrayList<HeartRate>();
		String query = "SELECT * FROM "+TABLE2;
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(query, null);
		
	    if (cursor.moveToFirst()) {
	        do {
	        	HeartRate hr = new HeartRate();
	        	hr.setUsername(cursor.getString(0));
	        	hr.setDate(cursor.getString(1));
	        	hr.setHeartRate(cursor.getFloat(2));
	        	rateList.add(hr);
	        }while(cursor.moveToNext());
	    }
	    return rateList;
	}
	
}
