package com.chazuo.czlib.db;


import java.util.List;

/**
 * @author LiQiong
 */
public interface IFindDB {
    <T> List<T> findAll(Class<T> t);

    <T> List<T> find(Class<T> t, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy);

    <T> List<T> find(Class<T> t, String selection, String[] selectionArgs);

    <T> int getCount(String sql, String[] paramArgs);

    <T> List<T> rawFind(Class<T> t,String sql,String[] selectionArgs);

}
