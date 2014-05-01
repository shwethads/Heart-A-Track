package exception;

import android.content.Context;
import android.widget.Toast;

public class MyException extends Exception{
	
	public MyException(Context context, String e) {
		Toast.makeText(context, e, Toast.LENGTH_LONG).show();
	}

}
