package com.txmcu.heatcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.tendcloud.tenddata.TCAgent;



public class MainActivity extends TabActivity  implements OnTabChangeListener {
	
	private class TabContent
	{ 
		public int imageBtnId;
		public int imageOnId;
		public int textResId;
		public Class<?> classType;
		public TabContent(int imageId,int imageOnId,int textId,Class<?> classType)
		{
			this.imageBtnId = imageId;
			this.imageOnId  = imageOnId;
			this.textResId  = textId;
			this.classType = classType;
			
		}
	}
	private List<TabContent> tabContents = new ArrayList<MainActivity.TabContent>();
	
	//private TabHost tabs;
	//private TabWidget widgets;
	private HCApplication application;
    private int lastSelectTabIndex = 0;
	
	private void AddTabButton(int btnImageResId,int btnStrResId, Class<?> paramClass, int tabIndex)
	  {
	    LinearLayout tabBtnLayout = (LinearLayout)LayoutInflater.from(this).inflate(R.layout.tab_button, getTabHost().getTabWidget(), false);
	    ImageView localImageView = (ImageView)tabBtnLayout.findViewById(R.id.tab_icon);
	    TextView localTextView = (TextView)tabBtnLayout.findViewById(R.id.tab_icon_txt);
	   
	    //for (String str = (String)e.get(Byte.valueOf(paramByte)); ; str = (String)d.get(Byte.valueOf(paramByte)))
	   // {
	      localTextView.setText(btnStrResId);
	      localTextView.setTag(tabIndex);
	      //this.f.add(localTextView);
	      localImageView.setImageResource(btnImageResId);
	      TabHost.TabSpec spec = getTabHost().newTabSpec(String.valueOf(tabIndex));
	      spec.setIndicator(tabBtnLayout);
	      spec.setContent(new Intent(this, paramClass));
	      getTabHost().addTab(spec);
	    //  return;
	    //}
	  }
	
	 public final void SelectTab(int tabIndex)
	  {
		 
		TabHost tabs = this.getTabHost();
		TabWidget widgets = tabs.getTabWidget();
		TabContent lasttabContent = tabContents.get(lastSelectTabIndex);
		TabContent tabContent = tabContents.get(tabIndex);
		
	    ((ImageView)widgets.getChildAt(lastSelectTabIndex).findViewById(R.id.tab_icon)).setImageResource(lasttabContent.imageBtnId);
	    
	    ImageView localImageView = (ImageView)widgets.getChildAt(tabIndex).findViewById(R.id.tab_icon);
	    localImageView.setImageResource(tabContent.imageOnId);
	    
	    TextView titleBarTextView = ((TextView)findViewById(R.id.top_bar));
	    
	    titleBarTextView.setText(tabContent.textResId);
	   
	    tabs.setCurrentTab(tabIndex);
	    lastSelectTabIndex = tabIndex;
	  
	  }
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		tabContents.add(new TabContent(R.drawable.btn_setup_selector,R.drawable.icon_setup_on,R.string.setup,SetupActivity.class));
		tabContents.add(new TabContent(R.drawable.btn_query_selector,R.drawable.icon_query_on,R.string.query,QueryActivity.class));
		tabContents.add(new TabContent(R.drawable.btn_start_selector,R.drawable.icon_start_on,R.string.start,StartActivity.class));
		tabContents.add(new TabContent(R.drawable.btn_share_selector,R.drawable.icon_share_on,R.string.main_share,ShareActivity.class));
		tabContents.add(new TabContent(R.drawable.btn_help_selector,R.drawable.icon_help_on,R.string.help,HelpActivity.class));
		
		
		for (int i = 0; i < tabContents.size(); i++) {
			TabContent tabContent = tabContents.get(i);
			AddTabButton(tabContent.imageBtnId,tabContent.textResId,tabContent.classType,i);
		}
		getTabHost().setOnTabChangedListener(this);
		SelectTab(2);
		//AddTabButton(R.drawable.btn_setup_selector,R.string.setup,SetupActivity.class,0);
		//AddTabButton(R.drawable.btn_query_selector,R.string.query,QueryActivity.class,1);
		//AddTabButton(R.drawable.btn_start_selector,R.string.start,StartActivity.class,2);
		//AddTabButton(R.drawable.btn_share_selector,R.string.share,ShareActivity.class,3);
		//AddTabButton(R.drawable.btn_help_selector, R.string.help,HelpActivity.class,4);
	    //this.tabs = getTabHost();
	    //this.widgets = this.tabs.getTabWidget();
	    this.application = ((HCApplication)getApplication());
	    
	    
	   // List<String> aList = SmsSender.getSMS(this);
	  //  for (String string : aList) {
		//	Log.d("sms", string);
		//}
	    
	}

	@Override
	public void onTabChanged(String tabId)
	{
		int curTab = Integer.parseInt(tabId);
		SelectTab(curTab);
		
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
	
	

}
