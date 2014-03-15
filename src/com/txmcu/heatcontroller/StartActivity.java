package com.txmcu.heatcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tendcloud.tenddata.TCAgent;
import com.txmcu.heatcontroller.HCApplication.StartModeType;
import com.txmcu.telephone.SmsSender;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);
		
		this.application =(HCApplication)getApplication();
		
		this.titleTextView = (TextView)findViewById(R.id.start_title);
		this.smsInfoTextView = (TextView)findViewById(R.id.sms_info_runtime);
		this.smsLinearLayout = (LinearLayout)findViewById(R.id.sms_runtime);
		
		this.phoneTextView =(TextView)findViewById(R.id.phone_runtime);
		
		this.HeatButtons[0] = (Button)findViewById(R.id.btn_time1);
		this.HeatButtons[1] = (Button)findViewById(R.id.btn_time2);
		this.HeatButtons[2] = (Button)findViewById(R.id.btn_time3);
		this.HeatButtons[3] = (Button)findViewById(R.id.btn_time4);
		this.HeatButtons[4] = (Button)findViewById(R.id.btn_time5);
		
		this.CarButtons[0] = (Button)findViewById(R.id.btn_car1);
		this.CarButtons[1] = (Button)findViewById(R.id.btn_car2);
		
		
		
		
		
		updateUI();
	}
	private void updateUI( ) {
		
		int selectCarid = this.application.getSelCarNo();
		
		
		updateCarButtonState(selectCarid);
		
		int timeNo = application.getHeatTime(selectCarid);
		timeNo = Math.min(timeNo, this.HeatButtons.length);
		timeNo = Math.max(0, timeNo);
		updateHeatButtonState(timeNo);
		int minitues = application.getPhoneHeatTime(selectCarid);
		String phoneTextString = String.format(getResources().getString(R.string.start_phone_runtime), minitues);
		phoneTextView.setText(phoneTextString);
		if (this.application.getModeType() == StartModeType.SMS) {
			this.smsInfoTextView.setVisibility(View.VISIBLE);
			this.smsLinearLayout.setVisibility(View.VISIBLE);
			this.phoneTextView.setVisibility(View.GONE);
			this.titleTextView.setText(R.string.smsmode);
		}
		else {
			this.smsInfoTextView.setVisibility(View.GONE);
			this.smsLinearLayout.setVisibility(View.GONE);
			this.phoneTextView.setVisibility(View.VISIBLE);
			this.titleTextView.setText(R.string.phonemode);
		}
	}
	
	public void onCar1Click(View theButton) {
		//updateCarButtonState(0);
		this.application.setSelCarNo(0);
		updateUI();
		//int selectCarid = this.application.getSelCarNo();
		//application.setHeatTime(selectCarid,0);
	}
	public void onCar2Click(View theButton) {
		
		this.application.setSelCarNo(1);
		updateUI();
		//updateCarButtonState(0);
		//int selectCarid = this.application.getSelCarNo();
		//application.setHeatTime(selectCarid,0);
	}
	
	public void onTime1Click(View theButton) {
		updateHeatButtonState(0);
		int selectCarid = this.application.getSelCarNo();
		application.setHeatTime(selectCarid,0);
	}
	public void onTime2Click(View theButton) {
		updateHeatButtonState(1);
		int selectCarid = this.application.getSelCarNo();
		application.setHeatTime(selectCarid,1);
	}
	public void onTime3Click(View theButton) {
		updateHeatButtonState(2);
		int selectCarid = this.application.getSelCarNo();
		application.setHeatTime(selectCarid,2);
	}
	public void onTime4Click(View theButton) {
		updateHeatButtonState(3);
		int selectCarid = this.application.getSelCarNo();
		application.setHeatTime(selectCarid,3);
	}
	public void onTime5Click(View theButton) {
		updateHeatButtonState(4);
		int selectCarid = this.application.getSelCarNo();
		application.setHeatTime(selectCarid,4);
	}
	
	public void onStartClick(View theButton) {
		
		int selectCarid = this.application.getSelCarNo();
		
		if(application.getModeType()==StartModeType.SMS){
			
			int timeNo = application.getHeatTime(selectCarid);
			timeNo = Math.min(timeNo, this.HeatButtons.length);
			timeNo = Math.max(0, timeNo);
			int minitues = HCApplication.heatTimeList[timeNo];
			SmsSender.SetSmsStart(this,selectCarid, minitues);
			TCAgent.onEvent(this, "startmode sms");
		}
		else {
			TCAgent.onEvent(this, "startmode phone");
			SmsSender.call(this,selectCarid);
			
		}
	}
	public void onStopClick(View theButton) {
		
		int selectCarid = this.application.getSelCarNo();
		
		if(application.getModeType()==StartModeType.SMS){
			
			//!TODO
			//Toast.makeText(this,com.txmcu.heatcontroller.R.string.function_limited, 
	        //        Toast.LENGTH_SHORT).show();
			
			SmsSender.SetSmsStop(this,selectCarid);
		}
		else {
			SmsSender.call(this,selectCarid);
		}
	}
	
	@Override
	protected void onResume (){
		super.onResume();
		TCAgent.onResume (this);
		updateUI();
	}
	@Override
	protected void onPause (){
		super.onPause();
		TCAgent.onPause(this);
	}
	
	private void updateCarButtonState(int selectedId) {
		for (int i = 0; i < carbuttonStates.length; i++) {
			this.CarButtons[i].setBackgroundResource(carbuttonStates[i].buttonImageId);
		}
		this.CarButtons[selectedId].setBackgroundResource(carbuttonStates[selectedId].buttonOnId);
	}
	
	private void updateHeatButtonState(int selectedId) {
		for (int i = 0; i < buttonStates.length; i++) {
			this.HeatButtons[i].setBackgroundResource(buttonStates[i].buttonImageId);
		}
		this.HeatButtons[selectedId].setBackgroundResource(buttonStates[selectedId].buttonOnId);
	}
	
	private TextView titleTextView;
	
	private TextView smsInfoTextView;
	private LinearLayout smsLinearLayout;
	
	private TextView phoneTextView;
	
	private HCApplication application;
	
	private Button[] HeatButtons = new Button[HCApplication.heatTimeList.length];
	private Button[] CarButtons = new Button[HCApplication.maxCarCount];
	
	private class ButtonState{
		int buttonImageId;
		int buttonOnId;
		public ButtonState(int imgId,int onId) {
			this.buttonImageId = imgId;
			this.buttonOnId    = onId;
		}
	}
	
	private ButtonState[] buttonStates={
			new ButtonState(R.drawable.btn_segment_left, R.drawable.segment_left_on),
			new ButtonState(R.drawable.btn_segment_middle, R.drawable.segment_middle_on),
			new ButtonState(R.drawable.btn_segment_middle, R.drawable.segment_middle_on),
			new ButtonState(R.drawable.btn_segment_middle, R.drawable.segment_middle_on),
			new ButtonState(R.drawable.btn_segment_right, R.drawable.segment_right_on)
			};
	
	private ButtonState[] carbuttonStates={
			new ButtonState(R.drawable.btn_segment_left, R.drawable.segment_left_on),
	
			new ButtonState(R.drawable.btn_segment_right, R.drawable.segment_right_on)
			};
}
