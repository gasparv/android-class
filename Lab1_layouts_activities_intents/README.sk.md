# Zdroje, životný cyklus aktivity a zámery (intent)
### Obsah:
- Toto cvičenie prezentuje úlohu statických zdrojov (adresár res) - súbory a adresáre, ktoré sa používajú najmä pri dizajnovaní aplikácie. Modifikátory zdrojov sú prezentované v [__res/values/strings.xml__](https://github.com/gasparv/android-class/blob/master/Lab1_layouts_activities_intents/app/src/main/res/values/strings.xml) a [__res/values-sk/strings.xml__](https://github.com/gasparv/android-class/blob/master/Lab1_layouts_activities_intents/app/src/main/res/values-sk/strings.xml). 
- Príklad implicitného a explicitného zámeru (intentu) nájdete v [MainActivity.java](https://github.com/gasparv/android-class/blob/master/Lab1_layouts_activities_intents/app/src/main/java/sk/tuke/smartlab/lab1_layouts_activities_intents/MainActivity.java) @ riadky 27-28 (explicitný) & riadky 35-41 (implicitné). 
- Životný cyklus aktivity je vysvetlený v [Slidoch 1 - Snímok 25](https://github.com/gasparv/android-class/blob/master/Lectures-Prednasky/VMIR-1.pptx). 

Ako na to:
1. Vytvorenie modifikátorov pre zdroje vyžaduje vytvoriť príslušné adresáre kliknutím pravým tlačidom na res adresár a výberom *Android Resource Directory*. Vyberte kategóriu adresáru (podľa typu statických zdrojov - napr. values) vyberte modifikátory a vytvorte adresár. Modifikátory sa použijú podľa poradia od najviac špecifických po najviac všeobecné (ak sa nenajde konkrétny zdroj pre danú kombináciu modifikátorov) [vid Slidy 2 - Snímok 5](https://github.com/gasparv/android-class/blob/master/Lectures-Prednasky/VMIR-2.pptx).
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
