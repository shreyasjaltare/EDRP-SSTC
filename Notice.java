package com.edrp.edrp;

import java.util.ArrayList;

import dataretrival.NoticeTask;
import beans.*;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Notice extends Activity {

	ListView lst;
	ArrayList<NoticePojo> nArr;
	String sid;
	Intent nit;
	SharedPreferences pref; 
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		
		lst = (ListView) findViewById(R.id.notelv);
		nArr = new ArrayList<NoticePojo>();
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
		StrictMode.enableDefaults();
		
		nArr = new NoticeTask().getNotice(sid);
		
		NoticeAdapter nad = new NoticeAdapter(Notice.this,nArr);
		lst.setAdapter(nad);
		
		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				AlertDialog alertDialog = new AlertDialog.Builder(
						Notice.this).create();
				String sender,content;
				
				if(nArr.get(arg2).getSender().equalsIgnoreCase("null")){
					sender = "-";
				}
				else sender = nArr.get(arg2).getSender();
				
				if(nArr.get(arg2).getContent().equalsIgnoreCase("null")){
					content = "No ontent to display.";
				}
				else content = nArr.get(arg2).getContent();
				
				alertDialog.setCanceledOnTouchOutside(true);
				alertDialog.setTitle("Sender: "+sender);
				alertDialog.setMessage(content);
				alertDialog.show();
				
				new NoticeTask().setReadYes(sid, nArr.get(arg2).getNoticeID());
				
				arg1.setAlpha(0.5f);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menuitem, menu);
	    
	    menu.findItem(R.id.newnot).setVisible(false);
	    
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
					Notice.this);

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
							Notice.this.finish();
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
			
		case R.id.info:
			nit = new Intent(this,MyInfo.class);
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
	
}

class NoticeAdapter extends BaseAdapter{
	
	Context ctx;
	ArrayList<NoticePojo> nPojo;
	
	public NoticeAdapter(Context ctx, ArrayList<NoticePojo> nPojo) {
		super();
		this.ctx = ctx;
		this.nPojo = nPojo;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return nPojo.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		
		NoticePojo np = nPojo.get(position);
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
		View nv = inflater.inflate(R.layout.notice_items, null);
		
		TextView sub,date;
		
		sub = (TextView) nv.findViewById(R.id.sub);
		date= (TextView) nv.findViewById(R.id.date);
		
		if(np.getRead().equalsIgnoreCase("N")){
			
			nv.setAlpha(1f);
		}
		else 
			nv.setAlpha(0.5f);
		
		sub.setText(np.getSubject());
		date.setText(np.getNoticeDate());
		return nv;
		
	}
	
}