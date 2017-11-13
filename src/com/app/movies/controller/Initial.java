package com.app.movies.controller;







import com.app.movies.controller.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Initial extends Activity {
	private static final int MENU_ABOUT = Menu.FIRST;
	private static final int MENU_EXIT = 2;
	Button BtnList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);
	
		BtnList = (Button) findViewById(R.id.buttonList);
	
		
	
		BtnList.setOnClickListener(new View.OnClickListener(){		      
		     public void onClick(View view){
		    	 Intent intent_movies_list = new Intent(Initial.this, MoviesList.class);
			        startActivity(intent_movies_list);
		     }
		});
		

		
	
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.inicio, menu);
		menu.add(0, MENU_ABOUT, 0, getString(R.string.about));
		menu.add(0, MENU_EXIT, 1, getString(R.string.exit));
		
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_ABOUT:
				About();
				return true;
			case MENU_EXIT:
				ExitApp();
				return true;
			
		}
		return false;
	}
	public void About(){
		Intent intent = new Intent(this, AboutActivity.class);
		this.startActivity(intent);						
	}
	public void ExitApp()
	{
		finish();
	}


}
