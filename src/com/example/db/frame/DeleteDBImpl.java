package com.example.db.frame;

import android.database.sqlite.SQLiteDatabase;
/**
 * 
 * @author LiQiong
 *
 */
public class DeleteDBImpl implements IDeleteDB {
	private SQLiteDatabase sqlDb;
	public DeleteDBImpl(SQLiteDatabase sqlDb){
		this.sqlDb=sqlDb;
	}
	@Override
	public <T> void delete(Class<T> t, String whereClause, String[] whereArgs) {
		sqlDb.delete(t.getSimpleName(), whereClause, whereArgs);
	}

	@Override
	public <T> void deleteAll(Class<T> t) {
		sqlDb.delete(t.getSimpleName(), null, null);
	}
}