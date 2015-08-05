package com.example.db.frame;

/**
 * 
 * @author LiQiong
 *
 */
public interface IDeleteDB {
	public <T> void delete(Class<T> t, String whereClause, String[] whereArgs);

	public <T> void deleteAll(Class<T> t);
}
