package com.txmcu.heatcontroller;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.txmcu.telephone.SmsSender;

public class SetPwdActivity extends BaseActivity {

	@Override
	public int getViewResId() {
		// TODO Auto-generated method stub
		return R.layout.setpassword;
	}

	@Override
	public int getTopOKTxtResId() {
		// TODO Auto-generated method stub
		//return R.string.send;
		return -1;
	}

	@Override
	public int getTitleTxtResId() {
		// TODO Auto-generated method stub
		return R.string.setup_set_password;
	}

	@Override
	public void onTopOkClick() {
		
		
		// TODO Auto-generated method stub
		
		
		
	}

	public class SetPwdSenderOnClickListener implements Button.OnClickListener
	   {

	     int carid;
	     SetPwdActivity activity;
	     public SetPwdSenderOnClickListener(SetPwdActivity parent,int carid) {
	          this.carid = carid;
	          this.activity = parent;
	          
	     }

	     @Override
	     public void onClick(View v)
	     {
	    	String oldString = setPwdInfos[carid].editold.getText().toString();
	 		String newString =  setPwdInfos[carid].editnew.getText().toString();
	 		String confirmString =  setPwdInfos[carid].editconfirm.getText().toString();
	 		if (!newString.equals(confirmString)) {
	 			Toast.makeText(activity,com.txmcu.heatcontroller.R.string.sms_pwd_wrong_confrm, 
	                     Toast.LENGTH_SHORT).show();
	 			return;
	 		}
	 		
	 		SmsSender.ChangePassWord(activity, carid,oldString, newString);

	     }

	  };
	  
	public class SetPwdInfo
	{
		public TextView infolbl;
		public Button   sender;
		public EditText editold;
		public EditText editnew;
		public EditText editconfirm;
	}
	@Override
	public void onActivityCreate() {
		// TODO Auto-generated method stub
		View subLayout1 = (View)findViewById(R.id.setpwd_car1);
		View subLayout2 = (View)findViewById(R.id.setpwd_car2);
		onCreateLayout(subLayout1,0);
		onCreateLayout(subLayout2,1);
	}
	
	private void onCreateLayout(View subLayout, int carid) {
		assert(subLayout != null);
		assert(setPwdInfos[carid] != null);
		if (setPwdInfos[carid]==null) {
			setPwdInfos[carid] = new SetPwdInfo();
		}
		
		
		setPwdInfos[carid].editold = (EditText)subLayout.findViewById(R.id.editoldpwd);
		setPwdInfos[carid].editnew = (EditText)subLayout.findViewById(R.id.editnewpwd);
		setPwdInfos[carid].editconfirm = (EditText)subLayout.findViewById(R.id.editconfirm);
		
		setPwdInfos[carid].sender= (Button)subLayout.findViewById(R.id.btn_setpwd_car);
		setPwdInfos[carid].infolbl = (TextView)subLayout.findViewById(R.id.setpwd_info_label);
		
		
		setPwdInfos[carid].infolbl.setText(String.format(getString(R.string.setpassword_label), carid+1));
		setPwdInfos[carid].sender.setText(String.format(getString(R.string.setpassword_sender), carid+1));
		
		
		setPwdInfos[carid].sender.setOnClickListener(new SetPwdSenderOnClickListener(this,carid));
	}
	
	public SetPwdInfo[] setPwdInfos = new SetPwdInfo[HCApplication.maxCarCount];
	
}
