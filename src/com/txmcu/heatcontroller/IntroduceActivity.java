package com.txmcu.heatcontroller;

import android.webkit.WebView;

public class IntroduceActivity extends BaseActivity {

	@Override
	public int getViewResId() {
		// TODO Auto-generated method stub
		return R.layout.help;
	}

	@Override
	public int getTopOKTxtResId() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int getTitleTxtResId() {
		// TODO Auto-generated method stub
		return R.string.help_detail;
	}

	@Override
	public void onTopOkClick() {
		// TODO Auto-generated method stub

	}
	@Override
	public void onActivityCreate() {
		// TODO Auto-generated method stub
		WebView b = ((WebView)findViewById(R.id.webView));
		b.loadUrl("file:///android_asset/introduce_ch.html");
	}

}
