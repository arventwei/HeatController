package com.txmcu.heatcontroller;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.tendcloud.tenddata.TCAgent;
import com.txmcu.telephone.SmsSender;

public class AdvancedActivity extends BaseActivity {

	@Override
	public int getViewResId() {
		// TODO Auto-generated method stub
		return R.layout.advancedoption;
	}

	@Override
	public int getTopOKTxtResId() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int getTitleTxtResId() {
		// TODO Auto-generated method stub
		return R.string.setup_advanced_option;
	}

	@Override
	public void onTopOkClick() {
		// TODO Auto-generated method stub
		
	}
	
	public class AdvanceCarInfo
	{
		public EditText editPhonEditText;
		public Button editAutoStartTimeEditText;
		public int      autoStartHour,autoStartMin;
		public ToggleButton toggleAutoStartEnable;
		
		public Button[] sendButtons = new Button[4];
	}
	
	
	 public class PhoneHeatTimeSenderOnClickListener implements Button.OnClickListener
	   {

	     int carid;
	     AdvancedActivity activity;
	     public PhoneHeatTimeSenderOnClickListener(AdvancedActivity parent,int carid) {
	          this.carid = carid;
	          this.activity = parent;
	          
	     }

	     @Override
	     public void onClick(View v)
	     {
	    	String tempString = activity.advanceCarInfos[carid].editPhonEditText.getText().toString();
	 		int phoneHeatTime = Integer.valueOf(tempString).intValue();  
	 		SmsSender.SetPhoneHeatTime(activity, carid,phoneHeatTime);
	     }

	  };
	 public class AutoStartTimeSenderOnClickListener implements Button.OnClickListener
	   {

	     int carid;
	     AdvancedActivity activity;
	     public AutoStartTimeSenderOnClickListener(AdvancedActivity parent,int carid) {
	          this.carid = carid;
	          this.activity = parent;
	          
	     }

	     @Override
	     public void onClick(View v)
	     {
	    	 SmsSender.SetAutoStartTime(activity,carid, activity.advanceCarInfos[carid].autoStartHour, 
	    			 activity.advanceCarInfos[carid].autoStartMin);
	    	//String tempString = activity.advanceCarInfos[carid].editPhonEditText.getText().toString();
	 		//int phoneHeatTime = Integer.valueOf(tempString).intValue();  
	 		//SmsSender.SetPhoneHeatTime(activity, carid,phoneHeatTime);
	     }

	  };
	  public class AutoStartEnableSenderOnClickListener implements Button.OnClickListener
	   {

	     int carid;
	     AdvancedActivity activity;
	     public AutoStartEnableSenderOnClickListener(AdvancedActivity parent,int carid) {
	          this.carid = carid;
	          this.activity = parent;
	          
	     }

	     @Override
	     public void onClick(View v)
	     {
	    	 
	    	 SmsSender.SetAutoStartEnable(activity,carid,activity.advanceCarInfos[carid].toggleAutoStartEnable.isChecked());

	     }

	  };
	  public class RecoverySenderOnClickListener implements Button.OnClickListener
	   {

	     int carid;
	     AdvancedActivity activity;
	     public RecoverySenderOnClickListener(AdvancedActivity parent,int carid) {
	          this.carid = carid;
	          this.activity = parent;
	          
	     }

	     @Override
	     public void onClick(View v)
	     {
	    	 SmsSender.SetRecovery(activity,carid);
	    	// SmsSender.SetAutoStartEnable(activity,carid,activity.advanceCarInfos[carid].toggleAutoStartEnable.isChecked());

	     }

	  };
	  
	  public class SelectTimeOnClickListener implements Button.OnClickListener
	   {

	     int carid;
	     AdvancedActivity activity;
	     public SelectTimeOnClickListener(AdvancedActivity parent,int carid) {
	          this.carid = carid;
	          this.activity = parent;
	          
	     }

	     @Override
	     public void onClick(View v)
	     {
	    	 
	    	 new TimePickerDialog(activity, new MyTimeSetListener(activity,carid), activity.advanceCarInfos[carid].autoStartHour, activity.advanceCarInfos[carid].autoStartMin, true).show();
	    	 //SmsSender.SetRecovery(activity,carid);
	    	// SmsSender.SetAutoStartEnable(activity,carid,activity.advanceCarInfos[carid].toggleAutoStartEnable.isChecked());

	     }

	  };
	  
	  class MyTimeSetListener implements TimePickerDialog.OnTimeSetListener
	   {

	     int carid;
	     AdvancedActivity activity;
	     public MyTimeSetListener(AdvancedActivity parent,int carid) {
	          this.carid = carid;
	          this.activity = parent;
	          
	     }

	     @Override
		    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		       // dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
		       // dateTime.set(Calendar.MINUTE,minute);
		    	advanceCarInfos[carid].autoStartHour = hourOfDay;
		    	advanceCarInfos[carid].autoStartMin = minute;
		    	activity.updateUI();
		    }

	  };
	  
	@Override
	public void onActivityCreate() {
		// TODO Auto-generated method stub
		
		application = (HCApplication)getApplication();
		
		View subLayout1 = (View)findViewById(R.id.advance_opt_car1);
		View subLayout2 = (View)findViewById(R.id.advance_opt_car2);
		
		onCreateLayout(subLayout1,0);
		onCreateLayout(subLayout2,1);
		
		
		updateUI();
		
	}
	private void onCreateLayout(View subLayout, int carid) {
		assert(subLayout != null);
		assert(advanceCarInfos[carid] != null);
		if (advanceCarInfos[carid]==null) {
			advanceCarInfos[carid] = new AdvanceCarInfo();
		}
		
		
		advanceCarInfos[carid].sendButtons[0] = (Button)subLayout.findViewById(R.id.btn_phoneHeatTime);
		advanceCarInfos[carid].sendButtons[1] = (Button)subLayout.findViewById(R.id.btn_autoStartTime);
		advanceCarInfos[carid].sendButtons[2] = (Button)subLayout.findViewById(R.id.btn_enable_timer);
		advanceCarInfos[carid].sendButtons[3] = (Button)subLayout.findViewById(R.id.btn_bind_car);
		
		advanceCarInfos[carid].editPhonEditText = (EditText)subLayout.findViewById(R.id.editPhoneHeatTime);
		advanceCarInfos[carid].editAutoStartTimeEditText = (Button)subLayout.findViewById(R.id.editAutoStartTime);
		advanceCarInfos[carid].toggleAutoStartEnable     = (ToggleButton)subLayout.findViewById(R.id.toggleAutoStartEnable);
		//editAutoStopTempEditText  = (EditText)findViewById(R.id.editAutoStopTemp);
		//toggleAutoStopEnable     = (ToggleButton)findViewById(R.id.toggleAutoStopEnable);
		advanceCarInfos[carid].editAutoStartTimeEditText.setOnClickListener(new SelectTimeOnClickListener(this,carid));
		
		
		advanceCarInfos[carid].autoStartHour = application.getAutoStartTimeHour(carid);
		advanceCarInfos[carid].autoStartMin  = application.getAutoStartTimeMin(carid);
		
		
		TextView lbl = (TextView)subLayout.findViewById(R.id.advance_text1);
		lbl.setText(String.format(getString(R.string.heat_time_info), carid+1));
		lbl = (TextView)subLayout.findViewById(R.id.advance_text2);
		lbl.setText(String.format(getString(R.string.heat_time_start_label), carid+1));
		lbl = (TextView)subLayout.findViewById(R.id.advance_text3);
		lbl.setText(String.format(getString(R.string.auto_start_enable), carid+1));
		lbl = (TextView)subLayout.findViewById(R.id.advance_text4);
		lbl.setText(String.format(getString(R.string.recovery), carid+1));
		
		
		advanceCarInfos[carid].sendButtons[0].setOnClickListener(new PhoneHeatTimeSenderOnClickListener(this,carid));
		advanceCarInfos[carid].sendButtons[1].setOnClickListener(new AutoStartTimeSenderOnClickListener(this,carid));
		advanceCarInfos[carid].sendButtons[2].setOnClickListener(new AutoStartEnableSenderOnClickListener(this,carid));
		advanceCarInfos[carid].sendButtons[3].setOnClickListener(new RecoverySenderOnClickListener(this,carid));
		//btn_phoneHeatTime
		
		
		
	}
	private void updateUI() {
		
		
		updateLayoutByCarId(0);
		updateLayoutByCarId(1);

	}

	private void updateLayoutByCarId(int carid) {
		String heatTiString = String.format("%d", application.getPhoneHeatTime(carid));
		advanceCarInfos[carid].editPhonEditText.setText(heatTiString);
		String autoStartString = String.format("%02d:%02d", advanceCarInfos[carid].autoStartHour,advanceCarInfos[carid].autoStartMin);
		advanceCarInfos[carid].editAutoStartTimeEditText.setText(autoStartString);
		advanceCarInfos[carid].toggleAutoStartEnable.setChecked(application.getAutoStartTimeEnable(carid));
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
	
//	public void onClickPhoneHeatTime(View theButtonView)
//	{
//		String tempString = editPhonEditText.getText().toString();
//		int phoneHeatTime = Integer.valueOf(tempString).intValue();  
//		SmsSender.SetPhoneHeatTime(this, 0,phoneHeatTime);
//	}
	
	//DateFormat formatDateTime=DateFormat.getDateTimeInstance();
	//Calendar dateTime=Calendar.getInstance();
	
	
//	TimePickerDialog.OnTimeSetListener car0timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//	    @Override
//	    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//	       // dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
//	       // dateTime.set(Calendar.MINUTE,minute);
//	    	advanceCarInfos[0].autoStartHour = hourOfDay;
//	    	advanceCarInfos[0].autoStartMin = minute;
//	    	updateUI();
//	    }
//	};
//	TimePickerDialog.OnTimeSetListener car1timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//	    @Override
//	    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//	       // dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
//	       // dateTime.set(Calendar.MINUTE,minute);
//	    	advanceCarInfos[1].autoStartHour = hourOfDay;
//	    	advanceCarInfos[1].autoStartMin = minute;
//	    	updateUI();
//	    }
//	};
	
	
//	public void onPickAutoStartTime(View theButtonView)
//	{
//		 new TimePickerDialog(this, new MyTimeSetListener(this,carid), autoStartHour, autoStartMin, true).show();
//	}
//	public void onClickAutoStartTimeSend(View theButton)
//	{
//		//!TODO
//		Toast.makeText(this,com.txmcu.heatcontroller.R.string.function_limited, 
//                Toast.LENGTH_SHORT).show();
//		
//		//SmsSender.SetAutoStartTime(this, autoStartHour, autoStartMin);
//	}
	
//	public void onClickAutoStartEnableSend(View theButton)
//	{
//		//!TODO
//		Toast.makeText(this,com.txmcu.heatcontroller.R.string.function_limited, 
//                Toast.LENGTH_SHORT).show();
//		
//		//SmsSender.SetAutoStartEnable(this,toggleAutoStartEnable.isChecked());
//	}
//	public void onClickAutoStopTemp(View theButton)
//	{
//		//!TODO
//		Toast.makeText(this,com.txmcu.heatcontroller.R.string.function_limited, 
//                Toast.LENGTH_SHORT).show();
//		
//		//String tempString = editAutoStopTempEditText.getText().toString();
//		//int autoStopTemp = Integer.valueOf(tempString).intValue();  
//		//SmsSender.SetAutoStopTemp(this,autoStopTemp);
//	}
//	
//	public void onClickAutoStopEnableSend(View theButton)
//	{
//		//!TODO
//		Toast.makeText(this,com.txmcu.heatcontroller.R.string.function_limited, 
//                Toast.LENGTH_SHORT).show();
//		
//		//SmsSender.SetAutoStopEnable(this,toggleAutoStopEnable.isChecked());
//	}
//	public void onClickCalibrateTimeSend(View theButton)
//	{
//		//!TODO
//		Toast.makeText(this,com.txmcu.heatcontroller.R.string.function_limited, 
//                Toast.LENGTH_SHORT).show();
//		
//		//SmsSender.SetCalibrateTime(this);
//	}
//	public void onClickRecoverySend(View theButton)
//	{
//		SmsSender.SetRecovery(this,0);
//	}

//	private EditText editPhonEditText;
//	private Button editAutoStartTImeEditText;
//	private int      autoStartHour,autoStartMin;
//	private ToggleButton toggleAutoStartEnable;
	//private EditText editAutoStopTempEditText;
	//private ToggleButton toggleAutoStopEnable;
	
	public AdvanceCarInfo[] advanceCarInfos = new AdvanceCarInfo[HCApplication.maxCarCount];
	
	private HCApplication application;
}
