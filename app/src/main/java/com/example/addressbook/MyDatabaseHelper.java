package com.example.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @ProjectName: AddressBook
 * @Package: com.example.addressbook
 * @QQ: 1025377230
 * @Author: Fonrye
 * @CreateDate: 2021/4/7 22:54
 * @Email: fonrye@163.com
 * @Version: 1.0
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Contacts.db";
    private static final String TABLE_NAME = "Contacts";
    private static final int DB_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table " + TABLE_NAME
                + " (_id integer primary key autoincrement, "
                + "_no text not null, "
                + "_name text not null, "
                + "_pnumber text);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    /**
     * 添加新数据
     *
     * @param entity
     * @return
     *
     */

    public long insert(Contact entity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_no", entity.getNo());
        values.put("_name", entity.getName());
        values.put("_pnumber", entity.getPhoneNumber());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    /**
     * 修改数据
     *
     * @param entity
     * @return
     */
    public int update(Contact entity) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_no = ?";
        String[] whereArgs = {entity.getNo()};

        ContentValues values = new ContentValues();
        values.put("_no", entity.getNo());
        values.put("_name", entity.getName());
        values.put("_pnumber", entity.getPhoneNumber());

        int rows = db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
        return rows;
    }

    /**
     * 删除
     *
     * @param entity
     * @return
     */
    public int delete(Contact entity) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_no = ?";
        String[] whereArgs = {entity.getNo()};

        int rows = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        return rows;
    }

    /**
     * 查询全部信息
     *
     * @return
     */
    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    /**
     * 模糊查询
     *
     * @param entity
     * @return
     */
    public Cursor query(String entity) {
        SQLiteDatabase db = getReadableDatabase();
//        sql风险 ，占位符？
        return db.query(TABLE_NAME, null, "_name like'%" + entity + "%'", null, null, null, null);
    }
}
