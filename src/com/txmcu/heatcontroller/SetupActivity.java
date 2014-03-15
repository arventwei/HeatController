package com.txmcu.heatcontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.tendcloud.tenddata.TCAgent;
import com.txmcu.heatcontroller.HCApplication.StartModeType;

public class SetupActivity extends Activity {
	
	private HCApplication application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setup);
		
		this.application = ((HCApplication)getApplication());
		
		if(this.application.getModeType() == StartModeType.SMS)
		{
			
		}
		else {
			
		}
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
	
	public void onUserBound(View theButton) {
	    Intent localIntent1 = new Intent();
	    localIntent1.setClass(this, BindingActivity.class);
	    startActivity(localIntent1);
		
	}
	public void onStartMode(View theButton) {
	    Intent localIntent1 = new Intent();
	    localIntent1.setClass(this, ModeTypeActivity.class);
	    startActivity(localIntent1);
		
	}
	public void onSetPassword(View theButton) {
	    Intent localIntent1 = new Intent();
	    localIntent1.setClass(this, SetPwdActivity.class);
	    startActivity(localIntent1);
		
	}
	public void onAdvancedOption(View theButton) {
	    Intent localIntent1 = new Intent();
	    localIntent1.setClass(this, AdvancedActivity.class);
	    startActivity(localIntent1);
		
	}
	
}
