package sk.tuke.smartlab.lab7a_intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

public class SomeKindOfService extends IntentService {

    public SomeKindOfService(String name) {
        super(name);
    }
    public SomeKindOfService(){super("Some service");}
    public static final String BROADCAST_ACTION = "some_string_which_specifies_the_intent_that_sent_the_broadcast";
    public static final String BROADCAST_SOME_DATA = "name_of_some_data_attribute_that_are_sent_through_intent";
    public static final String BROADCAST_SOME_OTHER_DATA = "name_of_some_other_data_attribute_that_are_sent_through_intent";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent broadcastIntent = new Intent(BROADCAST_ACTION);
        broadcastIntent.putExtra(BROADCAST_SOME_DATA,"some data");
        broadcastIntent.putExtra(BROADCAST_SOME_OTHER_DATA,intent.getBooleanExtra("dataThatCameFromActivity",false));
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }
}
