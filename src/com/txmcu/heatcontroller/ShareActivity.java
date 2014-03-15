package com.txmcu.heatcontroller;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.txmcu.heatcontroller.R;
public class ShareActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_share);
		ShareSDK.initSDK(this);
		
	}
	
	
	protected void onDestroy() {
		ShareSDK.stopSDK(this);
		super.onDestroy();
	}
	public void onShareClick(View view)
	{
		showShare(false,null);
	}
	
	
	// ʹ�ÿ�ݷ�����ɷ��?�������ϸ�Ķ�λ��SDK��ѹĿ¼��Docs�ļ�����OnekeyShare���JavaDoc��
		private void showShare(boolean silent, String platform) {
			final OnekeyShare oks = new OnekeyShare();
			oks.setNotification(R.drawable.ic_launcher, this.getString(R.string.app_name));
			//oks.setAddress("www.txmcu.com");
			oks.setTitle(this.getString(R.string.share));
			oks.setTitleUrl(this.getString(R.string.share_url));
			oks.setText(this.getString(R.string.share_value));
			//oks.setImagePath(MainActivity.TEST_IMAGE);
			//oks.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/25/1374723172663.jpg");
			oks.setUrl(this.getString(R.string.share_url));
			//oks.setFilePath(MainActivity.TEST_IMAGE);
			oks.setComment(this.getString(R.string.share));
			oks.setSite(this.getString(R.string.app_name));
			oks.setSiteUrl(this.getString(R.string.share_url));
			oks.setVenueName(this.getString(R.string.app_name));
			oks.setVenueDescription(this.getString(R.string.app_name));
			oks.setLatitude(23.122619f);
			oks.setLongitude(113.372338f);
			oks.setSilent(silent);
			if (platform != null) {
				oks.setPlatform(platform);
			}

			// ȥ��ע�ͣ�����༭ҳ����ʾΪDialogģʽ
//			oks.setDialogMode();

			// ȥ��ע�ͣ����ݷ���Ĳ������ͨ��OneKeyShareCallback�ص�
			oks.setCallback(new OneKeyShareCallback());
//			oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

			// ȥ��ע�ͣ���ʾ�ھŹ��������Զ����ͼ��
//			Bitmap logo = BitmapFactory.decodeResource(menu.getResources(), R.drawable.ic_launcher);
//			String label = menu.getResources().getString(R.string.app_name);
//			OnClickListener listener = new OnClickListener() {
//				public void onClick(View v) {
//					String text = "Customer Logo -- Share SDK " + ShareSDK.getSDKVersionName();
//					Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
//					oks.finish();
//				}
//			};
//			oks.setCustomerLogo(logo, label, listener);

			oks.show(this);
		}

}
