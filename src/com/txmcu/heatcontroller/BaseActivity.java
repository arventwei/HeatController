package com.txmcu.heatcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public abstract class BaseActivity extends Activity {
	
	  //private Button topBackButton;
	  private Button topOKButton;
	  private TextView topTitleTextView;
	  private FrameLayout clientFrameLayout;
	  private LayoutInflater layoutInflater;
	  
	  
	  public abstract int getViewResId();
	  
	  public abstract int getTopOKTxtResId();
	  
	  public abstract int getTitleTxtResId();
	  
	  public abstract void onTopOkClick();
	  
	  public abstract void onActivityCreate();
	  
	  protected void onCreate(Bundle paramBundle)
	  {
	    super.onCreate(paramBundle);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    if (this.layoutInflater == null)
	    	this.layoutInflater = LayoutInflater.from(getBaseContext());
	    setContentView(R.layout.activity_base);
	    this.clientFrameLayout = ((FrameLayout)findViewById(R.id.activity_client));
	    //this.topBackButton = ((Button)findViewById(2131165186));
	    this.topOKButton = ((Button)findViewById(R.id.btn_base_enter));
	    this.topTitleTextView = ((TextView)findViewById(R.id.txt_base_title));
	      //this.b.setOnClickListener(new b(this));
	    if (getTopOKTxtResId() != -1) 
	    {
	    	topOKButton.setVisibility(View.VISIBLE);
	    	topOKButton.setText(getTopOKTxtResId());
		}
	    else {
			topOKButton.setVisibility(View.GONE);
		}
	    
	    if (getTitleTxtResId() != -1) {
			this.topTitleTextView.setText(getTitleTxtResId());
		}
	      
	     
		if (getViewResId() != -1)
		{
			View localView = this.layoutInflater.inflate(getViewResId(), null);
		    if ((localView != null) && (this.clientFrameLayout.getChildCount() > 0))
		        this.clientFrameLayout.removeAllViews();
		    this.clientFrameLayout.addView(localView);
		}
		
		onActivityCreate();
	  }
	  
	  public void onClickBack(View theButton) {
		  //Log.d("onclick", "button back");
		  this.finish();
		  //this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_BACK));
	  }
	  public void  onClickOK(View theButton) {
		  onTopOkClick();
		
	}
	  
}
