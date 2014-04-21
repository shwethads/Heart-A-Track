package util;

import java.util.ArrayList;

import DBLayout.DatabaseHandler;
import entities.User;

public class Util {

	/**
	 * Check if values eneterd are correct
	 * @param uname
	 * @param pwd
	 * @return
	 */
	public boolean checkCredentials(DatabaseHandler db, String uname, String pwd) {
		ArrayList<User> users = db.getUsers();
		
		for(User u:users){
			if(u.getUsername().equalsIgnoreCase(uname) && u.getPassword().equalsIgnoreCase(pwd)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Check if username exixts
	 * @param uname
	 * @param pwd
	 * @return
	 */
	public boolean checkUname(DatabaseHandler db, String uname) {
		ArrayList<User> users = db.getUsers();
		
		for(User u:users){
			if(u.getUsername().equalsIgnoreCase(uname))
				return true;
			}
		return false;
	}

}
