package com.example.db.frame;

import java.util.List;
/**
 * 
 * @author LiQiong
 *
 */
public interface IFindDB {
	public <T> List<T> findAll(Class<T> t);

	public <T> List<T> find(Class<T> t, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy);
}
