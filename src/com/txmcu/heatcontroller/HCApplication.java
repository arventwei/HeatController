package com.txmcu.heatcontroller;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class HCApplication extends Application {
	
	public enum StartModeType
	{
		SMS,
		Phone,
	}
	
	public static final int maxCarCount = 2;
	
	public static final int[] heatTimeList = {10,20,30,40,50};
	
	private static final String boundNo="boundNo";
	private static final String simNo="simNo";
	private static final String modeType="modeType";
	private static final String heatTime="heatTime";
	private static final String autoStartTimeHour="autoStartTimeHour";
	private static final String autoStartTimeMin="autoStartTimeMin";
	private static final String autoStartTimeEnable="autoStartTimeEnable";
	private static final String autoStopTemp="autoStopTemp";//关机温控
	private static final String autoStopTempEnable="autoStopTempEnable";//关机温控开关
	private static final String passWord="passWord";
	private static final String phoneHeatTime="phoneHeatTime";
	
	private static final String selCarNo = "selCarNo";
	
	
	public String sendTo;
	public String msg;
	
	private List<String> smsList = new ArrayList<String>();
	
	SharedPreferences mPerferences;
	
	public void onCreate()
	{
	  super.onCreate();
	  mPerferences = PreferenceManager.getDefaultSharedPreferences(this); 
	   // Log.v("HCApplication", "create");
	}
	  
	
	public void setSelCarNo(int carid) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putInt(selCarNo,carid);  
	    mEditor.commit(); 
	}
	public int getSelCarNo() {
		return mPerferences.getInt(selCarNo, 0);
	}
	
	
	
	public void setPhoneHeatTime(int carid,int mins) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putInt(phoneHeatTime+carid,mins);  
	    mEditor.commit(); 
	}
	public int getPhoneHeatTime(int carid) {
		return mPerferences.getInt(phoneHeatTime+carid, 30);
	}
	
	public void setBoundNo(int carid,int no) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putInt(boundNo+carid,no);  
	    mEditor.commit(); 
	}
	public int getBoundNo(int carid) {
		return mPerferences.getInt(boundNo+carid, 0);
	}
	
	public void setSimNo(int carid,String sim) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putString(simNo+carid,sim);  
	    mEditor.commit(); 
	}
	public String getSimNo(int carid) {
		return mPerferences.getString(simNo+carid, "");
	}
	
	public void setPassWord(int carid,String pwd) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putString(passWord+carid,pwd);  
	    mEditor.commit(); 
	}
	public String getPassWord(int carid) {
		return mPerferences.getString(passWord+carid, "000000");
	}
	
	
	public void setModeType(StartModeType type) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putInt(modeType, type==StartModeType.SMS?0:1);  
	    mEditor.commit(); 
	}
	public StartModeType getModeType() {
		return mPerferences.getInt(modeType, 1)==0?StartModeType.SMS:StartModeType.Phone;
	}
	
	public void setHeatTime(int carid,int minutes) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putInt(heatTime+carid,minutes);  
	    mEditor.commit(); 
	}
	public int getHeatTime(int carid) {
		return mPerferences.getInt(heatTime+carid, 0);
	}
	
	
	
	public void setAutoStartTimeHour(int carid,int hours) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putInt(autoStartTimeHour+carid,hours);  
	    mEditor.commit(); 
	}
	public int getAutoStartTimeHour(int carid) {
		return mPerferences.getInt(autoStartTimeHour+carid, 6);
	}
	
	public void setAutoStartTimeMin(int carid,int minutes) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putInt(autoStartTimeMin+carid,minutes);  
	    mEditor.commit(); 
	}
	public int getAutoStartTimeMin(int carid) {
		return mPerferences.getInt(autoStartTimeMin+carid, 0);
	}
	
	public void setAutoStartTimeEnable(int carid,Boolean enable) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putBoolean(autoStartTimeEnable+carid,enable);  
	    mEditor.commit(); 
	}
	public Boolean getAutoStartTimeEnable(int carid) {
		return mPerferences.getBoolean(autoStartTimeEnable+carid, false);
	}
	
	/*public void setAutoStopTemp(int degree) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putInt(autoStopTemp,degree);  
	    mEditor.commit(); 
	}
	public int getAutoStopTemp() {
		return mPerferences.getInt(autoStopTemp, 30);
	}
	
	public void setAutoStopTempEnable(Boolean enable) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putBoolean(autoStopTempEnable,enable);  
	    mEditor.commit(); 
	}
	public Boolean getAutoStopTempEnable() {
		return mPerferences.getBoolean(autoStopTempEnable, false);
	}*/
	
	
	
	public void onReceiveSms(String messageString)
	{
		smsList.add(messageString);
	}
	
}
