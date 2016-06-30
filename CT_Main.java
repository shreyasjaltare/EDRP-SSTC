package com.edrp.edrp;

import java.util.ArrayList;

import dataretrival.CT_data;
import dataretrival.Student;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CT_Main extends Activity {
	
	String sid;
	Intent nit;
	ArrayList<String> subname,submarks;
	ListView lvheader;
	SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ct_main);
		StrictMode.enableDefaults();
		TextView tvname;
		Button submit;
		final Spinner cttype;
		
		tvname=(TextView) findViewById(R.id.stdname);
		submit=(Button) findViewById(R.id.getctresult);
		lvheader= (ListView) findViewById(R.id.lvheader);
		cttype=(Spinner) findViewById(R.id.cttype);
		Student s = new Student();
		
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
		
		tvname.setText(s.getName(sid));			
		ArrayList<String> info = s.getStudentInfo(sid);
		final String branch="CSE";
		final int sem = 7;
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int ctnum = 0;
				if(cttype.getSelectedItem().toString().equals("CT-1")){
					ctnum=1;
				}
				else if (cttype.getSelectedItem().toString().equals("CT-2")) {
					ctnum=2;
				}
				CT_data ct = new CT_data();			
				subname =ct.getSubName(branch, sem);
				submarks=ct.getSubMarks(sid,sem, ctnum);
				CTAdapter ad = new CTAdapter(getApplicationContext(), subname, submarks);
				lvheader.setAdapter(ad);
				
			}
		});
		
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menuitem, menu);
	    
	    menu.findItem(R.id.ctmark).setVisible(false);
	    
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
					CT_Main.this);

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
							CT_Main.this.finish();
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
			
		case R.id.info:
			nit = new Intent(this,MyInfo.class);
			nit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nit);
			return true;
			
		case R.id.acc:
			nit = new Intent(this,Accounts_main.class);
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


 class CTAdapter extends BaseAdapter{
	 
	 Context ctx;
	 ArrayList<String> subname,submarks;
	 
	 public CTAdapter(Context ctx, ArrayList<String> subname , ArrayList<String> submarks){
		 super();
		 this.ctx=ctx;
		 this.submarks=submarks;
		 this.subname=subname;
	 }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
			return submarks.size();				
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		LayoutInflater inf = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE); 
		
		View v = inf.inflate(R.layout.ct_row, null);
		
		TextView tsubj = (TextView) v.findViewById(R.id.textHeader);
		TextView tmark = (TextView) v.findViewById(R.id.SubMarks);
		if(subname.get(position)!="null"){
			tsubj.setText(subname.get(position));
		}
		else {
			v.setBackground(null);
		}
		if(subname.get(position)!="null"){
			tmark.setText(submarks.get(position));
		}
		else{
			v.setBackground(null);
		}
		
		return v;
	}

	public void onBackPressed(){
		
	}
}