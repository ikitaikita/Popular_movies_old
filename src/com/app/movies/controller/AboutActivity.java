package com.app.movies.controller;


import com.app.movies.controller.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;


public class AboutActivity extends Activity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_aboutme);
		

	}

}
