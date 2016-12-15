package com.chazuo.czlib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiQiong
 */
public class SaveDBImpl implements ISaveDB {
    private final static String TAG = SaveDBImpl.class.getSimpleName();
    private SQLiteDatabase sqlDb;

    public SQLiteDatabase getSqlDb() {
        return sqlDb;
    }

    public void setSqlDb(SQLiteDatabase sqlDb) {
        this.sqlDb = sqlDb;
    }


    public SaveDBImpl(SQLiteDatabase sqlDb) {
        this.sqlDb = sqlDb;
    }

    @Override
    public <T> long save(T t) {
        synchronized (TAG) {
            String simpleTableName = DatabaseUtil.getTableName(t.getClass());
            if (!DatabaseUtil.isExistTable(sqlDb, simpleTableName)) {
                String sql = DatabaseUtil.createTableSql(t.getClass());
                try {
                    sqlDb.execSQL(sql);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return insertValue(simpleTableName, t);
        }
    }

    @Override
    public <T> void saveAll(List<T> listClazzs) {
        synchronized (TAG) {
            if (listClazzs.size() > 0) {
                String simpleTableName = DatabaseUtil.getTableName(listClazzs.get(0).getClass());
                if (!DatabaseUtil.isExistTable(sqlDb, simpleTableName)) {
                    String sql = DatabaseUtil.createTableSql(listClazzs.get(0).getClass());
                    sqlDb.execSQL(sql);
                }
                for (int i = 0; i < listClazzs.size(); i++) {
                    insertValue(simpleTableName, listClazzs.get(i));
                }
            } else {
                Log.e(TAG, "传进来的List是空的！......save List<T> is null!");
            }
        }
    }

    /**
     * @param table
     * @param t
     */
    private <T> long insertValue(String table, T t) {
        DatabaseUtil.isFieldSame(sqlDb, t);
        ContentValues values = DatabaseUtil.valuesGet(t);
        long insertNum = sqlDb.insert(table, null, values);
        return insertNum;
    }
}