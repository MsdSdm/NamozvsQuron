package bilibqoy.mohirjonnnikibuilova.namozvsquron.SuraListen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSure extends SQLiteOpenHelper {
    public DatabaseSure(Context context) {
        super(context, "quranDataBase", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sureTable(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,number TEXT,place TEXT,countAyah TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addSure(Sure sure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", sure.getName());
        contentValues.put("number", sure.getNumber());
        contentValues.put("countAyah", sure.getCountAye());
        contentValues.put("place", sure.getPlace());
        long result = db.insert("sureTable", null, contentValues);
        db.close();
        if(result==-1){
            return false;
        }
        else return true;
    }


    public List<Sure> getSureList() {
        List<Sure> sureList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM sureTable", null);
        if (cursor.moveToFirst()) {
            do {
                Sure sure = new Sure();
                sure.setId(cursor.getInt(cursor.getColumnIndex("id")));
                sure.setName(cursor.getString(cursor.getColumnIndex("name")));
                sure.setNumber(cursor.getString(cursor.getColumnIndex("number")));
                sure.setCountAye(cursor.getString(cursor.getColumnIndex("countAyah")));
                sure.setPlace(cursor.getString(cursor.getColumnIndex("place")));
                sureList.add(sure);
            } while (cursor.moveToNext());

        }
        db.close();
        return sureList;
    }

    public boolean updateData(Sure sure){
        long result;
        SQLiteDatabase db=this.getWritableDatabase();
        String whereClause="id=?";
        String [] whereArgs=new String[] {String.valueOf(sure.getId())};
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",sure.getId());
        contentValues.put("name",sure.getName());
        contentValues.put("number",sure.getNumber());
        contentValues.put("countAyah",sure.getCountAye());
        contentValues.put("place",sure.getPlace());
        result=db.update("sureTable",contentValues,whereClause,whereArgs);
        db.close();
        if(result==-1){
            return false;
        }
        else return  true;
    }



}
