package sk.tuke.smartlab.lab7b_foregroundservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

public class ForegroundService extends Service {
    Notification foregroundNotification;
    public static boolean isServiceStarted = false;
    private final int SERVICE_ID = 1000;
    public static final String BROADCAST_INTENT_ACTION = "intent_action";
    public static final String BROADCAST_DATA = "data";

    @Override
    public void onCreate() {
        super.onCreate();

        //This intent is used to open app's MainActivity if the foreground notification is tapped.
        Intent result = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(result);

        //The intent is sent to the notification in form of a PendingIntent
        //PendingIntent transfers functionality and permissions to other applications so that they are allowed to interact with for or with your app.
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT>= 26){
            foregroundNotification = new NotificationCompat.Builder(this, createNotificationChannel("foreground_svc_channel_id","foreground_svc_channel_name"))
                    .setContentTitle("This is a foreground notification :)")
                    .setAutoCancel(true)
                    .setContentText("Tap this to open Lab7b app")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setOngoing(true)
                    .setContentIntent(resultPendingIntent)
                    .build();
        }
        else {
            foregroundNotification = new NotificationCompat.Builder(this, "")
                    .setContentTitle("This is a foreground notification :)")
                    .setAutoCancel(true)
                    .setContentText("Tap this to open Lab7b app")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setOngoing(true)
                    .setContentIntent(resultPendingIntent)
                    .build();
        }
            //Notification is mandatory for foreground services
            startForeground(SERVICE_ID, foregroundNotification);
            isServiceStarted = true;
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName){
        NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility (Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
        return channelId;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /********************************************/
        //In this section all the work should be done. Data is passed through the input intent object
        Intent data = new Intent(BROADCAST_INTENT_ACTION);
        data.putExtra(BROADCAST_DATA,"Heloooooo");
        /**********************************************/
        LocalBroadcastManager.getInstance(this).sendBroadcast(data);
        return Service.START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stopSelf();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        stopSelf();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
