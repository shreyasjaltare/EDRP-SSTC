package com.edrp.edrp;

import java.util.ArrayList;

import dataretrival.Student;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;




public class MyInfo extends Activity {	
	
	TextView tv,tvhdr,tvClg,tvSem,tvBrnch,tvDob,tvPhn,tvEmail;
	
	String sid;
	Intent nit;
	SharedPreferences pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_info);
		StrictMode.enableDefaults();
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
		
		tvhdr = (TextView) findViewById(R.id.tvhdr);
		tvClg = (TextView) findViewById(R.id.textClg);
		tvSem=(TextView) findViewById(R.id.textSem);
		tvBrnch=(TextView) findViewById(R.id.textBrnch);
		tvDob=(TextView) findViewById(R.id.textDOB);
		tvPhn=(TextView) findViewById(R.id.textPhone);
		tvEmail=(TextView) findViewById(R.id.textEmail);
		tvhdr.setText(s.getName(sid));
		ArrayList<String> al=s.getStudentInfo(sid);
		tvClg.setText(al.get(0));
		tvSem.setText(al.get(2));
		tvBrnch.setText(al.get(1));
		tvDob.setText(al.get(3));
		tvPhn.setText(al.get(4));
		tvEmail.setText(al.get(5));
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menuitem, menu);
	    
	    menu.findItem(R.id.info).setVisible(false);
	    
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
					MyInfo.this);

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
							MyInfo.this.finish();
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
			
		case R.id.libtran:
			nit = new Intent(this,Book_fine_Main.class);
			nit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nit);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

   public void onBackPressed(){
	   
   }
	
}
