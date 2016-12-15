package com.chazuo.czlib.db;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.chazuo.czlib.module.impl.CZController;

/**
 * @author LiQiong
 */
public class UpdateDBImpl implements IUpdateDB {
    private SQLiteDatabase sqlDb;

    public SQLiteDatabase getSqlDb() {
        return sqlDb;
    }

    public void setSqlDb(SQLiteDatabase sqlDb) {
        this.sqlDb = sqlDb;
    }

    public UpdateDBImpl(SQLiteDatabase sqlDb) {
        this.sqlDb = sqlDb;
    }

    @Override
    public <T> int update(T t, String whereClause, String[] whereArgs) {
        try {
            DatabaseUtil.isFieldSame(sqlDb, t);
            ContentValues values = DatabaseUtil.valuesGet(t);
            // the number of rows affected
            String tableName = DatabaseUtil.getTableName(t.getClass());
            int affected = sqlDb.update(tableName, values, whereClause, whereArgs);
            return affected;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    @Override
    public void update(String sql) {
        try {
            sqlDb.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}