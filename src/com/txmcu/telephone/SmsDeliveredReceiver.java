package com.txmcu.telephone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.txmcu.heatcontroller.R;

public class SmsDeliveredReceiver extends BroadcastReceiver {
	@Override
    public void onReceive(Context arg0, Intent arg1) {
        switch (getResultCode())
        {
            case Activity.RESULT_OK:
                Toast.makeText(arg0, R.string.sms_send_ok, 
                        Toast.LENGTH_SHORT).show();
                break;
            case Activity.RESULT_CANCELED:
                Toast.makeText(arg0, "SMS not delivered", 
                        Toast.LENGTH_SHORT).show();
                break;                        
        }
    }
}