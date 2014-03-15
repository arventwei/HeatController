/*
 * 瀹樼綉鍦扮珯:http://www.ShareSDK.cn
 * 鎶�湳鏀寔QQ: 4006852216
 * 瀹樻柟寰俊:ShareSDK   锛堝鏋滃彂甯冩柊鐗堟湰鐨勮瘽锛屾垜浠皢浼氱涓�椂闂撮�杩囧井淇″皢鐗堟湰鏇存柊鍐呭鎺ㄩ�缁欐偍銆傚鏋滀娇鐢ㄨ繃绋嬩腑鏈変换浣曢棶棰橈紝涔熷彲浠ラ�杩囧井淇′笌鎴戜滑鍙栧緱鑱旂郴锛屾垜浠皢浼氬湪24灏忔椂鍐呯粰浜堝洖澶嶏級
 *
 * Copyright (c) 2013骞�ShareSDK.cn. All rights reserved.
 */


package cn.sharesdk.onekeyshare;

import com.txmcu.heatcontroller.R;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import cn.sharesdk.framework.FakeActivity;

/** 鏌ョ湅缂栬緫椤甸潰涓浘鐗囩殑渚嬪瓙 */
public class PicViewer extends FakeActivity implements OnClickListener {
	private ImageView ivViewer;
	private Bitmap pic;

	/** 璁剧疆鍥剧墖鐢ㄤ簬娴忚 */
	public void setImageBitmap(Bitmap pic) {
		this.pic = pic;
		if (ivViewer != null) {
			ivViewer.setImageBitmap(pic);
		}
	}

	public void onCreate() {
		ivViewer = new ImageView(activity);
		ivViewer.setScaleType(ScaleType.CENTER_INSIDE);
		ivViewer.setBackgroundColor(0xc0000000);
		ivViewer.setOnClickListener(this);
		activity.setContentView(ivViewer);
		if (pic != null && !pic.isRecycled()) {
			ivViewer.setImageBitmap(pic);
		}
	}

	public void onClick(View v) {
		finish();
	}

}
