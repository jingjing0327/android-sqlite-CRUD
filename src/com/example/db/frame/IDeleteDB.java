package com.example.db.frame;


/**
 * 
 * @author LiQiong
 *
 */
public interface IDeleteDB {
	public <T> int delete(Class<T> t, String whereClause, String[] whereArgs);

	public <T> int deleteAll(Class<T> t);
}
