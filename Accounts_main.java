package com.edrp.edrp;

import beans.*;
import dataretrival.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Accounts_main extends Activity {
    /** Called when the activity is first created. */

	
	ArrayList<Accounts_pojo> alacnts= new ArrayList<Accounts_pojo>();
	String sid;
	Intent nit;
	ListView lv;
	SharedPreferences pref;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts_main);
        StrictMode.enableDefaults();
        
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
        
		Accounts_data ac = new Accounts_data();
        try {
			JSONArray jArray = new JSONArray(ac.getAcnts(sid));
			 for(int n = 0; n < jArray.length(); n++)
		        {
		            JSONObject object = jArray.getJSONObject(n);
		            alacnts.add(new Accounts_pojo(object.getString("RECPTNO"),object.getString("RECPTTYPE"),object.getString("RECPTDATE"),object.getString("AMT")));
		        }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
       // alacnts.add(new Accounts_pojo("Bdakha", "Annual", "12-may-1992", "20000"));
        
        lv=(ListView) findViewById(R.id.lvAcnts);
        AcAdapter ad=new AcAdapter(this, alacnts);     //custom adapter    
       
        lv.setAdapter(ad);
        
        lv.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2,	long arg3) {
				Accounts_pojo p=alacnts.get(arg2);
				Toast.makeText(Accounts_main.this,p.toString() ,Toast.LENGTH_SHORT).show();
				
			}
		});
    }
    
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menuitem, menu);
	    
	    menu.findItem(R.id.acc).setVisible(false);
	    
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
					Accounts_main.this);

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
							Accounts_main.this.finish();
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
			
		case R.id.info:
			nit = new Intent(this,MyInfo.class);
			nit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nit);
			return true;
			
		case R.id.libtran:
			nit = new Intent(this,Book_fine_Main.class);
			nit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nit);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

}

class AcAdapter extends BaseAdapter
{
   Context ctx;
   //ArrayList<Person> alp;
   ArrayList<Accounts_pojo> alacnts;
   
	public AcAdapter(Context ctx, ArrayList<Accounts_pojo> alacnts) {
	super();
	this.ctx = ctx;
	this.alacnts=alacnts;
}

	
	public int getCount() {
		// TODO Auto-generated method stub
		return alacnts.size();
	}

	
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	//Get a View that displays the data at the specified position in the data set. 
	//You can either create a View manually or inflate it from an XML layout file. 
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		/*
		 *int arg0- The position of the item within the adapter's data set of the item whose view we want.
		 *View arg1- To create a new view
		 *ViewGroup arg2- The parent that this view will eventually be attached to.
		 */
		
		 Accounts_pojo acnts=alacnts.get(arg0);
		 LayoutInflater linf=(LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
		/*
		 * LayoutInflater class is used to instantiate layout XML file into its corresponding View objects.
           In other words, it takes as input an XML file and builds the View objects from it.
           It is never used directly. Instead, use getLayoutInflater() or getSystemService(String) to 
           retrieve a standard LayoutInflater instance that is already hooked up to the current context
		 */
		View v=linf.inflate(R.layout.accounts_modified_view, null); /* We inflate the xml which gives us a view */
		TextView tv1=(TextView) v.findViewById(R.id.recptno);
		TextView tv2=(TextView) v.findViewById(R.id.recpttype);
		TextView tv3 = (TextView) v.findViewById(R.id.amt);
		TextView tv4 = (TextView) v.findViewById(R.id.recptdate);
		tv1.setText(acnts.getRecptno());
		tv2.setText(acnts.getRecpttype());
		tv3.setText(acnts.getAmt());
		tv4.setText(acnts.getRecptdate());
		
		return v;
	}

	public void onBackPressed(){
		
	}
}