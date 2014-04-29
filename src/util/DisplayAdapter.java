package util;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.heart_a_track.R;
/**
 * adapter to populate listview with data
 */
public class DisplayAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> date;
	private ArrayList<String> description;
	

	public DisplayAdapter(Context c, ArrayList<String> id,ArrayList<String> fname, ArrayList<String> lname) {
		this.mContext = c;

		this.id = id;
		this.date = fname;
		this.description = lname;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return id.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View child, ViewGroup parent) {
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.listcell, null);
			mHolder = new Holder();
			mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
			mHolder.txt_date = (TextView) child.findViewById(R.id.txt_date);
			mHolder.txt_description = (TextView) child.findViewById(R.id.txt_description);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.txt_id.setText(id.get(pos));
		mHolder.txt_date.setText(date.get(pos));
		mHolder.txt_description.setText(description.get(pos));

		return child;
	}

	public class Holder {
		TextView txt_id;
		TextView txt_date;
		TextView txt_description;
	}

}
