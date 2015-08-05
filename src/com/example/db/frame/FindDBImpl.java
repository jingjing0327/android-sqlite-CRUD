package com.example.db.frame;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 
 * @author LiQiong
 *
 */
public class FindDBImpl implements IFindDB {
	private SQLiteDatabase sqlDb;
	public FindDBImpl(SQLiteDatabase sqlDb){
		this.sqlDb=sqlDb;
	}
	@Override
	public <T> List<T> findAll(Class<T> t) {
		List<T> tList = new ArrayList<T>();
		try {
			Field[] field = t.getDeclaredFields();
			Cursor cursor = sqlDb.query(t.getSimpleName(), null, null, null, null, null, null);
			while (cursor.moveToNext()) {
				T instance = t.newInstance();
				for (int i = 0; i < field.length; i++) {
					Class<?> clazz = field[i].getType();
					String setName = field[i].getName();
					String setNameUpperCase = setName.replaceFirst(setName.substring(0, 1), setName.substring(0, 1).toUpperCase(Locale.getDefault()));
					Method method = t.getMethod("set" + setNameUpperCase, clazz);
					if (clazz.getSimpleName().equals("String")) {
						method.invoke(instance, cursor.getString(cursor.getColumnIndex(setName)));
					} else {
						method.invoke(instance, cursor.getInt(cursor.getColumnIndex(setName)));
					}
				}
				tList.add(instance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tList;
	}
	
	@Override
	public <T> List<T> find(Class<T> t, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		List<T> tList = new ArrayList<T>();
		try {
			Field[] field = t.getDeclaredFields();
			Cursor cursor = sqlDb.query(t.getSimpleName(), columns, selection, selectionArgs, groupBy, having, orderBy);
			while (cursor.moveToNext()) {
				T instance = t.newInstance();
				for (int i = 0; i < field.length; i++) {
					Class<?> clazz = field[i].getType();
					String setName = field[i].getName();
					String setNameUpperCase = setName.replaceFirst(setName.substring(0, 1), setName.substring(0, 1).toUpperCase(Locale.getDefault()));
					Method method = t.getMethod("set" + setNameUpperCase, clazz);
					if (clazz.getSimpleName().equals("String")) {
						method.invoke(instance, cursor.getString(cursor.getColumnIndex(setName)));
					} else {
						method.invoke(instance, cursor.getInt(cursor.getColumnIndex(setName)));
					}
				}
				tList.add(instance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tList;
	}
}