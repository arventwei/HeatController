package com.txmcu.heatcontroller;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tendcloud.tenddata.TCAgent;
import com.txmcu.telephone.SmsSender;

public class BindingActivity extends BaseActivity {
	
	
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

	private class ButtonState{
		int buttonImageId;
		int buttonOnId;
		public ButtonState(int imgId,int onId) {
			this.buttonImageId = imgId;
			this.buttonOnId    = onId;
		}
	}
	
	public class MyBindNoOnClickListener implements Button.OnClickListener
	   {

	     int carid;
	     int bindNo;
	     BindingActivity bindingActivity;
	     public MyBindNoOnClickListener(BindingActivity parent,int carid,int bindNo) {
	          this.carid = carid;
	          this.bindNo = bindNo;
	          this.bindingActivity = parent;
	          
	     }

	     @Override
	     public void onClick(View v)
	     {
	    	 bindingActivity.updateBoundButtonState(carid, bindNo);
	    	 //set when click sender button
	    	 //bindingActivity.application.setBoundNo(carid, bindNo);
	         //read your lovely variable
	     }

	  };
	  
	  public class MySenderOnClickListener implements Button.OnClickListener
	   {

	     int carid;
	     BindingActivity bindingActivity;
	     public MySenderOnClickListener(BindingActivity parent,int carid) {
	          this.carid = carid;
	          this.bindingActivity = parent;
	          
	     }

	     @Override
	     public void onClick(View v)
	     {
	 		//Log.d("aa", "bbbbbbbbbbb");
	 		// TODO Auto-generated method stub
	 	//	return R.string.setup_user_bound;
	 		String simNoString = bindingActivity.bindCars[carid].simEditText.getText().toString();
	 		bindingActivity.application.setSimNo(carid,simNoString);
	 		String pwdString = bindingActivity.bindCars[carid].pwdEditText.getText().toString();
	 		bindingActivity.application.setPassWord(carid,pwdString);
	 		bindingActivity.application.setBoundNo(carid,bindingActivity.bindCars[carid].curSelectedId);
	 		SmsSender.BoundingDevice(bindingActivity,carid);
	     }

	  };
	  
	private  ButtonState[] buttonStates={new ButtonState(R.drawable.btn_segment_left, R.drawable.segment_left_on),
			new ButtonState(R.drawable.btn_segment_middle, R.drawable.segment_middle_on),
			new ButtonState(R.drawable.btn_segment_right, R.drawable.segment_right_on)
			};
	@Override
	public int getViewResId() {
		// TODO Auto-generated method stub
		return R.layout.binding;
	}

	@Override
	public int getTopOKTxtResId() {
		// TODO Auto-generated method stub
		//return R.string.top_bound;
		return -1;
	}
	@Override
	public int getTitleTxtResId() {
		// TODO Auto-generated method stub
		return R.string.setup_user_bound;
	}
	
	@Override
	public void onTopOkClick() {
		//Log.d("aa", "bbbbbbbbbbb");
		// TODO Auto-generated method stub
	//	return R.string.setup_user_bound;
		//String simNoString = this.simEditText.getText().toString();
		//this.application.setSimNo(simNoString);
		//String pwdString = this.pwdEditText.getText().toString();
		//this.application.setPassWord(pwdString);
		//this.application.setBoundNo(curSelectedId);
		//SmsSender.BoundingDevice(this);
	}
	
	
	@Override
	public void onActivityCreate() {
		
		View subLayout1 = (View)findViewById(R.id.bind_car1);
		View subLayout2 = (View)findViewById(R.id.bind_car2);
		
		//bindCars[0].boundButtons
		// TODO Auto-generated method stub
		//int carid = 0;
		onCreateLayout(subLayout1, 0);
		onCreateLayout(subLayout2, 1);
		
		
		application = (HCApplication)getApplication();
		updateUI();
		
	}
	
	
	private void onCreateLayout(View subLayout, int carid) {
		assert(subLayout != null);
		assert(bindCars[carid] != null);
		if (bindCars[carid]==null) {
			bindCars[carid] = new bindCarInfo();
		}
		bindCars[carid].boundButtons[0] = (Button)subLayout.findViewById(R.id.btn_bound1);
		bindCars[carid].boundButtons[1] = (Button)subLayout.findViewById(R.id.btn_bound2);
		bindCars[carid].boundButtons[2] = (Button)subLayout.findViewById(R.id.btn_bound3);
		bindCars[carid].simEditText  = (EditText)subLayout.findViewById(R.id.binding_edit_sim);
		bindCars[carid].pwdEditText  = (EditText)subLayout.findViewById(R.id.binding_edit_pwd);
		TextView lbl = (TextView)subLayout.findViewById(R.id.label_bind_1);
		lbl.setText(String.format(getString(R.string.user_bound_num), carid+1));
		lbl = (TextView)subLayout.findViewById(R.id.label_bind_2);
		lbl.setText(String.format(getString(R.string.user_bound_sim), carid+1));
		lbl = (TextView)subLayout.findViewById(R.id.label_bind_3);
		lbl.setText(String.format(getString(R.string.user_bound_password), carid+1));
		
		bindCars[carid].boundButtons[0].setOnClickListener(new MyBindNoOnClickListener(this,carid,0));
		bindCars[carid].boundButtons[1].setOnClickListener(new MyBindNoOnClickListener(this,carid,1));
		bindCars[carid].boundButtons[2].setOnClickListener(new MyBindNoOnClickListener(this,carid,2));
		
		Button sendBtnButton = (Button)subLayout.findViewById(R.id.btn_bind_car);
		sendBtnButton.setOnClickListener(new MySenderOnClickListener(this,carid));
		
		sendBtnButton.setText(String.format(getString(R.string.user_bound_send), carid+1));
		sendBtnButton.requestFocus();
		
	}
	private void updateUI()
	{
		updateByCarId(0);
		updateByCarId(1);
		
	}
	private void updateByCarId(int carid) {
		int boundNo = application.getBoundNo(carid);
		
		boundNo = Math.min(boundNo, maxBoundCount-1);
		boundNo = Math.max(boundNo, 0);
		updateBoundButtonState(carid,boundNo);
		
		this.bindCars[carid].simEditText.setText(application.getSimNo(carid));
		this.bindCars[carid].pwdEditText.setText(application.getPassWord(carid));
	}
	private void updateBoundButtonState(int carid,int selectedId) {
		bindCars[carid].curSelectedId = selectedId;
		for (int i = 0; i < buttonStates.length; i++) {
			bindCars[carid].boundButtons[i].setBackgroundResource(buttonStates[i].buttonImageId);
		}
		bindCars[carid].boundButtons[selectedId].setBackgroundResource(buttonStates[selectedId].buttonOnId);
	}
//	public void onBound1Click(View theButton) {
//		updateBoundButtonState(0);
//		//application.setBoundNo(0);
//	}
//	public void onBound2Click(View theButton) {
//		updateBoundButtonState(1);
//		//application.setBoundNo(1);
//	}
//	public void onBound3Click(View theButton) {
//		updateBoundButtonState(2);
//		//application.setBoundNo(2);
//	}
	
	private  static int maxBoundCount = 3;
	public class bindCarInfo
	{
		public Button[] boundButtons = new Button[maxBoundCount];
		
		public EditText simEditText;
		public EditText pwdEditText;
		
		public HCApplication application;
		public int curSelectedId = 0;
	}
	
	private bindCarInfo[] bindCars = new bindCarInfo[HCApplication.maxCarCount];
/*	private Button[] boundButtons = new Button[3];
	
	private EditText simEditText;
	private EditText pwdEditText;
	*/
	private HCApplication application;
	//private int curSelectedId = 0;
	
}
