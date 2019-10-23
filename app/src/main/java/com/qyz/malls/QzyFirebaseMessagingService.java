package com.qyz.malls;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class QzyFirebaseMessagingService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
