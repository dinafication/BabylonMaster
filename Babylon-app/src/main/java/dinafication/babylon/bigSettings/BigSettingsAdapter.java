package dinafication.babylon.bigSettings;

import java.util.List;

import dinafication.babylon.main.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class BigSettingsAdapter extends ArrayAdapter<RowItem> {
	 
	    Context context;
	 
	    public BigSettingsAdapter(Context context, int resourceId,
	            List<RowItem> items) {
	        super(context, resourceId, items);
	        this.context = context;
	    }
	 
	    /*private view holder class*/
	    private class ViewHolder {
	        ImageView imageView;
	        TextView txtTitle;
	    }
	 
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder = null;
	        RowItem rowItem = getItem(position);
	 
	        LayoutInflater mInflater = (LayoutInflater) context
	                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.simple_row, null);
	            holder = new ViewHolder();
	            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
	            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
	            convertView.setTag(holder);
	        } else
	            holder = (ViewHolder) convertView.getTag();
	 
	        holder.txtTitle.setText(rowItem.getTitle());
	        holder.imageView.setImageResource(rowItem.getImageId());
	 
	        return convertView;
	    }
	}
