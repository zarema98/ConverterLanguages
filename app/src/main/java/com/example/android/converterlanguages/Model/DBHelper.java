package com.example.android.converterlanguages.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private Context mContext;
    public static final String DATABASE_NAME = "converterDb.db";
    public static final int DATABASE_VERSION = 3;

    private String DATABASE_LOCATION = "";
    private String DATABASE_FULL_PATH = "";

    private final String TBL_LANGUAGES = "languages";

    private final String COL_ID = "id";
    private final String COL_C_PLUS = "c_plus";
    private final String COL_PASCAL = "pascal";


    public SQLiteDatabase mDB;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContext = context;
        DATABASE_LOCATION = "data/data/" + mContext.getPackageName() + "/databases/";
        // DATABASE_LOCATION = mContext.getFilesDir().getPath() + mContext.getPackageName() + "/databases/";
        // DATABASE_LOCATION = mContext.getFilesDir().getPath();
        DATABASE_FULL_PATH = DATABASE_LOCATION + DATABASE_NAME;

        if(!isExistingDB()) {
            try {
                File dbLocation = new File(DATABASE_LOCATION);
                dbLocation.mkdirs();
                extractAssetToDatabaseDirectory(DATABASE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        mDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_FULL_PATH, null);

    }

    boolean isExistingDB() {
        File file  = new File(DATABASE_FULL_PATH);
        return file.exists();
    }


    public void extractAssetToDatabaseDirectory(String fileName) throws IOException{
        int length;
        InputStream sourceDatabase = this.mContext.getAssets().open(fileName);
        File destinationPath = new File(DATABASE_FULL_PATH);
        OutputStream destination = new FileOutputStream(destinationPath);

        byte[] buffer = new byte[32768];
        while ((length = sourceDatabase.read(buffer)) > 0 ) {
            destination.write(buffer, 0, length);
        }
        sourceDatabase.close();
        destination.flush();
        destination.close();
    }

//    public boolean isEmpty () {
//        String q = "SELECT count(*) FROM users";
//        Cursor result = mDB.rawQuery(q, null);
//        result.moveToFirst();
//        int count = result.getInt(0);
//        if(count > 0) return false;
//        else return true;
//    }


    public void addOne(String value) {
        try {
            String q = "INSERT INTO languages(["+COL_C_PLUS+"]) VALUES (?);";
            mDB.execSQL(q, new Object[]{value});
        } catch (SQLException ex) {

        }
    }

    public String search(String key) {
        String q = "SELECT  ["+COL_PASCAL+"] FROM  ["+TBL_LANGUAGES+"] WHERE ["+COL_PASCAL+"] LIKE '"+key+"%'";
        Cursor result = mDB.rawQuery(q, null);
        String name = "";
        while(result.moveToNext()) {
            name = result.getString(result.getColumnIndex(COL_PASCAL));
        }
        return name;
    }

    public String searchCPlus(String key) {
        String q = "SELECT  ["+COL_C_PLUS+"] FROM  ["+TBL_LANGUAGES+"] WHERE ["+COL_C_PLUS+"] LIKE '"+key+"%'";
        Cursor result = mDB.rawQuery(q, null);
        String name = "";
        while(result.moveToNext()) {
            name = result.getString(result.getColumnIndex(COL_C_PLUS));
        }
        return name;
    }

    public String getCPlus (String pascal) {
        String q = "SELECT  ["+COL_C_PLUS+"] FROM  ["+TBL_LANGUAGES+"] WHERE ["+COL_PASCAL+"] = (?)";
        Cursor result = mDB.rawQuery(q, new String[]{pascal});
        String name = "";
        while(result.moveToNext()) {
            name = result.getString(result.getColumnIndex(COL_C_PLUS));
        }
        return name;
    }

    public String getPascal (String pascal) {
        String q = "SELECT  ["+COL_PASCAL+"] FROM  ["+TBL_LANGUAGES+"] WHERE ["+COL_C_PLUS+"] = (?)";
        Cursor result = mDB.rawQuery(q, new String[]{pascal});
        String name = "";
        while(result.moveToNext()) {
            name = result.getString(result.getColumnIndex(COL_PASCAL));
        }
        return name;
    }

    public void addTwo(String value, String cValue) {
        try {
            String q = "UPDATE languages SET "+COL_PASCAL+" = (?) WHERE ["+COL_C_PLUS+"] = (?);";
            mDB.execSQL(q, new Object[]{value, cValue});
        } catch (SQLException ex) {

        }
}

    public void deleteAll() {
        try {
            String q = "DELETE from languages";
            mDB.execSQL(q, null);
        } catch (SQLException ex) {

        }
    }

    public void updateCPlus(String value, String cValue) {
        try {
            String q = "UPDATE languages SET "+COL_C_PLUS+" = (?) WHERE ["+COL_C_PLUS+"] = (?);";
            mDB.execSQL(q, new Object[]{value, cValue});
        } catch (SQLException ex) {

        }
    }

    public void updatePascal(String value, String cValue) {
        try {
            String q = "UPDATE languages SET "+COL_PASCAL+" = (?) WHERE ["+COL_PASCAL+"] = (?);";
            mDB.execSQL(q, new Object[]{value, cValue});
        } catch (SQLException ex) {

        }
    }

    public void removeOne (String key) {
        try {
            String q = "DELETE FROM languages WHERE upper (["+COL_C_PLUS+"]) = upper(?);";
            mDB.execSQL(q, new Object[]{key});
        } catch (SQLException ex) {

        }
    }

    public void removeTwo (String key) {
        try {
            String q = "DELETE FROM languages WHERE upper (["+COL_PASCAL+"]) = upper(?);";
            mDB.execSQL(q, new Object[]{key});
        } catch (SQLException ex) {

        }
    }

    public  String getC(int id) {
        String q = "SELECT  ["+COL_C_PLUS+"] FROM  ["+TBL_LANGUAGES+"] WHERE ["+COL_ID+"] = (?)";
        Cursor result = mDB.rawQuery(q,new String[]{String.valueOf(id)});
        String name="";
        while(result.moveToNext()) {
            name = result.getString(result.getColumnIndex(COL_C_PLUS));
        }
        return name;



    }

    public int getId ()  {
        String q = "SELECT  ["+COL_ID+"] FROM  ["+TBL_LANGUAGES+"]";
        Cursor result = mDB.rawQuery(q, null);
        int id=0;
        while(result.moveToNext()) {
            id = result.getInt(result.getColumnIndex(COL_ID));
        }
        return id;

    }

    public ArrayList<String> getAllOne () {
        String q = "SELECT  ["+COL_C_PLUS+"] FROM  ["+TBL_LANGUAGES+"]";
        Cursor result = mDB.rawQuery(q, null);
        ArrayList<String> names = new ArrayList<>();
        while(result.moveToNext()) {
            names.add(result.getString(result.getColumnIndex(COL_C_PLUS)));
        }
        return names;
    }

    public ArrayList<String> getAllTwo () {
        String q = "SELECT  ["+COL_PASCAL+"] FROM  languages";
        Cursor result = mDB.rawQuery(q, null);
        ArrayList<String> names = new ArrayList<>();
        while(result.moveToNext()) {
            names.add(result.getString(result.getColumnIndex(COL_PASCAL)));
        }
        return names;
    }

//    public String getUserSurname (int id) {
//        String q = "SELECT surname FROM users WHERE upper([id]) = "+id+" ";
//        Cursor result = mDB.rawQuery(q, null);
//        String surname = "";
//        while(result.moveToNext()) {
//            surname = result.getString(result.getColumnIndex(COL_ID));
//        }
//        return surname;
//    }
//
//    public void deleteUser(int id) {
//        try {
//            String q = "DELETE FROM users WHERE upper([id]) = upper(?)";
//            mDB.execSQL(q, new Object[]{id});
//        } catch (SQLException ex) {
//
//        }
//    }
//
//    public void addInfo (String name, String surname, int id) {
//        try {
//            String q = "UPDATE  users SET "+COL_NAME+" = (?), "+COL_SURNAME+" = (?) WHERE id = (?)";
//            mDB.execSQL(q, new Object[]{name, surname, id});
//        } catch (SQLException ex) {
//
//        }
//    }
//
//    public void updateSeen (int id) {
//        try {
//            String q = "UPDATE  users SET "+COL_SEEN+" = (?) WHERE id = "+id+" ";
//            mDB.execSQL(q, new String[]{String.valueOf(1)});
//        } catch (SQLException ex) {
//
//        }
//    }
//
//    public int getAuth () {
//        String q = "SELECT `auth` FROM users";
//        Cursor result = mDB.rawQuery(q, null);
//        int  auth = 0;
//        if(result.getCount() <= 0) {
//            auth = 0;
//        } else {
//            while(result.moveToNext()) {
//                auth = result.getInt(result.getColumnIndex(COL_AUTH));
//            }
//        }
//        return auth;
//    }
//
//    public User getUser (int id) {
//        String q = "SELECT * FROM users WHERE upper([id]) = upper(?)";
//        Cursor result = mDB.rawQuery(q, new String[]{String.valueOf(id)});
//        User user = null;
//        while(result.moveToNext()) {
//            user = new User();
//            user.id = result.getInt(result.getColumnIndex(COL_ID));
//            user.token = result.getString(result.getColumnIndex(COL_TOKEN));
//        }
//        return user;
//
//    }
//
//    public String  getToken (int id) {
//        String q = "SELECT token FROM users WHERE upper([id]) = upper(?)";
//        Cursor result = mDB.rawQuery(q, new String[]{String.valueOf(id)});
//        String token="";
//        while(result.moveToNext()) {
//            token = result.getString(result.getColumnIndex(COL_TOKEN));
//        }
//        return token;
//
//    }
//
//    public void updateAuth () {
//        try {
//            String q = "UPDATE  users SET "+COL_AUTH+" = uppper(?)";
//            Cursor result = mDB.rawQuery(q, new String[]{String.valueOf(1)});
//        } catch (SQLException ex) {
//
//        }
//    }
//
//    public int getId () {
//        String q = "SELECT id FROM users";
//        Cursor result = mDB.rawQuery(q, null);
//        int  id = 0;
//        while(result.moveToNext()) {
//            id = result.getInt(result.getColumnIndex(COL_ID));
//        }
//        return id;
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

