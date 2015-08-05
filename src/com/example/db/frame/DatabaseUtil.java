package com.example.db.frame;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
/**
 * 
 * @author LiQiong
 *
 */
public class DatabaseUtil {
	private final static String DBVarChar = "varchar(100)";
	private final static String DBInt = "int";

	/**
	 * create table sql
	 * 
	 * @param <T>
	 * @param tableName
	 * @param fieldDBs
	 * @return
	 */
	public <T> String createTableSql(Class<T> clazz) {
		List<FieldDB> fieldDBs = getFieldDB(clazz);
		StringBuffer sqlsb = new StringBuffer();
		sqlsb.append("create table ");
		sqlsb.append(clazz.getSimpleName());
		sqlsb.append("(");
		for (int i = 0; i < fieldDBs.size(); i++) {
			sqlsb.append(fieldDBs.get(i).getFieldName());
			sqlsb.append(" ");
			sqlsb.append(fieldDBs.get(i).getFieldType());
			if (i + 1 != fieldDBs.size())
				sqlsb.append(",");
		}
		sqlsb.append(")");
		return sqlsb.toString();
	}

	/**
	 * 
	 * @param javaType
	 * @return
	 */
	private String javaToDBType(String javaType) {
		if ("String".equals(javaType)) {
			return DBVarChar;
		}
		if ("int".equals(javaType)) {
			return DBInt;
		}
		if ("Intent".equals(javaType)) {
			return DBInt;
		}
		return null;
	}

	/**
	 * 
	 * @param t
	 * @return
	 */
	public <T> ContentValues valuesGet(T t) {
		ContentValues values = new ContentValues();
		List<FieldDB> fieldDBs = getFieldDB(t.getClass());
		Method[] methods = t.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (!method.getName().equals("getClass") && method.getName().startsWith("get")) {
				for (int j = 0; j < fieldDBs.size(); j++) {
					if (method.getName().toLowerCase(Locale.getDefault()).contains(fieldDBs.get(j).getFieldName().toLowerCase(Locale.getDefault()))) {
						try {
							values.put(fieldDBs.get(j).getFieldName(), method.invoke(t).toString());
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return values;
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	private <T> List<FieldDB> getFieldDB(Class<T> clazz) {
		List<FieldDB> fieldDBs = new ArrayList<FieldDB>();
		FieldDB fieldDB = null;
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			fieldDB = new FieldDB();
			fieldDB.setFieldType(javaToDBType(field.getType().getSimpleName()));
			fieldDB.setFieldName(field.getName());
			fieldDBs.add(fieldDB);
		}
		return fieldDBs;
	}
}