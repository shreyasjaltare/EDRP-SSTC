package com.edrp.edrp;

import dataretrival.*;
import beans.*;
import java.util.ArrayList;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class BookSearch extends Activity {

	
	ListView lv;
	AutoCompleteTextView search;
	ArrayList<String> autoAd;
	ArrayList<BookData> bData;
	RadioGroup rgp;
	String keyWord,sid;
	Button go;
	Books b;
	ArrayAdapter<String> ad;
	Intent nit;
	SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_search);
		
		lv = (ListView)findViewById(R.id.list);
		search = (AutoCompleteTextView)findViewById(R.id.search);
		go = (Button)findViewById(R.id.go);
		rgp = (RadioGroup)findViewById(R.id.radiog);
		b = new Books();
		
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
		
	// setting adapter depending upon the criteria selected

	/*	search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { */
		
	
		autoAd = b.book("Title");
		
		ad = new ArrayAdapter<String>(BookSearch.this,android.R.layout.simple_list_item_1,autoAd );
		search.setAdapter(ad);
		search.setThreshold(1);
		
		rgp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				int store=rgp.getCheckedRadioButtonId();
				
				RadioButton rb=(RadioButton) findViewById(store);
				
				autoAd = b.book(rb.getText().toString());
				
				ad = new ArrayAdapter<String>(BookSearch.this,android.R.layout.simple_list_item_1,autoAd );
				search.setAdapter(ad);
				search.setThreshold(1);				
		
				
			}
		});
		
	/*		}
		});
		*/
		go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int store=rgp.getCheckedRadioButtonId();
				
				RadioButton rb=(RadioButton) findViewById(store);
				
			//search by title
				if(rb.getText().equals("Title")){
					
					String title = search.getText().toString();
					
					bData = b.titleBook(title);
					
					if(bData.isEmpty()){
						
						Toast.makeText(BookSearch.this, "No Book With such Title: "+title, Toast.LENGTH_LONG).show();
					}
					else {
						
						MyAdapter aAd = new MyAdapter(BookSearch.this,bData);
						lv.setAdapter(aAd);
						
					}
				}
				
			//to search author wise
				else if(rb.getText().equals("Author")){
					
					String auth = search.getText().toString();
					
					bData = b.authBook(auth);
					
					if(bData.isEmpty()){
						
						Toast.makeText(BookSearch.this, "No Book By such Author name: "+auth, Toast.LENGTH_LONG).show();
					}
					else {
					
						MyAdapter aAd = new MyAdapter(BookSearch.this,bData);
						lv.setAdapter(aAd);
						
					}
					
				} 
				
			//search publisher wise
				else if(rb.getText().equals("Publisher")){
					
					String publisher = search.getText().toString();
					
					bData = b.publisherBook(publisher);
					
					if(bData.isEmpty()){
						
						Toast.makeText(BookSearch.this, "No Book From such Publisher: "+publisher, Toast.LENGTH_LONG).show();
					}

					else {
				
						MyAdapter aAd = new MyAdapter(BookSearch.this,bData);
						lv.setAdapter(aAd);
						
					}
					
				} 
			}
		});
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int i,
					long l) {
				AlertDialog alertDialog = new AlertDialog.Builder(
						BookSearch.this).create();
				
				alertDialog.setTitle(bData.get(i).getTitle());
				
				alertDialog.setMessage(bData.get(i).toString());
				
				alertDialog.setCanceledOnTouchOutside(true);
				
				alertDialog.show();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menuitem, menu);
	    
	    menu.findItem(R.id.bSearch).setVisible(false);
	    
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
					BookSearch.this);

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
							BookSearch.this.finish();
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
			
		case R.id.info:
			nit = new Intent(this,MyInfo.class);
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
}

class MyAdapter extends BaseAdapter{

	Context ctx;
	ArrayList<BookData> bData;
	
	@Override
	public int getCount() {
		
		return bData.size();
	}

	public MyAdapter(Context ctx, ArrayList<BookData> bData) {
		super();
		this.ctx = ctx;
		this.bData = bData;
	}

	@Override
	public Object getItem(int arg0) {
		
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		
		return 0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		String av;
		
		BookData book = bData.get(arg0);
		LayoutInflater linf=(LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
		
		View v = linf.inflate(R.layout.book_list_item,null);
		
		TextView author,title,edition,status;
	
		author = (TextView) v.findViewById(R.id.author);
		title = (TextView) v.findViewById(R.id.title);
		edition = (TextView) v.findViewById(R.id.edition);
		status = (TextView) v.findViewById(R.id.status);
		av=book.getAvailable();
		
		if(av.equalsIgnoreCase("not available")){
			
			v.setBackgroundResource(android.R.color.holo_red_dark);
			
		}
		else if(av.equalsIgnoreCase("available")){
			
			v.setBackgroundResource(android.R.color.holo_green_light);
			
		}
		else if(av.equalsIgnoreCase("reference")){
			
			v.setBackgroundResource(android.R.color.holo_blue_light);
			
		}
		
		author.setText(book.getAuthor());
		title.setText(book.getTitle());
		edition.setText(book.getEdition());
		status.setText(book.getAvailable());
		
		return v;
	}

	public void onBackPressed(){
		
	}
	
}