/*
 * 瀹樼綉鍦扮珯:http://www.ShareSDK.cn
 * 鎶�湳鏀寔QQ: 4006852216
 * 瀹樻柟寰俊:ShareSDK   锛堝鏋滃彂甯冩柊鐗堟湰鐨勮瘽锛屾垜浠皢浼氱涓�椂闂撮�杩囧井淇″皢鐗堟湰鏇存柊鍐呭鎺ㄩ�缁欐偍銆傚鏋滀娇鐢ㄨ繃绋嬩腑鏈変换浣曢棶棰橈紝涔熷彲浠ラ�杩囧井淇′笌鎴戜滑鍙栧緱鑱旂郴锛屾垜浠皢浼氬湪24灏忔椂鍐呯粰浜堝洖澶嶏級
 *
 * Copyright (c) 2013骞�ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import com.txmcu.heatcontroller.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.os.Handler.Callback;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import cn.sharesdk.framework.FakeActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;

/**
 * 蹇嵎鍒嗕韩鐨勫叆鍙� * <p>
 * 閫氳繃涓嶅悓鐨剆etter璁剧疆鍙傛暟锛岀劧鍚庤皟鐢▄@link #show(Context)}鏂规硶鍚姩蹇嵎鍒嗕韩
 */
public class OnekeyShare extends FakeActivity implements
		OnClickListener, PlatformActionListener, Callback {
	private static final int MSG_TOAST = 1;
	private static final int MSG_ACTION_CCALLBACK = 2;
	private static final int MSG_CANCEL_NOTIFY = 3;
	// 椤甸潰
	private FrameLayout flPage;
	// 瀹牸鍒楄〃
	private PlatformGridView grid;
	// 鍙栨秷鎸夐挳
	private Button btnCancel;
	// 婊戜笂鏉ョ殑鍔ㄧ敾
	private Animation animShow;
	// 婊戜笅鍘荤殑鍔ㄧ敾
	private Animation animHide;
	private boolean finishing;
	private boolean canceled;
	private HashMap<String, Object> reqMap;
	private ArrayList<CustomerLogo> customers;
	private int notifyIcon;
	private String notifyTitle;
	private boolean silent;
	private PlatformActionListener callback;
	private ShareContentCustomizeCallback customizeCallback;
	private boolean dialogMode;

	public OnekeyShare() {
		reqMap = new HashMap<String, Object>();
		customers = new ArrayList<CustomerLogo>();
		callback = this;
	}

	public void show(Context context) {
		super.show(context, null);
	}

	/** 鍒嗕韩鏃禢otification鐨勫浘鏍囧拰鏂囧瓧 */
	public void setNotification(int icon, String title) {
		notifyIcon = icon;
		notifyTitle = title;
	}

	/** address鏄帴鏀朵汉鍦板潃锛屼粎鍦ㄤ俊鎭拰閭欢浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵 */
	public void setAddress(String address) {
		reqMap.put("address", address);
	}

	/** title鏍囬锛屽湪鍗拌薄绗旇銆侀偖绠便�淇℃伅銆佸井淇★紙鍖呮嫭濂藉弸鍜屾湅鍙嬪湀锛夈�浜轰汉缃戝拰QQ绌洪棿浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵 */
	public void setTitle(String title) {
		reqMap.put("title", title);
	}

	/** titleUrl鏄爣棰樼殑缃戠粶閾炬帴锛屼粎鍦ㄤ汉浜虹綉鍜孮Q绌洪棿浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵 */
	public void setTitleUrl(String titleUrl) {
		reqMap.put("titleUrl", titleUrl);
	}

	/** text鏄垎浜枃鏈紝鎵�湁骞冲彴閮介渶瑕佽繖涓瓧娈�*/
	public void setText(String text) {
		reqMap.put("text", text);
	}

	/** imagePath鏄湰鍦扮殑鍥剧墖璺緞锛岄櫎Linked-In澶栫殑鎵�湁骞冲彴閮芥敮鎸佽繖涓瓧娈�*/
	public void setImagePath(String imagePath) {
		reqMap.put("imagePath", imagePath);
	}

	/** imageUrl鏄浘鐗囩殑缃戠粶璺緞锛屾柊娴井鍗氥�浜轰汉缃戙�QQ绌洪棿鍜孡inked-In鏀寔姝ゅ瓧娈�*/
	public void setImageUrl(String imageUrl) {
		reqMap.put("imageUrl", imageUrl);
	}

	/** musicUrl浠呭湪寰俊锛堝強鏈嬪弸鍦堬級涓娇鐢紝鏄煶涔愭枃浠剁殑鐩存帴鍦板潃 */
	public void serMusicUrl(String musicUrl) {
		reqMap.put("musicUrl", musicUrl);
	}

	/** url浠呭湪寰俊锛堝寘鎷ソ鍙嬪拰鏈嬪弸鍦堬級涓娇鐢紝鍚﹀垯鍙互涓嶆彁渚�*/
 	public void setUrl(String url) {
		reqMap.put("url", url);
	}

	/** filePath鏄緟鍒嗕韩搴旂敤绋嬪簭鐨勬湰鍦拌矾鍔诧紝浠呭湪寰俊濂藉弸鍜孌ropbox涓娇鐢紝鍚﹀垯鍙互涓嶆彁渚�*/
	public void setFilePath(String filePath) {
		reqMap.put("filePath", filePath);
	}

	/** comment鏄垜瀵硅繖鏉″垎浜殑璇勮锛屼粎鍦ㄤ汉浜虹綉鍜孮Q绌洪棿浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵 */
	public void setComment(String comment) {
		reqMap.put("comment", comment);
	}

	/** site鏄垎浜鍐呭鐨勭綉绔欏悕绉帮紝浠呭湪QQ绌洪棿浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵 */
	public void setSite(String site) {
		reqMap.put("site", site);
	}

	/** siteUrl鏄垎浜鍐呭鐨勭綉绔欏湴鍧�紝浠呭湪QQ绌洪棿浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵 */
	public void setSiteUrl(String siteUrl) {
		reqMap.put("siteUrl", siteUrl);
	}

	/** foursquare鍒嗕韩鏃剁殑鍦版柟鍚�*/
	public void setVenueName(String venueName) {
		reqMap.put("venueName", venueName);
	}

	/** foursquare鍒嗕韩鏃剁殑鍦版柟鎻忚堪 */
	public void setVenueDescription(String venueDescription) {
		reqMap.put("venueDescription", venueDescription);
	}

	/** 鍒嗕韩鍦扮含搴︼紝鏂版氮寰崥銆佽吘璁井鍗氬拰foursquare鏀寔姝ゅ瓧娈�*/
	public void setLatitude(float latitude) {
		reqMap.put("latitude", latitude);
	}

	/** 鍒嗕韩鍦扮粡搴︼紝鏂版氮寰崥銆佽吘璁井鍗氬拰foursquare鏀寔姝ゅ瓧娈�*/
	public void setLongitude(float longitude) {
		reqMap.put("longitude", longitude);
	}

	/** 鏄惁鐩存帴鍒嗕韩 */
	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	/** 璁剧疆缂栬緫椤电殑鍒濆鍖栭�涓钩鍙�*/
	public void setPlatform(String platform) {
		reqMap.put("platform", platform);
	}

	/** 璁剧疆鑷畾涔夌殑澶栭儴鍥炶皟 */
	public void setCallback(PlatformActionListener callback) {
		this.callback = callback;
	}

	/** 璁剧疆鐢ㄤ簬鍒嗕韩杩囩▼涓紝鏍规嵁涓嶅悓骞冲彴鑷畾涔夊垎浜唴瀹圭殑鍥炶皟 */
	public void setShareContentCustomizeCallback(ShareContentCustomizeCallback callback) {
		customizeCallback = callback;
	}

	/** 璁剧疆鑷繁鍥炬爣鍜岀偣鍑讳簨浠讹紝鍙互閲嶅璋冪敤娣诲姞澶氭 */
	public void setCustomerLogo(Bitmap logo, String label, OnClickListener ocListener) {
		CustomerLogo cl = new CustomerLogo();
		cl.label = label;
		cl.logo = logo;
		cl.listener = ocListener;
		customers.add(cl);
	}

	// 璁剧疆缂栬緫椤甸潰鐨勬樉绀烘ā寮忎负Dialog妯″紡
	public void setDialogMode() {
		dialogMode = true;
		reqMap.put("dialogMode", dialogMode);
	}

	public void onCreate() {
		// 鏄剧ず鏂瑰紡鏄敱platform鍜宻ilent涓や釜瀛楁鎺у埗鐨�		
		// 濡傛灉platform璁剧疆浜嗭紝鍒欐棤椤绘樉绀轰節瀹牸锛屽惁鍒欓兘浼氭樉绀猴紱
		// 濡傛灉silent涓簍rue锛岃〃绀轰笉杩涘叆缂栬緫椤甸潰锛屽惁鍒欎細杩涘叆銆�		
		// 鏈被鍙垽鏂璸latform锛屽洜涓轰節瀹牸鏄剧ず浠ュ悗锛屼簨浠朵氦缁橮latformGridView鎺у埗
		// 褰損latform鍜宻ilent閮戒负true锛屽垯鐩存帴杩涘叆鍒嗕韩锛�		
		// 褰損latform璁剧疆浜嗭紝浣嗘槸silent涓篺alse锛屽垯鍒ゆ柇鏄惁鏄�浣跨敤瀹㈡埛绔垎浜�鐨勫钩鍙帮紝
		// 鑻ヤ负鈥滀娇鐢ㄥ鎴风鍒嗕韩鈥濈殑骞冲彴锛屽垯鐩存帴鍒嗕韩锛屽惁鍒欒繘鍏ョ紪杈戦〉闈�		
		if (reqMap.containsKey("platform")) {
			String name = String.valueOf(reqMap.get("platform"));
			if (silent) {
				HashMap<Platform, HashMap<String, Object>> shareData
						= new HashMap<Platform, HashMap<String,Object>>();
				shareData.put(ShareSDK.getPlatform(activity, name), reqMap);
				share(shareData);
			} else if (ShareCore.isUseClientToShare(name)) {
				HashMap<Platform, HashMap<String, Object>> shareData
						= new HashMap<Platform, HashMap<String,Object>>();
				shareData.put(ShareSDK.getPlatform(activity, name), reqMap);
				share(shareData);
			} else {
				EditPage page = new EditPage();
				page.setShareData(reqMap);
				page.setParent(this);
				if (dialogMode) {
					page.setDialogMode();
				}
				page.show(activity, null);

				finish();
			}
			return;
		}

		initPageView();
		initAnim();
		activity.setContentView(flPage);

		// 璁剧疆瀹牸鍒楄〃鏁版嵁
		grid.setData(reqMap, silent);
		grid.setCustomerLogos(customers);
		grid.setParent(this);
		btnCancel.setOnClickListener(this);

		// 鏄剧ず鍒楄〃
		flPage.clearAnimation();
		flPage.startAnimation(animShow);

		// 鎵撳紑鍒嗕韩鑿滃崟鐨勭粺璁�		
		ShareSDK.logDemoEvent(1, null);
	}

	private void initPageView() {
		flPage = new FrameLayout(getContext());
		flPage.setOnClickListener(this);

		// 瀹牸鍒楄〃鐨勫鍣紝涓轰簡鈥滀笅瀵归綈鈥濓紝鍦ㄥ閮ㄥ寘鍚簡涓�釜FrameLayout
		LinearLayout llPage = new LinearLayout(getContext()) {
			public boolean onTouchEvent(MotionEvent event) {
				return true;
			}
		};
		llPage.setOrientation(LinearLayout.VERTICAL);
		llPage.setBackgroundResource(R.drawable.share_vp_back);
		FrameLayout.LayoutParams lpLl = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.BOTTOM;
		llPage.setLayoutParams(lpLl);
		flPage.addView(llPage);

		// 瀹牸鍒楄〃
		grid = new PlatformGridView(getContext());
		LinearLayout.LayoutParams lpWg = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		int dp_10 = cn.sharesdk.framework.utils.R.dipToPx(getContext(), 10);
		lpWg.setMargins(0, dp_10, 0, 0);
		grid.setLayoutParams(lpWg);
		llPage.addView(grid);

		// 鍙栨秷鎸夐挳
		btnCancel = new Button(getContext());
		btnCancel.setTextColor(0xffffffff);
		btnCancel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		btnCancel.setText(R.string.cancel);
		btnCancel.setPadding(0, 0, 0, cn.sharesdk.framework.utils.R.dipToPx(getContext(), 5));
		btnCancel.setBackgroundResource(R.drawable.btn_cancel_back);
		LinearLayout.LayoutParams lpBtn = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, cn.sharesdk.framework.utils.R.dipToPx(getContext(), 45));
		lpBtn.setMargins(dp_10, 0, dp_10, dp_10 * 2);
		btnCancel.setLayoutParams(lpBtn);
		llPage.addView(btnCancel);
	}

	private void initAnim() {
		animShow = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 1,
				Animation.RELATIVE_TO_SELF, 0);
		animShow.setDuration(300);

		animHide = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 1);
		animHide.setDuration(300);
	}

	public void onClick(View v) {
		if (v.equals(flPage) || v.equals(btnCancel)) {
			canceled = true;
			finish();
		}
	}

	public boolean onKeyEvent(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			canceled = true;
		}
		return super.onKeyEvent(keyCode, event);
	}

	public void finish() {
		if (finishing) {
			return;
		}

		if (animHide == null) {
			finishing = true;
			super.finish();
			return;
		}

		// 鍙栨秷鍒嗕韩鑿滃崟鐨勭粺璁�		
		if (canceled) {
			ShareSDK.logDemoEvent(2, null);
		}
		finishing = true;
		animHide.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				flPage.setVisibility(View.GONE);
				OnekeyShare.super.finish();
			}
		});
		flPage.clearAnimation();
		flPage.startAnimation(animHide);
	}

	/** 寰幆鎵ц鍒嗕韩 */
	public void share(HashMap<Platform, HashMap<String, Object>> shareData) {
		boolean started = false;
		for (Entry<Platform, HashMap<String, Object>> ent : shareData.entrySet()) {
			Platform plat = ent.getKey();
			String name = plat.getName();
			boolean isWechat = "WechatMoments".equals(name) || "Wechat".equals(name);
			if (isWechat && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				msg.obj = activity.getString(R.string.wechat_client_inavailable);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			boolean isGooglePlus = "GooglePlus".equals(name);
			if (isGooglePlus && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				msg.obj = activity.getString(R.string.google_plus_client_inavailable);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			boolean isQQ = "QQ".equals(name);
			if (isQQ && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				msg.obj = activity.getString(R.string.qq_client_inavailable);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			boolean isPinterest = "Pinterest".equals(name);
			if (isPinterest && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				msg.obj = activity.getString(R.string.pinterest_client_inavailable);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			HashMap<String, Object> data = ent.getValue();
			int shareType = Platform.SHARE_TEXT;
			String imagePath = String.valueOf(data.get("imagePath"));
			if (imagePath != null && (new File(imagePath)).exists()) {
				shareType = Platform.SHARE_IMAGE;
				if (data.containsKey("url") && !TextUtils.isEmpty(data.get("url").toString())) {
					shareType = Platform.SHARE_WEBPAGE;
				}
			}
			else {
				Object imageUrl = data.get("imageUrl");
				if (imageUrl != null && !TextUtils.isEmpty(String.valueOf(imageUrl))) {
					shareType = Platform.SHARE_IMAGE;
					if (data.containsKey("url") && !TextUtils.isEmpty(data.get("url").toString())) {
						shareType = Platform.SHARE_WEBPAGE;
					}
				}
			}
			data.put("shareType", shareType);

			if (!started) {
				started = true;
				if (equals(callback)) {
					showNotification(2000, getContext().getString(R.string.sharing));
				}
				finish();
			}
			plat.setPlatformActionListener(callback);
			ShareCore shareCore = new ShareCore();
			shareCore.setShareContentCustomizeCallback(customizeCallback);
			shareCore.share(plat, data);
		}
	}

	public void onComplete(Platform platform, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);
	}

	public void onError(Platform platform, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = t;
		UIHandler.sendMessage(msg, this);

		// 鍒嗕韩澶辫触鐨勭粺璁�		
		ShareSDK.logDemoEvent(4, platform);
	}

	public void onCancel(Platform platform, int action) {
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);
	}

	public boolean handleMessage(Message msg) {
		switch(msg.what) {
			case MSG_TOAST: {
				String text = String.valueOf(msg.obj);
				Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
			}
			break;
			case MSG_ACTION_CCALLBACK: {
				switch (msg.arg1) {
					case 1: {
						// 鎴愬姛
						showNotification(2000, getContext().getString(R.string.share_completed));
					}
					break;
					case 2: {
						// 澶辫触
						String expName = msg.obj.getClass().getSimpleName();
						if ("WechatClientNotExistException".equals(expName)
								|| "WechatTimelineNotSupportedException".equals(expName)) {
							showNotification(2000, getContext().getString(R.string.wechat_client_inavailable));
						}
						else if ("GooglePlusClientNotExistException".equals(expName)) {
							showNotification(2000, getContext().getString(R.string.google_plus_client_inavailable));
						}
						else if ("QQClientNotExistException".equals(expName)) {
							showNotification(2000, getContext().getString(R.string.qq_client_inavailable));
						}
						else {
							showNotification(2000, getContext().getString(R.string.share_failed));
						}
					}
					break;
					case 3: {
						// 鍙栨秷
						showNotification(2000, getContext().getString(R.string.share_canceled));
					}
					break;
				}
			}
			break;
			case MSG_CANCEL_NOTIFY: {
				NotificationManager nm = (NotificationManager) msg.obj;
				if (nm != null) {
					nm.cancel(msg.arg1);
				}
			}
			break;
		}
		return false;
	}

	// 鍦ㄧ姸鎬佹爮鎻愮ず鍒嗕韩鎿嶄綔
	private void showNotification(long cancelTime, String text) {
		try {
			Context app = getContext().getApplicationContext();
			NotificationManager nm = (NotificationManager) app
					.getSystemService(Context.NOTIFICATION_SERVICE);
			final int id = Integer.MAX_VALUE / 13 + 1;
			nm.cancel(id);

			long when = System.currentTimeMillis();
			Notification notification = new Notification(notifyIcon, text, when);
			PendingIntent pi = PendingIntent.getActivity(app, 0, new Intent(), 0);
			notification.setLatestEventInfo(app, notifyTitle, text, pi);
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			nm.notify(id, notification);

			if (cancelTime > 0) {
				Message msg = new Message();
				msg.what = MSG_CANCEL_NOTIFY;
				msg.obj = nm;
				msg.arg1 = id;
				UIHandler.sendMessageDelayed(msg, cancelTime, this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
