package com.nbserver.androideatitv2server.services;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.nbserver.androideatitv2server.Common.Common;
import com.nbserver.androideatitv2server.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFCMServices extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Map<String, String> dataRecv = remoteMessage.getData();
        if (dataRecv != null) {
            if (dataRecv.get(Common.NOTI_TITLE).equals("New Order Client")) {

                ///Here we need call main activity because we have to assign value for Common.currentUser
                //So we must call mainactivity. to do that, if you directly call home activity it will crash
                //As it can be assigned to mainactivyt after Login.
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(Common.IS_OPEN_ACTIVITY_NEW_ORDER, true); //Use extra to detect is app from notification.

                Common.showNotification(this, new Random().nextInt(),
                        dataRecv.get(Common.NOTI_TITLE),
                        dataRecv.get(Common.NOTI_CONTENT),
                        intent);

            } else
                Common.showNotification(this, new Random().nextInt(),
                        dataRecv.get(Common.NOTI_TITLE),
                        dataRecv.get(Common.NOTI_CONTENT),
                        null);
        }
    }

    @Override
    public void onNewToken(@NonNull String newToken) {
        super.onNewToken(newToken);
        Common.updateNewToken(this, newToken, true, false); //Becasue we are in server app, isServer=true
    }
}
