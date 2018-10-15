# Data persistence - Room, Internal & external storage, shared preferences
*Disclaimer: RoomDb ORM is a simpler approach. If you want more control over the DB, you can use more detailed approach with the [SQLiteOpenHelper and Content providers](https://developer.android.com/training/data-storage/sqlite) 
(I will probably later add a dedicated project Lab3b_SQLiteOpenHelper)*
![Using Room ORM in app diagram](https://github.com/kkui-chi/VMIR/blob/master/images/room-data.jpg)
### Contents:
- This lab presents the recommended possiblity to store files inside a local SQLite database using Room ORM Persistence library
- An example of storing data in a file can be found in [MainActivity.java](https://github.com/kkui-chi/VMIR/blob/master/Lab3_DataPersistence/app/src/main/java/sk/tuke/smartlab/lab3_datapersistence/MainActivity.java) @ lines 84-131 (external storage) & lines 136-152 (internal storage). 
- Runtime permissions lifecycle is used to provide write permissions for ROOM DB ORM as well as for the write external storage method in [MainActivity.java](https://github.com/kkui-chi/VMIR/blob/master/Lab3_DataPersistence/app/src/main/java/sk/tuke/smartlab/lab3_datapersistence/MainActivity.java). 

### HowTo:
--------
##### Setting up the prerequisites
1. Add an app level permission to access external storage inside the [AndroidManifest.xml](https://github.com/kkui-chi/VMIR/blob/master/Lab3_DataPersistence/app/src/main/AndroidManifest.xml) file above the ```<application>``` tag. This is not necessary for Room but for later methods that will write data to a file inside an external storage.
```<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>```
2. In the app's [build.gradle](https://github.com/kkui-chi/VMIR/blob/master/Lab3_DataPersistence/app/build.gradle) add the following content into the ```dependencies``` section
2.1 ```def room_version="1.1.1"``` - specify a variable that will later reference the Room version for other dependencies
2.2 ```implementation "android.arch.persistence.room:runtime:$room_version"``` - import room library with specific version using the predefined variable ```room_version```
2.3 ```annotationProcessor "android.arch.persistence.room:compiler:$room_version"``` - import the library that processes the Room annotations in classes.
----
##### Implementing the Room ORM logic
3. Define entity classes that you want to use inside your database. We added a single entity named ___Movie___ inside the [Movie.java](https://github.com/kkui-chi/VMIR/blob/master/Lab3_DataPersistence/app/src/main/java/sk/tuke/smartlab/lab3_datapersistence/RoomDb/Movie.java) class. 
3.1 Create an empty constructor (this is required by the Room ORM)
3.2 Create private variables that represent columns of the entity (db table). We created Id, Title, Release_year & Director
3.3 Generate getters and setters for each variable by ```RightClick -> Generate... -> Getter & Setter -> select variables -> OK```
3.3 Create a parametric constructor for filling the entire entity
3.4 Annotate private variables and class with Room annotations
.  3.4.1 ```@PrimaryKey(autogenerate=true)```  sets the variable as the column with primary key that will be automatically generated
.  3.4.2 ```@ColumnInfo(name="<column_name>")``` sets the variable as a database column with a specific name that will be used inside the schema.
. 3.4.3 ```@Entity``` sets the entire class as a database entity.
4. Define data access object interface [MovieDao.class](https://github.com/kkui-chi/VMIR/blob/master/Lab3_DataPersistence/app/src/main/java/sk/tuke/smartlab/lab3_datapersistence/RoomDb/MovieDao.java) which is used as a provider for query, insert, update, delete, etc. methods over the entity.
4.1 Create methods that you want to use with the database. We created ```getAll()``` that will return list of all movies, ```getById(int Id)``` that will return a single movie according to it's Id, ```insertMovies(Movie... movie)``` that will insert one or more Movie objects inside the Movie entity and ```deleteMovie(Movie movie)``` that removes the Movie object from the database.
*Note: Usually DAO classes should be separated for each logical data part. In this case, we only have a single entity "Movie" so we only need one DAO class.* 
4.2 Annotate each method and the interface.
4.2.1 ```@Dao``` should be used over the interface to state that this intrface will be used to define the DAO objects
4.2.2 ```@Query("<QUERY_STATEMENT>")``` should be used over a method that selects data from database using the SELECT keyword and retrieves a specific object with specific object type (in our case of type Movie).
4.2.3 ```@Insert``` should be used over a method that inserts the data into DB
4.2.4 ```@Delete``` should be used over a method that deletes the data from DB
5. Create the database abstract class (In our case [MovieDatabase.java](https://github.com/kkui-chi/VMIR/blob/master/Lab3_DataPersistence/app/src/main/java/sk/tuke/smartlab/lab3_datapersistence/RoomDb/MovieDatabase.java)).
5.1 Extend this class by ```RoomDatabase``` base class
5.2 Create a public abstract method that will provide access to DAO objects ```public abstract MovieDao movieDao();```
5.3 Annotate the class with ```@Database(entities={<comma_separated_list_of_entity_classes.class>}, version=<DB_Version>)```
6. Create a static class ([DbTools.cs](https://github.com/kkui-chi/VMIR/blob/master/Lab3_DataPersistence/app/src/main/java/sk/tuke/smartlab/lab3_datapersistence/RoomDb/DbTools.java)) that will provide a singleton instance of the database context. The context generation method should use ```Room.databaseBuilder(Context context, Class<T> contextclass, String dbName).build()``` to return the database context.
-----
##### Implementing the AsyncTask - *DB operations have to run in separate thread*
7. In the [MainActivity.java](https://github.com/kkui-chi/VMIR/blob/master/Lab3_DataPersistence/app/src/main/java/sk/tuke/smartlab/lab3_datapersistence/MainActivity.java) implement the ```AsyncTask<Movie,Integer, List<Movie>>``` async class. This step is necessary because the DB operations can only be carried out outside of the UI thread.
7.1 Create an inner class called ```DbGetData``` that extends ```AsyncTask<Movie, Integer, List<Movie>>```
7.2 Override two of it's methods ```doInBackground(Movie... movies)``` and ```onPostExecute(List<Movie> movies)```.
7.3 In ```doInBackground``` get database context then the DAO object and use the appropriate method to get the data. 
```List<Movie> data =  DbTools.getDbContext(new WeakReference<Context>(MainActivity.this)).movieDao().getAll();```
7.4 In our example I created a sample piece of code that will firstly check if there are any entries in the DB, if not it will call the insert method and insert two movies and will then retrieve data from DB and return them from the AsyncTask (@ lines 55-60).
7.5 In ```onPostExecute()``` method do whatever you need with the data. I created a Toast message with the director's name of the first movie in DB, set the hello world TextView to the title of the first movie & also saved it to sharedPreferences and to internal and external files (see later sections of this lab).
7.6 Instantiate the DbGetData class in the onCreate method and call execute with new movie data (@ lines 41-45)
---
##### Writing into internal storage
8. Create a method ```writeDataToFileInternal(List<Movie> data, String fileName)```
8.1 Create a null ```FileOutputStream outputStream;```
8.2 use try catch to handle IOException and other exceptions when opening a file for writing.
8.3 Assign outputStream with the result of ```openFileOutput(fileName, Context.MODE_PRIVATE)```. The MODE_PRIVATE represents the access mode to the file - can be shared only by the creator app.
8.4 Write bytes to the output stream - all data written to the file need to be converted to bytes. If you want to build strings and then write byte stream use ```StringBuilder``` at first.
8.5 After writing the data, close the outputstream by calling the ```outputStream.close()``` method.
---
##### *Requesting runtime permissions*
To be able to write to external storage, you must request the permission in runtime after (including) Android Marshmallow 6.0 (API 23+)
9. When first using the function that requires the permission in the app check for the permission grant state using 
```if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))```
9.1 If this statement is true continue with the app as normal - e.g. call method to write data to external storage
9.2 Create a global final int variable that will store the request code of the permission ```PERMISSION_ID_ACCESS_STORAGE=1000```
9.3 Otherwise, request the permission using the ```ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_ID_ACCESS_STORAGE);``` statement.
10. Override and implement the ```onRequestPermissionsResult``` method. 
10.1 Use switch case statement to check for the specified request code
10.2 Loop through the permissions and check if the permission is the one which you requested
10.3 For this permission check if the result is ```PackageManager.PERMISSION_GRANTED``` or not.
10.4 If the user granted you the permission to write to external storage continue with writing the file with method ```writeDataToFileExternal```
10.5 Otherwise, inform the user that the app can't run without this permission and quit the app using the ```finish()``` method. 
*Note: You should always inform the user about the reasons that you require the permissions and provide at least some functionality without the permission*.

---
##### Writing into external storage
11. At first you need to check, wheher the device has an external storage mounted and that the app is able to read/write to that storage. Both states can be retrieved by calling the ```Environment.getExternalStorageState()``` method.
11.1 If this state equals the ```Environment.MEDIA_MOUNTED``` it means that the app can read and write to external storage
11.2 If the state equals the ```Environment.MEDIA_MOUNTED_READ_ONLY``` it means that the app can only read the external storage
11.3 Otherwise, the app can neither read nor write to external storage.
12. Get the path to the external storage directory using ```getExternalStorageDirectory()``` method.
13. Use File class to create a directory path (combine the absolute path of the storage directory ```getExternalStorageDirectory().getAbsolutePath()``` with your custom path ```relPath```) and check if it exists using ```.exists()``` method.
13.1 If the directory does not exists use ```.mkdirs()``` over the file object to create the entire path directories.
14. Combine the directory path and the filename info a new file object of File class type.
15. Create a FileOutputStream and instantiate it over the created file object from 14.
16. Write byte data as in writing to internal storage - 8.4.
17. Close the output stream as in 8.5.
---
##### Writing simple key-value data into SharedPreferences
Note: this approach is implemented in ```saveFirstMovieNameToSharedPreferences(String, key, String movieTitle)``` method
18. Create a private method that will write the data for you (```saveFirstMovieNameToSharedPreferences(String, key, String movieTitle)```)
19. Create the __sp__ object from the ```SharedPreferences``` class by calling the ```getSharedPreferences(package_name, MODE)``` method. MODE_PRIVATE is explained in 8.3.
20. To read content of the shared preferences use `sp.get~Type~()`. To write, create `SharedPreferences.Editor ed` object by assigning it to result of the ```sp.edit()``` method.
21. To put key-value data, use ```ed.put~Type~(key, data);``` method.
22. To save all changes to shared preferences call ```ed.apply()```.
*Note: apply() method is asynchronous and does not block the UI thread. If the save has to be immediate, use the commit() method instead. Beware that this method is synchronous, so it may block the UI thread if you save a lot of data in one editing session.*

## GLHF

---

### Official documentation:
- [Room Architecture](https://developer.android.com/training/data-storage/room/)
- [Room - DAO class](https://developer.android.com/training/data-storage/room/defining-data)
- [Save to files](https://developer.android.com/training/data-storage/files)
- [Save to SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences)
- [Permissions mechanism and lifecycle](https://developer.android.com/training/permissions/requesting)