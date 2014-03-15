/*
 * 瀹樼綉鍦扮珯:http://www.ShareSDK.cn
 * 鎶�湳鏀寔QQ: 4006852216
 * 瀹樻柟寰俊:ShareSDK   锛堝鏋滃彂甯冩柊鐗堟湰鐨勮瘽锛屾垜浠皢浼氱涓�椂闂撮�杩囧井淇″皢鐗堟湰鏇存柊鍐呭鎺ㄩ�缁欐偍銆傚鏋滀娇鐢ㄨ繃绋嬩腑鏈変换浣曢棶棰橈紝涔熷彲浠ラ�杩囧井淇′笌鎴戜滑鍙栧緱鑱旂郴锛屾垜浠皢浼氬湪24灏忔椂鍐呯粰浜堝洖澶嶏級
 *
 * Copyright (c) 2013骞�ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import com.txmcu.heatcontroller.R;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;
import cn.sharesdk.framework.Platform;

/**
 * ShareCore鏄揩鎹峰垎浜殑瀹為檯鍑哄彛锛屾绫讳娇鐢ㄤ簡鍙嶅皠鐨勬柟寮忥紝閰嶅悎浼犻�杩涙潵鐨凥ashMap锛� *鏋勯�{@link ShareParams}瀵硅薄锛屽苟鎵ц鍒嗕韩锛屼娇蹇嵎鍒嗕韩涓嶅啀闇�鑰冭檻鐩爣骞冲彴
 */
public class ShareCore {
	private ShareContentCustomizeCallback customizeCallback;

	/** 璁剧疆鐢ㄤ簬鍒嗕韩杩囩▼涓紝鏍规嵁涓嶅悓骞冲彴鑷畾涔夊垎浜唴瀹圭殑鍥炶皟 */
	public void setShareContentCustomizeCallback(ShareContentCustomizeCallback callback) {
		customizeCallback = callback;
	}

	/**
	 * 鍚戞寚瀹氬钩鍙板垎浜唴瀹�	 * <p>
	 * <b>娉ㄦ剰锛�/b><br>
	 * 鍙傛暟data鐨勯敭鍊奸渶瑕佷弗鏍兼寜鐓@link ShareParams}涓嶅悓瀛愮被鍏蜂綋瀛楁鏉ュ懡鍚嶏紝
	 *鍚﹀垯鏃犳硶鍙嶅皠姝ゅ瓧娈碉紝涔熸棤娉曡缃叾鍊笺�
	 */
	public boolean share(Platform plat, HashMap<String, Object> data) {
		if (plat == null || data == null) {
			return false;
		}

		Platform.ShareParams sp = null;
		try {
			sp = getShareParams(plat, data);
		} catch(Throwable t) {
			sp = null;
		}

		if (sp != null) {
			if (customizeCallback != null) {
				customizeCallback.onShare(plat, sp);
			}
			plat.share(sp);
		}
		return true;
	}

	private Platform.ShareParams getShareParams(Platform plat,
			HashMap<String, Object> data) throws Throwable {
		String className = plat.getClass().getName() + "$ShareParams";
		Class<?> cls = Class.forName(className);
		if (cls == null) {
			return null;
		}

		Object sp = cls.newInstance();
		if (sp == null) {
			return null;
		}

		for (Entry<String, Object> ent : data.entrySet()) {
			try {
				Field fld = cls.getField(ent.getKey());
				if (fld != null) {
					fld.setAccessible(true);
					fld.set(sp, ent.getValue());
				}
			} catch(Throwable t) {}
		}

		return (Platform.ShareParams) sp;
	}

	/** 鍒ゆ柇鎸囧畾骞冲彴鏄惁浣跨敤瀹㈡埛绔垎浜�*/
	public static boolean isUseClientToShare(String platform) {
		if ("Wechat".equals(platform) || "WechatMoments".equals(platform)
				|| "ShortMessage".equals(platform) || "Email".equals(platform)
				|| "GooglePlus".equals(platform) || "QQ".equals(platform)
				|| "Pinterest".equals(platform)) {
			return true;
		}

		return false;
	}

}
