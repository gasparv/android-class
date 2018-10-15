# Resources, activity lifecycle and intents
### Contents:
- This lab presents the role of static resources - files and folders that are usually used within the app design. Resource modifiers are presented on [__res/values/strings.xml__](https://github.com/kkui-chi/VMIR/blob/master/Lab1_layouts_activities_intents/app/src/main/res/values/strings.xml) and [__res/values-sk/strings.xml__](https://github.com/kkui-chi/VMIR/blob/master/Lab1_layouts_activities_intents/app/src/main/res/values-sk/strings.xml) folder. 
- An example of implicit and explicit intent can be found in [MainActivity.java](https://github.com/kkui-chi/VMIR/blob/master/Lab1_layouts_activities_intents/app/src/main/java/sk/tuke/smartlab/lab1_layouts_activities_intents/MainActivity.java) @ lines 27-28 (explicit) & lines 35-41 (implicit). 
- Activity lifecycle was explained in [Lecture 1 - Slide 25](https://github.com/kkui-chi/VMIR/blob/master/Lectures-Prednasky/VMIR-1.pptx). 

HowTo:
1. Creating modifiers for resources requires creating folders by right clicking on res directory and selecting *Android Resource Directory*. Pick the folder name (type of resource content - e.g. values) select modifiers and create the folder. The modifiers "bubble up" from the most specific to the most general (if no specific resource is found) [see Lecture 2 - Slide 5](https://github.com/kkui-chi/VMIR/blob/master/Lectures-Prednasky/VMIR-2.pptx).
2. To use an explicit intent you need to specify the context "that you are leaving" and the class which you want to "create the new context" from. Alternativelly you can add simple data or create a serialized object or a parcel and pass it with the intent using *.putExtra()* method.
*Note: There are multiple ways of retrieving the context of an activity. You __SHOULD NOT__ use methods getApplicationContext() or getBaseContext() because these make references from your component to the context of the entire app. Cloging the app with tons of references to application context or even base context is not a good practice.*
The good way is to use: 
- __this__ keywork if you are retrieving context directly from within the app's methods
- __ActivityName.this__ if you are retrieving context from within any instance of another class inside the running activity, e.g. inside the overriden onClick method of the OnClickListener method
- __getActivity()__ method over objects belonging to a running activity but in context of other (mostly inner) classes, e.g. in fragments
__*You can also use WeakReference<Context> to provide a weak reference to the context which is less prone to creating memory leaks. To retrieve the context object from the WeakReference object you need to call .get() method over it.*__
```java
Intent i = new Intent(this,AnotherActivity.class);
```


### Official documentation:
- [Resources](https://developer.android.com/guide/topics/resources/providing-resources)
- [Intents](https://developer.android.com/guide/components/intents-filters)
- [Common implicit intents](https://developer.android.com/guide/components/intents-common)
- [Activity lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)