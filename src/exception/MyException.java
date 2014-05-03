package exception;

import android.content.Context;
import android.widget.Toast;

public class MyException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyException(Context context, String e) {
		Toast.makeText(context, e, Toast.LENGTH_LONG).show();
	}

}
