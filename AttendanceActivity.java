package com.edrp.edrp;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class AttendanceActivity extends Activity {

	public AttendanceActivity(){
		
		// Getting current year, day and month
		
		final Calendar c = Calendar.getInstance();
		
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
	
	}
	
	TextView percent,totalPeriods,attendedPeriods,iText,fText;
	Button from,to;
	Intent nit;
	String sid;
	SharedPreferences pref;
	
	static final int DATE_DIALOG_ID1 = 1;
	static final int DATE_DIALOG_ID2 = 2;
	 public  int iYear,iMonth,iDay,fYear,fMonth,fDay;
	 
	 private int mYear, mMonth, mDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance);
		
		from = (Button) findViewById(R.id.initialDate);
		to = (Button) findViewById(R.id.finalDate);
		
		percent = (TextView) findViewById(R.id.per);
		totalPeriods = (TextView) findViewById(R.id.totperiod);
		attendedPeriods = (TextView) findViewById(R.id.atnperiod);
		iText = (TextView) findViewById(R.id.iDate);
		fText = (TextView) findViewById(R.id.fDate);
		
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
		
		from.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				
				showDialog(DATE_DIALOG_ID1);
			}
		});
		
		to.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
			
				showDialog(DATE_DIALOG_ID2);
				
			}
		});
		
	}
	
						// Register  DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener iDateSetListener =
                           new DatePickerDialog.OnDateSetListener() {
                        // the callback received when the user "sets" the Date in the DatePickerDialog
                               public void onDateSet(DatePicker view, int yearSelected,
                                                     int monthOfYear, int dayOfMonth) {
                                  iYear = yearSelected;
                                  iMonth = monthOfYear;
                                  iDay = dayOfMonth;
                                                                 
                                  // Set the Selected Date in Select date Button
                                  iText.setText(" "+iDay+"-"+iMonth+"-"+iYear);
                               }
                           };

                           // Register  DatePickerDialog listener
     private DatePickerDialog.OnDateSetListener fDateSetListener =
                           new DatePickerDialog.OnDateSetListener() {
                       // the callback received when the user "sets" the Date in the DatePickerDialog
    	 				       public void onDateSet(DatePicker view, int yearSelected,
    	 				    		   				int monthOfYear, int dayOfMonth) {
    	 				    	   fYear = yearSelected;
                                   fMonth = monthOfYear;
                                   fDay = dayOfMonth;
                       
                                   if(fYear<iYear||(fYear==iYear && fMonth<iMonth)||(fMonth==iMonth && fDay<iDay)){
                                	   
                                	   fText.setTextColor(android.graphics.Color.RED);
                                	   fText.setText("Wrong selection");
                                   }
                                   else{
                                	   fText.setTextColor(android.graphics.Color.WHITE);
                                	   fText.setText(" "+fDay+"-"+fMonth+"-"+fYear);
                                	  
                                	   percent.setText("60%");
                                	   
                                	   totalPeriods.setText("250");
                                	   attendedPeriods.setText("150");
                                   }
                               
                                }
                           };
     
                        // Method automatically gets Called when you call showDialog()  method
                           @Override
                           protected Dialog onCreateDialog(int id) {
                               switch (id) {
                               case DATE_DIALOG_ID1:
                        // create a new DatePickerDialog with values you want to show
                            	   DatePickerDialog dialog = new DatePickerDialog(this,2, iDateSetListener, mYear, mMonth, mDay);
                            	    dialog.getDatePicker().setMaxDate(new Date().getTime());
                            	    
                                   return dialog;
                               case DATE_DIALOG_ID2:
                            	   
                            	   DatePickerDialog dialog2 = new DatePickerDialog(this,2, fDateSetListener, mYear, mMonth, mDay);
                           	    	dialog2.getDatePicker().setMaxDate(new Date().getTime());
                            	   return dialog2;
                                   
                         }
                               
                        return null;
                        }
        
                           @Override
                       	public boolean onCreateOptionsMenu(Menu menu) {
                       							
                       			MenuInflater inflater = getMenuInflater();
                       			inflater.inflate(R.menu.menuitem, menu);
                       			
                       			menu.findItem(R.id.att).setVisible(false);
                       			
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
                					AttendanceActivity.this);

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
                							AttendanceActivity.this.finish();
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
                       	case R.id.info:
                       		nit = new Intent(this,MyInfo.class);
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
