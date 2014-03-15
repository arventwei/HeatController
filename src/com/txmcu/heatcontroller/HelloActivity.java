package com.txmcu.heatcontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;


public class HelloActivity  extends Activity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hello);
		
		
		new Handler().postDelayed(new Runnable() {
            
		      @Override
		      public void run() {
		            // TODO Auto-generated method stub
		            Intent intent = new Intent(HelloActivity.this,MainActivity.class);
		            startActivity(intent);
		            HelloActivity.this.finish();
		      }
		},2000);
	}
}
