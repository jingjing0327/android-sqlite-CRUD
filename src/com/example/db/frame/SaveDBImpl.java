package com.example.db.frame;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * 
 * @author LiQiong
 *
 */
public class SaveDBImpl implements ISaveDB {

	private SQLiteDatabase sqlDb;

	public SaveDBImpl(SQLiteDatabase sqlDb) {
		this.sqlDb = sqlDb;
	}

	@Override
	public <T> void save(T t) {
		String simpleTableName = t.getClass().getSimpleName();

		if (!isExist(simpleTableName)) {
			DatabaseUtil databaseUtil = new DatabaseUtil();
			String sql = databaseUtil.createTableSql(t.getClass());
			sqlDb.execSQL(sql);
			databaseUtil = null;
		}
		insertValue(simpleTableName, t);
	}

	@Override
	public <T> void saveAll(List<T> listClazzs) {
		if (listClazzs.size() > 0) {
			String simpleTableName = listClazzs.get(0).getClass().getSimpleName();
			if (!isExist(simpleTableName)) {
				DatabaseUtil databaseUtil = new DatabaseUtil();
				String sql = databaseUtil.createTableSql(listClazzs.get(0).getClass());
				sqlDb.execSQL(sql);
				databaseUtil = null;
			}
			for (int i = 0; i < listClazzs.size(); i++) {
				insertValue(simpleTableName, listClazzs.get(i));
			}
		} else {
			Log.e("!!!!!!SaveDBImpl!!!!!!", "传进来的List是空的！......save List<T> is null!");
		}
	}

	/**
	 * 
	 * @param table
	 * @param t
	 */
	private <T> void insertValue(String table, T t) {
		DatabaseUtil databaseUtile = new DatabaseUtil();
		ContentValues values = databaseUtile.valuesGet(t);
		sqlDb.insert(table, null, values);
		databaseUtile = null;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> findAllTable(SQLiteDatabase sqlData) {
		String sql = "SELECT NAME FROM SQLITE_MASTER WHERE NAME <> 'android_metadata'";
		List<String> names = new ArrayList<String>();
		Cursor cursor = sqlData.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			names.add(cursor.getString(cursor.getColumnIndex("name")));
		}
		return names;
	}

	/**
	 * 判断是否存在某一张表
	 * 
	 * @param tableName
	 * @return true is exist,false is not exist;
	 */
	private boolean isExist(String tableName) {
		List<String> tableNames = findAllTable(sqlDb);
		boolean isExist = false;
		for (int i = 0; i < tableNames.size(); i++) {
			if (tableName.equals(tableNames.get(i))) {
				isExist = true;
			}
		}
		return isExist;
	}
}