package com.example.nguyenvanphituoc.foody.DAO;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by PhiTuocPC on 4/2/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db_foody_copy.sqlite";
    //
    private static String DATABASE_PATH;

    private SQLiteDatabase myDataBase;

    private final Context dbContext;

    public static SQLiteDatabase initDatabase(Activity activity) {
        try {
            String outFileName = activity.getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME;
            File f = new File(outFileName);
            if (!f.exists()) {
                InputStream e = activity.getAssets().open(DATABASE_NAME);
                File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");
                if (!folder.exists()) {
                    if(!folder.mkdir())
                        return null;
                }
                FileOutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = e.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                e.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activity.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    private void copyDataBase() throws IOException {
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        File f = new File(outFileName);
        if (!f.exists()) {
            InputStream e = dbContext.getAssets().open("database/" + DATABASE_NAME);
            File folder = new File(DATABASE_PATH);
            if (!folder.exists()) {
                if(!folder.mkdir())
                    return;
            }
            FileOutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];

            int length;
            while ((length = e.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            e.close();
        }
    }
    private void openDataBase() throws SQLException {
        String dbPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        boolean exist = false;
        try {
            String dbPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(dbPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.v("db log", "database does't exist");
        }

        if (checkDB != null) {
            exist = true;
            checkDB.close();
        }
        return exist;
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.dbContext = context;
        DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        // checking database and open it if exists
        if (checkDataBase()) {
            openDataBase();
        } else {
            try {
//                this.getReadableDatabase();
                copyDataBase();
                this.close();
                openDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
            Toast.makeText(context, "Initial database is created", Toast.LENGTH_LONG).show();
        }
    }


    public SQLiteDatabase getMyDataBase() {
        return myDataBase;
    }

    @Override
    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
