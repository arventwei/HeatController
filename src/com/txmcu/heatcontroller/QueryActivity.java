package com.txmcu.heatcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.tendcloud.tenddata.TCAgent;
import com.txmcu.telephone.SmsSender;

public class QueryActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_query);
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
	
	public void onQuerySetting_car1(View theButton) {
		
		SmsSender.QuerySetting(this, 0);
	  //  Intent localIntent1 = new Intent();
	  //  localIntent1.setClass(this, QuerySettingActivity.class);
	  //  startActivity(localIntent1);
		
	}
	public void onQueryBound_car1(View theButton) {
		SmsSender.QueryBound(this, 0);
	  //  Intent localIntent1 = new Intent();
	  //  localIntent1.setClass(this, QueryBoundActivity.class);
	  //  startActivity(localIntent1);
		
	}
	
	public void onQuerySetting_car2(View theButton) {
		
		SmsSender.QuerySetting(this, 1);
		  //  Intent localIntent1 = new Intent();
		  //  localIntent1.setClass(this, QuerySettingActivity.class);
		  //  startActivity(localIntent1);
			
		}
		public void onQueryBound_car2(View theButton) {
			
			SmsSender.QueryBound(this, 1);
		  //  Intent localIntent1 = new Intent();
		  //  localIntent1.setClass(this, QueryBoundActivity.class);
		  //  startActivity(localIntent1);
			
		}
}
