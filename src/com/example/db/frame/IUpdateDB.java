package com.example.db.frame;

/**
 * 
 * @author LiQiong
 *
 */
public interface IUpdateDB {
	public <T> void update(T t, String whereClause, String[] whereArgs);
}
