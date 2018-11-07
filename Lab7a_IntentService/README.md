## Description will be here soon.
#### This lab shows the implementation of an Intent Service. The idea behind the Intent service is that:
- It automatically creates a new worker thread for you
- You do not need to implement onStartCommand because it is implicitly implemented and it passes active intent right to onHandleIntent method
- You do not need to destroy the service, call stopService() or call selfStop() inside the service because if there are no more intents passed to the onHandleIntent method, the service implicitly stops itself and joins the worker thread with UI.
- You can send messages through LocalBroadcastManager or with ResultReceiver's method onReceiveResult()... Note: Only LocalBroadcastManager is implemented in the lab as an example.

GLHF