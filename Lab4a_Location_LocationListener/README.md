## Getting user's location using the **LocationListener** interface
*Note: This approach is the most common, although not the most suitable. The recommended approach is to use [Google Play Services using FusedLocationProviderClient](https://developers.google.com/location-context/fused-location-provider/). This scenario is presented in [Lab4b](https://github.com/kkui-chi/VMIR/tree/master/Lab4b_Location_FusedLocationProviderClient)*.
### Contents:
- This lab presents the standard and the simplest approach to getting user's location.
- LocationListener is registered to receive updates from GPS provider with boundaries of 5ms (GPS in most mobile phones is able to provide at maximum a single sample in 1000ms) and 5 meters (usual precission with good GPS signal is 15 meters).
- Runtime permissions lifecycle is used to provide ACCESS_FINE_LOCATION permission for GPS sensors. 
### Disclaimer:
- There are some things that are disputable at least. 
- Minimum recommended GPS location update time is 1000ms.
- Minimum recommended GPS location update distance is 15meters.
- LocationListener can and sometimes should be implemented inside a background service & in a separate thread. The reason is that it can provide location throughout the entire application.
- User is unable to determine the state of the GPS satellites, thus does not have information about the GPS signal. To tackle this problem use [GpsStatus.Listener](https://developer.android.com/reference/android/location/GpsStatus.Listener) interface (deprecated at API 24) or later [GnssStatus and GnssStatus.Callback](https://developer.android.com/reference/android/location/GnssStatus.Callback) (since API 24).
- 
### HowTo:
--------
##### Setting up the prerequisites
1. Add an app level permission to access location of the device (```ACCESS_FINE_LOCATION``` for GPS provider - precise location and/or ```ACCESS_COARSE_LOCATION``` for other providers - estimated location) inside the [AndroidManifest.xml](https://github.com/kkui-chi/VMIR/blob/master/Lab4a_Location_LocationListener/app/src/main/AndroidManifest.xml) file above the ```<application>``` tag. The permission is of level DANGEROUS, thus we will need to request the permission in runtime.
2. In some useful context, we need to implement the ```LocationListener``` interface. We can do it either by using the implements directive of the activity or service or create a ```new``` instance of ```LocationListener``` and call it throughout the required contexts. We chose the first approach. Observe, that in our [MainActivity](https://github.com/kkui-chi/VMIR/blob/master/Lab4a_Location_LocationListener/app/src/main/java/sk/tuke/smartlab/lab4a_location_locationlistener/MainActivity.java) we have ```implements LocationListener``` directive.
3. We need to implement the interface's methods ```onLocationChanged```, ```onStatusChanged```, ```onProviderEnabled``` and ```onProviderDisabled```. The idea of each method is self explanatory. This means that we need to practically use at least the ```onLocationChanged``` method to handle the location event and react accordingly.
4. Create LocationManager as a class object and initialize it in onCreate by calling the ```getSystemService(Context.LOCATION_SERVICE)```. The resulting object needs to be explicitly converted into LocationManager, because there are multiple types of system services that this method can return (Bluetooth service, Alarms, etc).
5. Check for permissions ```ACCESS_FINE_LOCATION``` and/or ```ACCESS_COARSE_LOCATION``` depending on what kind of location you want to retrieve.
6. The ```LocationListener``` has to be registered by the ```LocationManager```. If you use the LocationListener in an activity, the recommended approach is to carry out the registration in onResume and unregister the listener in onPause. In our case, we only register the listener and never unregister it which is not a good habit. We also register the listener in the onCreate method, which is not as versatile as registering it in the app context or in onResume of an activity.
7. The registration is done by calling the ```requestLocationUpdates(PROVIDER, time_treshold, distance_treshold, location_listener_instance)``` over the LocationManager instance. __This method requires the location permissions__.
    - 7.1 There are basically 3providers one can use GPS_PROVIDER is the most precise but drains the battery the most, NETWORK_PROVIDER and PASSIVE_PROVIDER use approximations using compatible WIFI access points and local cellular networks to estimate location but they drain the battery less.
    - 7.2 Time treshold states what is the minimum refresh time. If set e.g. to 5000 (ms), the onLocationChanged will be triggered every 5 seconds at most. This is not a rule, because the method can be triggered even later if the location does not change during the 5 seconds.
    - 7.3 Distance treshold states what is the minimum refresh distance. If set to e.g. 50 (meters), the onLocationChanged will be only triggered if the location change is at least 50 meters from the previous location.
8. We add an ID to a textview in our [activity_main.xml](https://github.com/kkui-chi/VMIR/blob/master/Lab4a_Location_LocationListener/app/src/main/res/layout/activity_main.xml), retrieve it in the MainActivity using findViewById and assign the retrieved location to it in ```onLocationChanged``` method using the provided ```location``` object. You can view other methods that are available over the ```location``` object.
9. (Appendix) If you only want a single location event you can try to use the ```getLastKnownLocation()``` method. Beware that this method can also return null (if the location was not yet catched by your or other apps) or obsolete location (if the last location change was too distant - in time or space).
## GLHF ... :)
---
__Note: 7.2 and 7.3 are used to avoid unnecessary battery drain and also jitter of location if the provider's precission is too low. For GPS hardware of most common smartphones can provide locations as fast as 1Hz (1 Location update per second). Setting up the minimum refresh time to less than 1000 (ms) is useless. The same goes with the distance. Precision of GPS is usually 10-15 meters with good satellite fix and signal. If we set constants too low, we may observe the location "jumping" (i.e. location marker is constantly moving in a chaotic manner on the map) - constantly changing because of lack of precission__.

---

### Official documentation:
- [LocationManager](https://developer.android.com/reference/android/location/LocationManager)
- [Location strategies](https://developer.android.com/guide/topics/location/strategies#java)
