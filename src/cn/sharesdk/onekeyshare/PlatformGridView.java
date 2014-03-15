/*
 * 瀹樼綉鍦扮珯:http://www.ShareSDK.cn
 * 鎶�湳鏀寔QQ: 4006852216
 * 瀹樻柟寰俊:ShareSDK   锛堝鏋滃彂甯冩柊鐗堟湰鐨勮瘽锛屾垜浠皢浼氱涓�椂闂撮�杩囧井淇″皢鐗堟湰鏇存柊鍐呭鎺ㄩ�缁欐偍銆傚鏋滀娇鐢ㄨ繃绋嬩腑鏈変换浣曢棶棰橈紝涔熷彲浠ラ�杩囧井淇′笌鎴戜滑鍙栧緱鑱旂郴锛屾垜浠皢浼氬湪24灏忔椂鍐呯粰浜堝洖澶嶏級
 *
 * Copyright (c) 2013骞�ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import com.txmcu.heatcontroller.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;

/**
 * 骞冲彴瀹牸鍒楄〃鏄剧ず宸ュ叿銆� * <p>
 * PlatformGridView瀵光�android.support鈥濆寘鏈変緷璧栵紝鍥犳璇锋敞鎰忔鏌ユ偍椤圭洰涓槸
 *鍚﹀凡缁忛泦鎴愪簡鐩稿簲鐨刯ar鍖� */
public class PlatformGridView extends LinearLayout implements
		OnPageChangeListener, OnClickListener, Callback {
	private static final int MSG_PLATFORM_LIST_GOT = 1;
	// 姣忛〉鏄剧ず9鏍�	
	private static final int PAGE_SIZE = 9;
	// 瀹牸瀹瑰櫒
	private ViewPager pager;
	// 椤甸潰鎸囩ず鍣�	
	private ImageView[] points;
	private Bitmap grayPoint;
	private Bitmap whitePoint;
	// 鏄惁涓嶈烦杞珽ditPage鑰岀洿鎺ュ垎浜�	
	private boolean silent;
	// 骞冲彴鏁版嵁
	private Platform[] platformList;
	// 浠庡閮ㄤ紶杩涙潵鐨勫垎浜暟鎹紙鍚垵濮嬪寲鏁版嵁锛�	
	private HashMap<String, Object> reqData;
	private OnekeyShare parent;
	private ArrayList<CustomerLogo> customers;

	public PlatformGridView(Context context) {
		super(context);
		init(context);
	}

	public PlatformGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(final Context context) {
		setOrientation(VERTICAL);
		int dp_10 = cn.sharesdk.framework.utils.R.dipToPx(context, 10);
		setPadding(dp_10, dp_10, dp_10, dp_10);

		pager = new ViewPager(context);
		disableOverScrollMode(pager);
		pager.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		pager.setOnPageChangeListener(this);
		addView(pager);

		// 涓轰簡鏇村ソ鐨剈i鏁堟灉锛屽紑鍚瓙绾跨▼鑾峰彇骞冲彴鍒楄〃
		new Thread(){
			public void run() {
				platformList = ShareSDK.getPlatformList(context);
				UIHandler.sendEmptyMessage(MSG_PLATFORM_LIST_GOT, PlatformGridView.this);
			}
		}.start();
	}

	public boolean handleMessage(Message msg) {
		switch (msg.what) {
			case MSG_PLATFORM_LIST_GOT: {
				afterPlatformListGot();
			}
			break;
		}
		return false;
	}

	/** 鍒濆鍖栧鏍煎垪琛╱i */
	public void afterPlatformListGot() {
		Context context = getContext();

		// 涓轰簡鏇村ソ鐨剈i鏁堟灉锛屽紑鍚瓙绾跨▼鑾峰彇骞冲彴鍒楄〃
		int cusSize = customers == null ? 0 : customers.size();
		int platSize = platformList == null ? 0 : platformList.length;
		int pageSize = (platSize + cusSize) > PAGE_SIZE
				? PAGE_SIZE : (platSize + cusSize);
		int lines = pageSize / 3;
		if (pageSize % 3 > 0) {
			lines++;
		}
		ViewGroup.LayoutParams lp = pager.getLayoutParams();
		int dp_10 = cn.sharesdk.framework.utils.R.dipToPx(context, 10);
		int scrW = getResources().getDisplayMetrics().widthPixels;
		lp.height = (scrW - dp_10 * 2) * lines / 3;
		pager.setLayoutParams(lp);
		PlatformAdapter adapter = new PlatformAdapter(platformList, customers, this);
		pager.setAdapter(adapter);
		int pageCount = 0;
		if (platformList != null) {
			int size = platSize + cusSize;
			pageCount = size / PAGE_SIZE;
			if (size % PAGE_SIZE > 0) {
				pageCount++;
			}
		}
		points = new ImageView[pageCount];
		if (points.length <= 0) {
			return;
		}

		LinearLayout llPoints = new LinearLayout(context);
		// 濡傛灉椤甸潰鎬绘槸瓒呰繃1锛屽垯璁剧疆椤甸潰鎸囩ず鍣�		
		llPoints.setVisibility(pageCount > 1 ? View.VISIBLE: View.GONE);
		LayoutParams lpLl = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.CENTER_HORIZONTAL;
		llPoints.setLayoutParams(lpLl);
		addView(llPoints);

		int dp_5 = cn.sharesdk.framework.utils.R.dipToPx(context, 5);
		grayPoint = BitmapFactory.decodeResource(getResources(), R.drawable.gray_point);
		whitePoint = BitmapFactory.decodeResource(getResources(), R.drawable.white_point);
		for (int i = 0; i < pageCount; i++) {
			points[i] = new ImageView(context);
			points[i].setScaleType(ScaleType.CENTER_INSIDE);
			points[i].setImageBitmap(grayPoint);
			LayoutParams lpIv = new LayoutParams(dp_5, dp_5);
			lpIv.setMargins(dp_5, dp_5, dp_5, 0);
			points[i].setLayoutParams(lpIv);
			llPoints.addView(points[i]);
		}
		int curPage = pager.getCurrentItem();
		points[curPage].setImageBitmap(whitePoint);
	}

	public void onPageScrollStateChanged(int state) {
		if (ViewPager.SCROLL_STATE_IDLE == state) {
			for (int i = 0; i < points.length; i++) {
				points[i].setImageBitmap(grayPoint);
			}

			int curPage = pager.getCurrentItem();
			points[curPage].setImageBitmap(whitePoint);
		}
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	public void onPageSelected(int position) {

	}

	/**
	 * 璁剧疆椤甸潰鍒濆鍖栧拰鍒嗕韩鎿嶄綔闇�鐨勬暟鎹�	 * <p>
	 * 姝ゆ柟娉曞湪{@link OnekeyShare}鐨刄I鍒濆鍖栦腑琚皟鐢�	 *
	 * @param intent 鎼哄甫鍒濆鍖栨暟鎹殑Intent
	 */
	public void setData(HashMap<String, Object> data, boolean silent) {
		reqData = data;
		this.silent = silent;
	}

	/** 璁剧疆鑷繁鍥炬爣鍜岀偣鍑讳簨浠�*/
	public void setCustomerLogos(ArrayList<CustomerLogo> customers) {
		this.customers = customers;
	}

	/** 璁剧疆鍒嗕韩鎿嶄綔鐨勫洖璋冮〉闈�*/
	public void setParent(OnekeyShare parent) {
		this.parent = parent;
	}

	public void onClick(View v) {
		Platform plat = (Platform) v.getTag();
		if (plat != null) {
			if (silent) {
				HashMap<Platform, HashMap<String, Object>> shareData
						= new HashMap<Platform, HashMap<String,Object>>();
				shareData.put(plat, reqData);
				parent.share(shareData);
				return;
			}

			String name = plat.getName();
			parent.setPlatform(name);
			// EditPage涓嶆敮鎸佸井淇″钩鍙般�Google+銆丵Q鍒嗕韩銆丳interest銆佷俊鎭拰閭欢锛屾�鏄墽琛岀洿鎺ュ垎浜�			
			if (ShareCore.isUseClientToShare(name)
					|| ("Evernote".equals(name) && !plat.isSSODisable())) {
				HashMap<Platform, HashMap<String, Object>> shareData
						= new HashMap<Platform, HashMap<String,Object>>();
				shareData.put(plat, reqData);
				parent.share(shareData);
				return;
			}

			// 璺宠浆EditPage鍒嗕韩
			EditPage page = new EditPage();
			page.setShareData(reqData);
			page.setParent(parent);
			if ("true".equals(String.valueOf(reqData.get("dialogMode")))) {
				page.setDialogMode();
			}
			page.show(parent.getContext(), null);
			parent.finish();
		}
	}

	// 绂佺敤ViewPage OverScroll鐨勨�鍙戝厜鈥濇晥鏋�	
	private void disableOverScrollMode(View view) {
		if (Build.VERSION.SDK_INT < 9) {
			return;
		}
		try {
			Method m = View.class.getMethod("setOverScrollMode",
					new Class[] { Integer.TYPE });
			m.setAccessible(true);
			m.invoke(view, new Object[] { Integer.valueOf(2) });
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/** 瀹牸鍒楄〃鏁版嵁閫傞厤鍣�*/
	private static class PlatformAdapter extends PagerAdapter {
		// 瀹牸鍒楄〃鍏冪礌
		private GridView[] girds;
		private List<Object> logos;
		private OnClickListener callback;
		// 琛屾暟
		private int lines;

		public PlatformAdapter(Platform[] platforms, ArrayList<CustomerLogo> customers,
				OnClickListener callback) {
			logos = new ArrayList<Object>();
			if (platforms != null) {
				logos.addAll(Arrays.asList(platforms));
			}
			if (customers != null) {
				logos.addAll(customers);
			}
			this.callback = callback;
			girds = null;

			if (logos != null) {
				int size = logos.size();
				int pageCount = size / PAGE_SIZE;
				if (size % PAGE_SIZE > 0) {
					pageCount++;
				}
				girds = new GridView[pageCount];
			}
		}

		public int getCount() {
			return girds == null ? 0 : girds.length;
		}

		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		public Object instantiateItem(ViewGroup container, int position) {
			if (girds[position] == null) {
				int pageSize = PAGE_SIZE;
				int curSize = pageSize * position;
				int listSize = logos == null ? 0 : logos.size();
				if (curSize + pageSize > listSize) {
					pageSize = listSize - curSize;
				}
				Object[] gridBean = new Object[pageSize];
				for (int i = 0; i < pageSize; i++) {
					gridBean[i] = logos.get(curSize + i);
				}

				if (position == 0) {
					lines = gridBean.length / 3;
					if (gridBean.length % 3 > 0) {
						lines++;
					}
				}
				girds[position] = new GridView(container.getContext(), callback);
				girds[position].setData(lines, gridBean);
			}
			container.addView(girds[position]);
			return girds[position];
		}

		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	/** 绠�槗鐨勫鏍煎垪琛ㄦ帶浠�*/
	private static class GridView extends LinearLayout {
		private Object[] beans;
		private OnClickListener callback;
		private int lines;
		// 鏍煎瓙瀹藉害
		private int iconWidth;

		public GridView(Context context, OnClickListener callback) {
			super(context);
			this.callback = callback;
		}

		public void setData(int lines, Object[] beans) {
			this.lines = lines;
			this.beans = beans;
			init();
		}

		private void init() {
			int dp_10 = cn.sharesdk.framework.utils.R.dipToPx(getContext(), 10);
			int scrW = getResources().getDisplayMetrics().widthPixels;
			iconWidth = (scrW - dp_10 * 2) / 3 - dp_10 * 4;

			setOrientation(VERTICAL);

			int size = beans == null ? 0 : beans.length;
			int lineSize = size / 3;
			if (size % 3 > 0) {
				lineSize++;
			}
			LayoutParams lp = new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			lp.weight = 1;
			for (int i = 0; i < lines; i++) {
				LinearLayout llLine = new LinearLayout(getContext());
				llLine.setLayoutParams(lp);
				addView(llLine);

				if (i >= lineSize) {
					continue;
				}

				for (int j = 0; j < 3; j++) {
					final int index = i * 3 + j;
					if (index >= size) {
						LinearLayout llItem = new LinearLayout(getContext());
						llItem.setLayoutParams(lp);
						llLine.addView(llItem);
						continue;
					}

					final LinearLayout llItem = getView(index, callback, getContext());
					llItem.setTag(beans[index]);
					llItem.setLayoutParams(lp);
					llLine.addView(llItem);
				}
			}
		}

		private LinearLayout getView(int position, OnClickListener ocL, Context context) {
			Bitmap logo;
			String label;
			OnClickListener listener;
			if (beans[position] instanceof Platform) {
				logo = getIcon((Platform) beans[position]);
				label = getName((Platform) beans[position]);
				listener = ocL;
			}
			else {
				logo = ((CustomerLogo) beans[position]).logo;
				label = ((CustomerLogo) beans[position]).label;
				listener = ((CustomerLogo) beans[position]).listener;
			}

			LinearLayout ll = new LinearLayout(context);
			ll.setOrientation(LinearLayout.VERTICAL);
			int dp_5 = cn.sharesdk.framework.utils.R.dipToPx(context, 5);
			ll.setPadding(dp_5, dp_5, dp_5, dp_5);

			ImageView iv = new ImageView(context);
			iv.setScaleType(ScaleType.CENTER_INSIDE);
			LinearLayout.LayoutParams lpIv = new LinearLayout.LayoutParams(
					iconWidth, iconWidth);
			lpIv.gravity = Gravity.CENTER_HORIZONTAL;
			iv.setLayoutParams(lpIv);
			iv.setImageBitmap(logo);
			ll.addView(iv);

			TextView tv = new TextView(context);
			tv.setTextColor(0xffffffff);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			tv.setSingleLine();
			tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
			LinearLayout.LayoutParams lpTv = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			lpTv.weight = 1;
			tv.setLayoutParams(lpTv);
			tv.setText(label);
			ll.addView(tv);
			ll.setOnClickListener(listener);

			return ll;
		}

		private Bitmap getIcon(Platform plat) {
			if (plat == null) {
				return null;
			}

			String name = plat.getName();
			if (name == null) {
				return null;
			}

			String resName = "logo_" + plat.getName();
			int resId = cn.sharesdk.framework.utils.R.getResId(R.drawable.class, resName);
			return BitmapFactory.decodeResource(getResources(), resId);
		}

		private String getName(Platform plat) {
			if (plat == null) {
				return "";
			}

			String name = plat.getName();
			if (name == null) {
				return "";
			}

			int resId = cn.sharesdk.framework.utils.R.getStringRes(getContext(), plat.getName());
			return getContext().getString(resId);
		}

	}

}
