package com.edrp.edrp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
 
    // Keep all Images in array
    
    public String[] str;
    public Integer[] mThumbIds = {
            R.drawable.icn_account, R.drawable.icn_attendance,
            R.drawable.icn_libsrch, R.drawable.icn_info,
            R.drawable.icn_news, R.drawable.icn_ct,R.drawable.icn_libtr
    };
 
    // Constructor
    public ImageAdapter(Context c,String [] str){
        mContext = c;
        this.str = str;
    }
 
    @Override
    public int getCount() {
        return mThumbIds.length;
    }
 
    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	LayoutInflater inflater = (LayoutInflater) mContext
    			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     
    		View gridView;
     
    		if (convertView == null) {
    			
    			gridView = inflater.inflate(R.layout.grid_option, null);
    		
    		//TextView work
    			TextView tv = (TextView)gridView.findViewById(R.id.opt);	
    			tv.setText(str[position]);
    			
    		//ImageView work
    			ImageView img = (ImageView)gridView.findViewById(R.id.img);
    			
    			if(str[position].equalsIgnoreCase("my info")){
    				
    				img.setImageResource(R.drawable.icn_info);
    				
    			}
    			
    			else if(str[position].equalsIgnoreCase("attendance")){
    				
    				img.setImageResource(R.drawable.icn_attendance);
    				
    			}
    			
    			else if(str[position].equalsIgnoreCase("accounts")){
    				
    				img.setImageResource(R.drawable.icn_account);
    				
    			}
    			
    			else if(str[position].equalsIgnoreCase("search library books")){
    				
    				img.setImageResource(R.drawable.icn_libsrch);
    				
    			}
    			
    			else if(str[position].equalsIgnoreCase("news/notice")){
    				
    				img.setImageResource(R.drawable.icn_news);
    				
    			}
    			else if(str[position].equalsIgnoreCase("CT marks")){
    				
    				img.setImageResource(R.drawable.icn_ct);
    				
    			}
    			else if(str[position].equalsIgnoreCase("Library transaction")){
    				
    				img.setImageResource(R.drawable.icn_libtr);
    				
    			}
    		} else {
    			gridView = (View) convertView;
    		}
     
    		return gridView;
    }
 
}
