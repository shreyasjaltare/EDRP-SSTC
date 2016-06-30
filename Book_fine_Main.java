package com.edrp.edrp;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import dataretrival.BookFineData;

import beans.Book_fine_pojo;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Book_fine_Main extends Activity {
	
	
	ArrayList<Book_fine_pojo> albkfine = new ArrayList<Book_fine_pojo>();
	String sid;
	Intent nit;
	SharedPreferences pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		ListView lv = (ListView) findViewById(R.id.lvlibtrans);
		StrictMode.enableDefaults();
		setContentView(R.layout.activity_lib_main);
		
		pref = getSharedPreferences("edrp",0);
		
		if(pref.contains("uid"))
		{
			sid = pref.getString("uid",null);
		}
		else{
			
			Toast.makeText(this, "Logged Out!", Toast.LENGTH_LONG).show();
			nit = new Intent(getApplicationContext(),Login.class);
			nit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			nit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(nit);
		}		
        
        BookFineData bkfinedata = new BookFineData();
        
        try {
			JSONArray jArray = new JSONArray(bkfinedata.getBookFine(sid));
			 for(int n = 0; n < jArray.length(); n++)
		        {
		            JSONObject object = jArray.getJSONObject(n);
		            albkfine.add(new Book_fine_pojo(object.getInt("accno"), object.getInt("fine"), object.getString("title"), object.getString("issuedate"), object.getString("duedate"), object.getString("returndate"),object.getString("status")));
		            
		        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    lv = (ListView) findViewById(R.id.lvlibtrans);
    BookFineAdapter ad = new BookFineAdapter(this, albkfine);
    lv.setAdapter(ad);
   
      lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int i,
				long arg3) {
			
			AlertDialog alertDialog = new AlertDialog.Builder(
					Book_fine_Main.this).create();
			
			alertDialog.setTitle(albkfine.get(i).getTitle());
			
			alertDialog.setMessage(albkfine.get(i).toString());
			
			alertDialog.setCanceledOnTouchOutside(true);
			
			alertDialog.show();
		}
    	  
	});
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menuitem, menu);
	    
	    menu.findItem(R.id.libtran).setVisible(false);
	    
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()){
		
		case R.id.menuopt:
			nit = new Intent(this,MainMenu.class);
			nit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nit);
			return true;
			
		case R.id.logout:
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					Book_fine_Main.this);

			// Setting Dialog Title
			alertDialog.setTitle("Logout...?");

			// Setting Dialog Message
			alertDialog.setMessage("Do you want to Logout...?");

			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.logoutalert);

			// Setting Positive Yes Button
			alertDialog.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int which) {
							// User pressed Cancel button. Write Logic Here
							pref = getSharedPreferences("edrp", 0);
							Editor edit = pref.edit();
							edit.clear();
							edit.commit();
							Toast.makeText(getApplicationContext(), "Logged Out!", Toast.LENGTH_LONG).show();
							
							nit = new Intent(getApplicationContext(),Login.class);
							nit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
							nit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(nit);
							finish();
						}
					});
			// Setting Positive Yes Button
			alertDialog.setNegativeButton("NO",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int which) {
							moveTaskToBack(true);
							Book_fine_Main.this.finish();
						}
					});
			// Setting Positive "Cancel" Button
			alertDialog.setNeutralButton("Cancel",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int which) {
							
						}
					});
			// Showing Alert Message
			alertDialog.show();

			return true;
		case R.id.att:
			nit = new Intent(this,AttendanceActivity.class);
			nit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nit);
			return true;
			
		case R.id.bSearch:
			nit = new Intent(this,BookSearch.class);
			nit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nit);
			return true;
			
		case R.id.newnot:
			nit = new Intent(this,Notice.class);
			nit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nit);
			return true;
			
		case R.id.ctmark:
			nit = new Intent(this,CT_Main.class);
			nit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nit);
			return true;
			
		case R.id.acc:
			nit = new Intent(this,Accounts_main.class);
			nit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nit);
			return true;
			
		case R.id.info:
			nit = new Intent(this,MyInfo.class);
			nit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nit);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}


}

class BookFineAdapter extends BaseAdapter{
	
	Context ctx;
	ArrayList<Book_fine_pojo> albkfine;
	
	public BookFineAdapter(Context ctx, ArrayList<Book_fine_pojo> albkfine){
		super();
		this.ctx=ctx;
		this.albkfine= albkfine;
	}

	@Override
	public int getCount() {
		return albkfine.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Book_fine_pojo bkfine= albkfine.get(arg0);
		
	    LayoutInflater linf=(LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
		View v=linf.inflate(R.layout.lib_single_row, null);
		TextView tv1= (TextView) v.findViewById(R.id.bkid);
		TextView tv2= (TextView) v.findViewById(R.id.bktitle);
		TextView tv3= (TextView) v.findViewById(R.id.issuedate);
		TextView tv4= (TextView) v.findViewById(R.id.duedate);
		TextView tv5= (TextView) v.findViewById(R.id.returndate);
		TextView tv6= (TextView) v.findViewById(R.id.bkfine);
		TextView tv7= (TextView) v.findViewById(R.id.bkstatus);
		tv1.setText(String.valueOf(bkfine.getAccno()));
		tv2.setText(bkfine.getTitle());
		tv3.setText(bkfine.getIdate());
		tv4.setText(bkfine.getDdate());
		tv5.setText(bkfine.getRdate());
		tv6.setText("Rs. "+String.valueOf(bkfine.getFine()));
		if(bkfine.getStatus().equalsIgnoreCase("unpaid")){
			
			tv7.setTextColor(android.graphics.Color.RED);
		}
		else 
			tv7.setTextColor(android.graphics.Color.GREEN);
		tv7.setText(bkfine.getStatus());
		return v;
	
	}
	
public void onBackPressed(){
	
}

}
