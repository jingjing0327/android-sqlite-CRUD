package com.example.db.frame;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
/**
 * 
 * @author LiQiong
 *
 */
public class UpdateDBImpl implements IUpdateDB {
	private SQLiteDatabase sqlDb;

	public UpdateDBImpl(SQLiteDatabase sqlDb) {
		this.sqlDb = sqlDb;
	}

	@Override
	public <T> void update(T t, String whereClause, String[] whereArgs) {
		DatabaseUtil databaseUtil = new DatabaseUtil();
		ContentValues values = databaseUtil.valuesGet(t);
		sqlDb.update(t.getClass().getSimpleName(), values, whereClause, whereArgs);
		databaseUtil = null;
	}
}