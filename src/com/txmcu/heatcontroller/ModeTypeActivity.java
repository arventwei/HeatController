package com.txmcu.heatcontroller;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.txmcu.heatcontroller.HCApplication.StartModeType;

public class ModeTypeActivity extends BaseActivity {

	@Override
	public int getViewResId() {
		// TODO Auto-generated method stub
		return R.layout.modetype;
	}

	@Override
	public int getTopOKTxtResId() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int getTitleTxtResId() {
		// TODO Auto-generated method stub
		return R.string.setup_start_mode;
	}

	@Override
	public void onTopOkClick() {
		// TODO Auto-generated method stub

	}
	@Override
	public void onActivityCreate() {
		// TODO Auto-generated method stub
		this.smsButton = (Button)this.findViewById(R.id.btn_sms);
		this.phoneButton = (Button)this.findViewById(R.id.btn_phone);
		
		this.application = (HCApplication)getApplication();
		if (this.application.getModeType() == StartModeType.SMS) {
			this.smsButton.setBackgroundResource(R.drawable.check_up_on);
		}
		else {
			this.phoneButton.setBackgroundResource(R.drawable.check_down_on);
		}
		Log.d("onActivityCreate", "ModeType");
	}
	
	private Button smsButton;
	private Button phoneButton;
	private HCApplication application;
	
	public void onSelectSms(View theButton) {
		
		Log.d("sms", "sms");
		this.smsButton.setBackgroundResource(R.drawable.check_up_on);
		this.phoneButton.setBackgroundResource(R.drawable.btn_check_down);
		this.application.setModeType(StartModeType.SMS);
		//this.smsButton.setPressed(true);
		//this.phoneButton.setPressed(false);
	}
	
	public void onSelectPhone(View theButton) {
		Log.d("phone", "phone");
		this.smsButton.setBackgroundResource(R.drawable.btn_check_up);
		this.phoneButton.setBackgroundResource(R.drawable.check_down_on);
		this.application.setModeType(StartModeType.Phone);
	}
}
