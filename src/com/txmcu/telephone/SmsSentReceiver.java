package com.txmcu.telephone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.txmcu.heatcontroller.HCApplication;
import com.txmcu.heatcontroller.R;

public class SmsSentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context arg0, Intent arg1) {
    	Boolean isFailedBoolean = false;
        switch (getResultCode())
        {
            case Activity.RESULT_OK:
                Toast.makeText(arg0, R.string.sms_sending, 
                        Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                Toast.makeText(arg0, "Generic failure", 
                        Toast.LENGTH_SHORT).show();
                isFailedBoolean = true;
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Toast.makeText(arg0, "No service", 
                        Toast.LENGTH_SHORT).show();
                isFailedBoolean = true;
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                Toast.makeText(arg0, "Null PDU", 
                        Toast.LENGTH_SHORT).show();
                isFailedBoolean = true;
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                Toast.makeText(arg0, "Radio off", 
                        Toast.LENGTH_SHORT).show();
                isFailedBoolean = true;
                break;
        }
        if (isFailedBoolean) {
        	HCApplication application = (HCApplication)arg0.getApplicationContext();
    		Uri uri = Uri.parse("smsto:"+application.sendTo);   
    		Intent it = new Intent(Intent.ACTION_SENDTO, uri);   
    		it.putExtra("sms_body",application.msg);   
    		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		arg0.startActivity(it);
    		return;
		}
    }
}