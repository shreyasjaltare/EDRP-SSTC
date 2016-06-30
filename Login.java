package com.edrp.edrp;

import dataretrival.Student;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class Login extends Activity {

	Button bt;
	String pwd;
	public String sid;
	EditText id,pass;
	SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		id = (EditText)findViewById(R.id.sid);
		pass=(EditText)findViewById(R.id.pass);
		bt = (Button)findViewById(R.id.login);
		
		
		StrictMode.enableDefaults();
		bt.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
		
	// On empty fields			
				
				if(id.getText().toString().isEmpty()){
					
					Toast.makeText(getApplicationContext(), "Enter Student ID", Toast.LENGTH_SHORT).show();
					
				}
				
				else if(pass.getText().toString().isEmpty()){
					
					Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
					
				}
				
				else{
					
					Student s = new Student();
					sid = id.getText().toString().toUpperCase();	
					pwd = pass.getText().toString();
				
					String str=s.verifyPassword(sid,pwd);
					
					if(str.equals("loggin")){
				
						pref = getSharedPreferences("edrp",0);
						Editor edit = pref.edit();
						edit.putString("uid", sid);
						edit.putString("pass", pwd);
						edit.commit();
						
						Intent it = new Intent(Login.this, MainMenu.class);
						startActivity(it);
						finish();
					}

					else {
				
						Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
					}
				
				}
			
			}
		});
		
		
	}
		@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

		
	public void onBackPressed(){
		
		System.exit(0);		
	}
	
	public void onResume(){
		
		pref = getSharedPreferences("edrp",0 );
		
		if(pref.contains("uid"))
		{
			if(pref.contains("pass")){
				
				Intent i = new Intent(Login.this,MainMenu.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
				
			}
		}
		
		super.onResume();
	}
	
}
