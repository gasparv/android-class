package sk.tuke.smartlab.lab3_datapersistence;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.util.List;

import sk.tuke.smartlab.lab3_datapersistence.RoomDb.DbTools;
import sk.tuke.smartlab.lab3_datapersistence.RoomDb.Movie;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends AppCompatActivity {

    List<Movie> insertedMovies;
    private final int PERMISSION_ID_ACCESS_STORAGE = 1000;
    private final String fileName = "movies.txt";
    private final String extPath = "/dataFiles";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //START: ROOM DB Persistence preparation
        DbGetData taskLoadData = new DbGetData();
        Movie newMovie = new Movie("Cats and dogs","2005","Joe Unknown");
        Movie newMovie2 = new Movie("Amazing quest of Ernest Bliss","1936","Alfred Zeisler");
        taskLoadData.execute(newMovie,newMovie2);
        //END: ROOM DB Persistence preparation
    }

    //START: ROOM DB Persistence TASKS in NEW THREAD
    class DbGetData extends AsyncTask<Movie, Integer, List<Movie>>{

        @Override
        protected List<Movie> doInBackground(Movie... movies) {
            List<Movie> data =  DbTools.getDbContext(new WeakReference<Context>(MainActivity.this)).movieDao().getAll();
            if(data.size()==0) {
                DbTools.getDbContext(new WeakReference<Context>(MainActivity.this)).movieDao().insertMovies(movies);
                return DbTools.getDbContext(new WeakReference<Context>(MainActivity.this)).movieDao().getAll();
            }
            else
            return DbTools.getDbContext(new WeakReference<Context>(MainActivity.this)).movieDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
             insertedMovies = movies;
            Toast.makeText(MainActivity.this,insertedMovies.get(0).getDirector(),Toast.LENGTH_LONG).show();
            ((TextView)findViewById(R.id.tv_movie)).setText(insertedMovies.get(0).getTitle());
            saveFirstMovieNameToSharedPreferences("dataKey",insertedMovies.get(0).getTitle());
            if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                writeDataToFileInternal(insertedMovies,fileName);
                writeDataToFileExternal(insertedMovies,fileName, extPath);
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_ID_ACCESS_STORAGE);
            }

        }
    }
    //END: ROOM DB Persistence TASKS in NEW THREAD


    //START: FILE Persistence preparation and tasks - External storage
    private void writeDataToFileExternal(List<Movie> data, String fileName, String relPath){
        if(checkExtStorage()[1]) {
            File storageDir = getExternalStorageDirectory();

            File dir = new File(storageDir.getAbsolutePath() + relPath);
            if (!dir.exists())
                dir.mkdirs();

            File file = new File(dir, fileName);

            try {
                FileOutputStream osWriter = new FileOutputStream(file);

                osWriter.write("--- Tu sa zacina zapis ---".getBytes());
                osWriter.write(("\n").getBytes());
                for(Movie movie:data){
                    osWriter.write(Long.toString(movie.getId()).getBytes());
                    osWriter.write(("\t" + movie.getTitle()).getBytes());
                    osWriter.write(("\t " + movie.getRelease_year()).getBytes());
                    osWriter.write(("\t" + movie.getDirector()).getBytes());
                    osWriter.write(("\n").getBytes());
                }
                osWriter.write("--- Tu sa konci zapis ---".getBytes());
                osWriter.write("\n".getBytes());
                osWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
            }
        }
    }
    private boolean[] checkExtStorage(){
        boolean extStorageMounted = false;
        boolean extStorageCanWrite = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Read and write
            extStorageMounted = extStorageCanWrite = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Only read
            extStorageMounted = true;
            extStorageCanWrite = false;
        } else {
            // No read, no write
            extStorageMounted = extStorageCanWrite = false;
        }
        return new boolean[]{extStorageMounted,extStorageCanWrite};
    }
     //END: FILE Persistence preparation and tasks - External Storage

    //START: FILE Persistence preparation and tasks - internal storage
    private void writeDataToFileInternal(List<Movie> data, String fileName){
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write("--- Tu sa zacina zapis ---".getBytes());
            outputStream.write(("\n").getBytes());
            for(Movie movie:data){
                outputStream.write(Long.toString(movie.getId()).getBytes());
                outputStream.write(("\t" + movie.getTitle()).getBytes());
                outputStream.write(("\t " + movie.getRelease_year()).getBytes());
                outputStream.write(("\t" + movie.getDirector()).getBytes());
                outputStream.write(("\n").getBytes());
            }
            outputStream.write("--- Tu sa konci zapis ---".getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //What happends when the user grants or denies the requested permission to write to external storage
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSION_ID_ACCESS_STORAGE:{
                for(int i = 0;i < permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"Writing to file ...",Toast.LENGTH_LONG).show();
                                writeDataToFileInternal(insertedMovies,fileName);
                                writeDataToFileExternal(insertedMovies,fileName,extPath);
                        }
                        else{
                            //In real cases, this string should not be hardcoded and would be places inside the values/strings.xml file
                            Toast.makeText(this,"Unable to get permissions, have to quit.",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
            }
        }
    }
    //END: FILE Persistence preparation and tasks

    //START: SharedPreferences persistence
        private void saveFirstMovieNameToSharedPreferences(String key, String movieTitle){
            SharedPreferences sp = getSharedPreferences("sk.tuke.smartlab.lab3_datapersistence",Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString(key,movieTitle);
            ed.apply();
        }
    //EMD: SharedPreferences persistence
}
