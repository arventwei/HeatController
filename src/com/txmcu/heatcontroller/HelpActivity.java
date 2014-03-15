package com.txmcu.heatcontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.tendcloud.tenddata.TCAgent;

public class HelpActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_help);
	}
	
	@Override
	protected void onResume (){
		super.onResume();
		TCAgent.onResume (this);
	}
	@Override
	protected void onPause (){
		super.onPause();
		TCAgent.onPause(this);
	}
	
	  public void onClick(View paramView)
	  {
	    switch (paramView.getId())
	    {
	    default:
	      return;
	    case R.id.btn_quickstart:
	      startActivity(new Intent(this, FastStartActivity.class));
	      return;
	    case R.id.btn_detail:
	      startActivity(new Intent(this, IntroduceActivity.class));
	      return;
	    case R.id.btn_contactus:
	    	startActivity(new Intent(this, ContactActivity.class));
	    	return;
	    }
	    
	  }
	  
}
