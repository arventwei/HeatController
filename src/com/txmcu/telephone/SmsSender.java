package com.txmcu.telephone;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.txmcu.heatcontroller.HCApplication;
import com.txmcu.heatcontroller.R;

public class SmsSender {

	public static void SetSmsStop(Context paramContext,int carid) {

		SendSms(paramContext,carid, "关闭");

	}

	@SuppressLint("DefaultLocale")
	public static void SetSmsStart(Context paramContext, int carid,int mins) {
		String timeString = String.format("预热%03d", mins);
		SendSms(paramContext, carid,timeString);

	}

	public static void SetCalibrateTime(Context paramContext,int carid) {

		SendSms(paramContext,carid, "校正时间");

	}

	public static void SetRecovery(Context paramContext,int carid) {

		SendSms(paramContext,carid, "恢复");

	}

	
	public static void QueryBound(Context paramContext,int carid) {

		SendSms(paramContext,carid, "查询绑定");

	}
	
	public static void QuerySetting(Context paramContext,int carid) {

		SendSms(paramContext,carid, "查询设置");

	}
	/*public static void SetAutoStopEnable(Context paramContext, Boolean enable) {
		HCApplication application = (HCApplication) paramContext
				.getApplicationContext();
		application.setAutoStopTempEnable(enable);

		String timeString = enable ? "温控开" : "温控关";
		SendSms(paramContext, timeString);

	}*/

	/*public static void SetAutoStopTemp(Context paramContext, int temp) {
		if (temp < 0 || temp >= 99) {
			Toast.makeText(paramContext,
					com.txmcu.heatcontroller.R.string.set_auto_stop_temp_wrong,
					Toast.LENGTH_SHORT).show();
			return;
		}

		HCApplication application = (HCApplication) paramContext
				.getApplicationContext();
		application.setAutoStopTemp(temp);

		String timeString = String.format("温控%02d", temp);
		SendSms(paramContext, timeString);

	}*/

	public static void SetAutoStartEnable(Context paramContext,int carid, Boolean enable) {
		HCApplication application = (HCApplication) paramContext
				.getApplicationContext();
		application.setAutoStartTimeEnable(carid,enable);

		String timeString = enable ? "定时开" : "定时关";
		SendSms(paramContext, carid,timeString);

	}

	@SuppressLint("DefaultLocale")
	public static void SetAutoStartTime(Context paramContext, int carid,int hour, int min) {
		HCApplication application = (HCApplication) paramContext
				.getApplicationContext();
		application.setAutoStartTimeHour(carid,hour);
		application.setAutoStartTimeMin(carid,min);
		String timeString = String.format("定时%02d%02d", hour, min);
		SendSms(paramContext, carid,timeString);

	}

	@SuppressLint("DefaultLocale")
	public static void SetPhoneHeatTime(Context paramContext,int carid, int phoneHeatTime) {
		if (phoneHeatTime <= 0 || phoneHeatTime > 180) {
			Toast.makeText(paramContext,
					com.txmcu.heatcontroller.R.string.set_phone_heatTime_wrong,
					Toast.LENGTH_SHORT).show();
			return;
		}
		HCApplication application = (HCApplication) paramContext
				.getApplicationContext();
		application.setPhoneHeatTime(carid,phoneHeatTime);
		String cmdString = String.format("保持%03d", phoneHeatTime);
		SendSms(paramContext,carid, cmdString);

	}

	public static void ChangePassWord(final Context paramContext,int carid,
			String oldPwdString, String newPwdString) {
		if (oldPwdString.length() != 6 || newPwdString.length() != 6) {
			Toast.makeText(paramContext,
					com.txmcu.heatcontroller.R.string.sms_pwd_wrong_length,
					Toast.LENGTH_SHORT).show();
			return;
		}
		String cmdString = String.format("旧密码%s新密码%s", oldPwdString,
				newPwdString);
		// HCApplication application =
		// (HCApplication)paramContext.getApplicationContext();
		// application.setPassWord(newPwdString);
		SendSms(paramContext, carid,cmdString);
	}

	@SuppressLint("DefaultLocale")
	public static void BoundingDevice(final Context paramContext,int carid) {
		HCApplication application = (HCApplication) paramContext
				.getApplicationContext();

		String cmd = String.format("绑定%d%s", application.getBoundNo(carid) + 1,
				application.getPassWord(carid));
		SendSms(paramContext,carid, cmd);
	}
	
	public static void getSimInoList(Context paramContext) {
		//Telephony.SIMInfo localSIMInfo1 = Telephony.SIMInfo.getSIMInfoBySlot(paramContext, 0);
		
	}

	public static void SendSms(final Context paramContext,int carid, String smsString) {

		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";
		HCApplication application = (HCApplication) paramContext
				.getApplicationContext();
		String simNoString = application.getSimNo(carid);
		if (simNoString.length() <= 0) {

			Toast.makeText(paramContext,
					com.txmcu.heatcontroller.R.string.sms_no_sim,
					Toast.LENGTH_SHORT).show();
			return;
		}
		Log.d("SendSms",
				String.format("simNo:%s cmd:%s", simNoString, smsString));
		application.msg = smsString;
		application.sendTo = simNoString;

		PendingIntent sentPI = PendingIntent.getBroadcast(paramContext, 0,
				new Intent(SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(paramContext, 0,
				new Intent(DELIVERED), 0);

		// ---when the SMS has been delivered---

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(simNoString, null, smsString, sentPI, deliveredPI);
		
		Toast.makeText(paramContext, R.string.sms_processing,
				Toast.LENGTH_SHORT).show();

	}

	public static void call(Context paramContext,int carid) {
		try {
			HCApplication application = (HCApplication) paramContext
					.getApplicationContext();
			String simNoString = application.getSimNo(carid);
			if (simNoString.length() <= 0) {

				Toast.makeText(paramContext,
						com.txmcu.heatcontroller.R.string.sms_no_sim,
						Toast.LENGTH_SHORT).show();
				return;
			}

			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse(String.format("tel:%s", simNoString)));
			paramContext.startActivity(callIntent);
		} catch (ActivityNotFoundException e) {
			Log.e("sample call in android", "Call failed", e);
		}
	}

//	public static List<String> getSMS(Context paraContext) {
//		List<String> sms = new ArrayList<String>();
//		Uri uriSMSURI = Uri.parse("content://sms/inbox");
//		Cursor cur = paraContext.getContentResolver().query(uriSMSURI, null,
//				null, null, null);
//
//		while (cur.moveToNext()) {
//			String address = cur.getString(cur.getColumnIndex("address"));
//			String body = cur.getString(cur.getColumnIndexOrThrow("body"));
//			sms.add("Number: " + address + " .Message: " + body);
//
//		}
//		return sms;
//
//	}

	public static boolean hasSimCard(Context paramContext) {
		switch (((TelephonyManager) paramContext
				.getSystemService(Context.TELEPHONY_SERVICE)).getSimState()) {

		case TelephonyManager.SIM_STATE_READY:
			return true;
		default:
			return false;
		}

	}

	public static void closeSoftInput(Context paramContext) {

		// InputMethodManager.getInstance(getContext().getApplicationContext()).hideSoftInputFromWindow(paramContext.u.getWindowToken(),
		// 0);

		// InputMethodManager inputMethodManager =
		// (InputMethodManager)paramContext.getSystemService(Context.INPUT_METHOD_SERVICE);

		// inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),

		// InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
