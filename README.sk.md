# Vývoj natívnych mobilných Android aplikácií v jazyku Java
Tento repozitár obsahuje zdrojáky, cvičenia a zopár slidov k vývoju na platformu Android. Tieto zdroje som pôvodne vytvoril pre výučbu predmetu na FEI TU v Košiciach.

### Novinky
Prednášky na AndroidDevSummite 2018  [AndroidDevSummit CA, USA](https://www.youtube.com/playlist?list=PLWz5rJ2EKKc8WFYCR9esqGGY0vOZm2l6e) 7-8.11.2018

### Slidy - teória
- [Slidy 1](https://github.com/gasparv/android-class/tree/master/Lectures-Prednasky/VMIR-1.pptx) - Organizácia kurzu, očakávané výsledky a skúška. Základná konštrukcia mobilných aplikácií.
- [Slidy 2](https://github.com/gasparv/android-class/tree/master/Lectures-Prednasky/VMIR-2.pptx) - Zdroje, adresáre zdrojov, modifikátory, trieda R, pracovanie s rozmermi drawables, kompilačné skripty Gradle, SDK and platformové nástroje ADB.
- [Slidy 3](https://github.com/gasparv/android-class/tree/master/Lectures-Prednasky/VMIR-3.pptx) - Perzistencia dát pomocou Room DB - ORM rámca framework, Koncepcia abstrakcie databázy, Perzistencia dát v súboroch a zdieľaných preferenciách (SharedPreferences).
- [Slidy 4](https://github.com/gasparv/android-class/tree/master/Lectures-Prednasky/VMIR-4.pptx) - Live programovanie - vid. zdrojove kódy. - Ako vytvoriť fungujúci aplikačný ekosystém s REST API Serverom (ASP.NET Core 2.1) a REST API klientom (Android appka)
- [Slidy 4 - REST API Server](https://github.com/gasparv/android-class/tree/master/Lectures-Prednasky/Lecture4_SERVER_ExampleAPIServer) - Zdrojáky pre ASP.NET Core Web API serverovú aplikáciu.
- [Slidy 4 - REST API Client](https://github.com/gasparv/android-class/tree/master/Lectures-Prednasky/Lecture4_CLIENT_RetroFit2_API_Consuming) - Zdrojáky pre Android appku s knižnicou Retrofit2 - konzumácia REST API odpovede.

### Cvičenia s komentárom
*Pozn: Návod v Slovenskom jazyku na každé cvičenie je v jednotlivých adresároch v súboroch README.sk.md*
- [Cvičenie 0](https://github.com/gasparv/android-class/tree/master/Lab0_java_basic) - Opakovanie základných konceptov a objektového programovania v jazyku JAVA
- [Cvičenie 1](https://github.com/gasparv/android-class/tree/master/Lab1_layouts_activities_intents) - Layouty, aktivity, implicitné a explicitné zámery (intent)
- [Cvičenie 2](https://github.com/gasparv/android-class/tree/master/Lab2_RecyclerView) - RecyclerView, ViewHolder vzor, LayoutManager, adaptér pre zoznamy (RecyclerView.Adapter)
- [Cvičenie 3](https://github.com/gasparv/android-class/tree/master/Lab3_DataPersistence) - Oprávnenia žiadané za behu, základy bezpečného delenia úloh do vlákien pomocou triedy AsyncTask, Room ORM, Zápis do súboru v internom úložisku aplikácie, ukladanie konštánt v SharedPreferences
- [Cvičenie 4a](https://github.com/gasparv/android-class/tree/master/Lab4a_Location_LocationListener) - Získavanie používateľovej polohy pomocou rozhrania LocationListener a GPS poskytovateľa
- [Cvičenie 4b](https://github.com/gasparv/android-class/tree/master/Lab4b_Location_FusedLocationProviderClient) - Získavanie používateľovej polohy pomocou Google Play služieb - triedy FusedLocationProviderClient a GPS poskytovateľa
- [Cvičenie 5](https://github.com/gasparv/android-class/tree/master/Lab5_Maps_OSMDroid) - Poskytovanie máp pomocou rámca OSMDroid s mapami Open Street Maps
- [Cvičenie 6](https://github.com/gasparv/android-class/tree/master/Lab6_RestApiClient_RetroFit2) - Konzumovanie dát zo vzdialeného REST API použítím knižnice Retrofit2
- [Cvičenie 7](https://github.com/gasparv/android-class/tree/master/Lab7_BackgroundStickyService_Location) - použitie vždy-bežiacej služby na pozadí, za účelom získavania polohy nezávisle od životného cyklu aktivity.
- [Cvičenie 7a](https://github.com/gasparv/android-class/tree/master/Lab7a_IntentService) - Running batch jobs in a separate worker thread from any activity or service using IntentService.
- [Cvičenie 8](https://github.com/gasparv/android-class/tree/master/Lab8_Sensors) - registrácia listenera senzorov a prijímanie dát.
- [Cvičenie 8a](https://github.com/gasparv/android-class/tree/master/Lab8a_Sensors_Android_Things_Rpi3) - použitie Raspberry Pi 3 s Android Things. Príklad obsahuje RC522 RFID zápis a čítanie a BMP280 I2C meteo senzor (teplota, atm. tlak)
- [Cvičenie 9](https://github.com/gasparv/android-class/tree/master/Lab9_SystemBroadcastReceiver) - registrácia BroadcastReceivera pre príjem systémových broadcastov (vysielaní) 
