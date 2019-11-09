package com.qyz.malls.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.qyz.malls.AppExecutors;
import com.qyz.malls.R;
import com.qyz.malls.apicall.ApiInstanceClass;
import com.qyz.malls.db.LocalDatabase;
import com.qyz.malls.db.User;
import com.qyz.malls.db.UserDao;
import com.qyz.malls.restaurants.activity.RestaurantHomeActivity;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.concurrent.Executor;

import okhttp3.RequestBody;

public class QzyMessagingService extends FirebaseMessagingService {
    private static final String TAG = "QzyMessagingService";
    private FirebaseAuth mAuth;
    private Context context;
    public static String newToken = null;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }

    @Override
    public void onNewToken(final String token){
        context = this;
        final LinkedHashMap<String,String> params = new LinkedHashMap<>();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            newToken = token;
            return;
        }
        params.put("partition",currentUser.getPhoneNumber());
        params.put("sort",currentUser.getUid());
        params.put("passkey",token);

        Executor diskIO = new AppExecutors().getDiskIO();
        diskIO.execute(new Runnable() {
            @Override
            public void run() {
                UserDao userDao = LocalDatabase.getInstance(context).userDao();
                if (userDao.getUserCount() == 0 || userDao.getGcmToken()==null) {
                    addToDatabase(userDao);
                }else if((!userDao.getPhoneNumber().equalsIgnoreCase(currentUser.getPhoneNumber()) || !userDao.getGcmToken().equalsIgnoreCase(params.get("passkey")))){
                    addToDatabase(userDao);
                }
            }

                public void addToDatabase(UserDao userDao){
                    Log.d(TAG, "sendPostCred/run: Inserting into database for user:"+userDao.getPhoneNumber());
                    User user = new User();
                    user.setPhonenumber(currentUser.getPhoneNumber());
                    user.setUserid(currentUser.getUid());
                    user.setGcmToken(params.get("passKey"));
                    userDao.insertDetails(user);

                    //RequestBody body  = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(params)).toString());
                    RequestBody body = RequestBody.create((new JSONObject(params)).toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
                    ApiInstanceClass.getInstance().submitPostRequest(ApiInstanceClass.getBaseInterface(),body,null,"post_user_cred",null);
                }

        });
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, RestaurantHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_info_black_24dp)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}

