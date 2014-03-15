/*
 * 瀹樼綉鍦扮珯:http://www.ShareSDK.cn
 * 鎶�湳鏀寔QQ: 4006852216
 * 瀹樻柟寰俊:ShareSDK   锛堝鏋滃彂甯冩柊鐗堟湰鐨勮瘽锛屾垜浠皢浼氱涓�椂闂撮�杩囧井淇″皢鐗堟湰鏇存柊鍐呭鎺ㄩ�缁欐偍銆傚鏋滀娇鐢ㄨ繃绋嬩腑鏈変换浣曢棶棰橈紝涔熷彲浠ラ�杩囧井淇′笌鎴戜滑鍙栧緱鑱旂郴锛屾垜浠皢浼氬湪24灏忔椂鍐呯粰浜堝洖澶嶏級
 *
 * Copyright (c) 2013骞�ShareSDK.cn. All rights reserved.
 */

package com.txmcu.heatcontroller;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * OneKeyShareCallback鏄揩鎹峰垎浜姛鑳界殑涓�釜鈥滃閮ㄥ洖璋冣�绀轰緥銆� *婕旂ず浜嗗浣曢�杩囨坊鍔爀xtra鐨勬柟娉曪紝灏嗗揩鎹峰垎浜殑鍒嗕韩缁撴灉鍥炶皟鍒� *澶栭潰鏉ュ仛鑷畾涔夊鐞嗐�
 */
public class OneKeyShareCallback implements PlatformActionListener {

	public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
		System.out.println(res.toString());
		// 鍦ㄨ繖閲屾坊鍔犲垎浜垚鍔熺殑澶勭悊浠ｇ爜
	}

	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();
		// 鍦ㄨ繖閲屾坊鍔犲垎浜け璐ョ殑澶勭悊浠ｇ爜
	}

	public void onCancel(Platform plat, int action) {
		// 鍦ㄨ繖閲屾坊鍔犲彇娑堝垎浜殑澶勭悊浠ｇ爜
	}

}
