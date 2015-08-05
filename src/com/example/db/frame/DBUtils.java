package com.example.db.frame;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * CRUD 操作
 * 
 * @author LiQiong 
 */
public class DBUtils {
	private SQLiteDatabase sqlDb;

	/**
	 * no close
	 * 
	 * @param context
	 */
	public DBUtils(Context context) {
		DatabaseHelper helper = new DatabaseHelper(context);
		sqlDb = helper.getWritableDatabase();
	}

	public <T> void update(T t, String whereClause, String[] whereArgs) {
		IUpdateDB updateImpl = new UpdateDBImpl(sqlDb);
		updateImpl.update(t, whereClause, whereArgs);
		closeDB();
		updateImpl = null;
	}

	public <T> void delete(Class<T> t, String whereClause, String[] whereArgs) {
		IDeleteDB deleteImpl = new DeleteDBImpl(sqlDb);
		deleteImpl.delete(t, whereClause, whereArgs);
		closeDB();
		deleteImpl = null;
	}

	public <T> void deleteAll(Class<T> t) {
		IDeleteDB deleteImpl = new DeleteDBImpl(sqlDb);
		deleteImpl.deleteAll(t);
		closeDB();
		deleteImpl = null;
	}

	/**
	 * 增加一次关闭一次
	 * 
	 * @param t
	 */
	public <T> void save(T t) {
		ISaveDB saveImpl = new SaveDBImpl(sqlDb);
		saveImpl.save(t);
		closeDB();
		saveImpl = null;
	}

	/**
	 * no close database 方便多次使用增加数据
	 * 注意要调用closeDB
	 * @param t
	 */
	public <T> void saveNoClose(T t) {
		ISaveDB saveImpl = new SaveDBImpl(sqlDb);
		saveImpl.save(t);
		saveImpl = null;
	}

	/**
	 * 保存List 列表
	 * 
	 * @param listClazzs
	 */
	public <T> void saveAll(List<T> listClazzs) {
		ISaveDB saveImpl = new SaveDBImpl(sqlDb);
		saveImpl.saveAll(listClazzs);
		closeDB();
		saveImpl = null;
	}

	/**
	 * 查询所有
	 * 
	 * @param t
	 * @return
	 */
	public <T> List<T> findAll(Class<T> t) {
		IFindDB findImpl = new FindDBImpl(sqlDb);
		List<T> tList = findImpl.findAll(t);
		closeDB();
		return tList;
	}

	/**
	 * query 和一些条件
	 * 
	 * @param t
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return List<T>{@link www.sevenga.com}
	 */
	public <T> List<T> find(Class<T> t, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		IFindDB findImpl = new FindDBImpl(sqlDb);
		List<T> tList = findImpl.find(t, columns, selection, selectionArgs, groupBy, having, orderBy);
		closeDB();
		return tList;
	}

	/**
	 * close data base
	 */
	public void closeDB() {
		sqlDb.close();
	}
}