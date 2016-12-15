package com.chazuo.czlib.db;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author LiQiong
 */
public class DatabaseUtil {
    private final static String DBVarChar = "varchar(200)";
    private final static String DBInt = "integer";
    private final static String DBBoolean = "INTEGER(1)";
    private final static String DBFloat = "FLOAT";
    private final static String DBLong = "INTEGER(4)";
    private final static String DBDouble = "DOUBLE";

    /**
     * 判断是否存在某一张表
     *
     * @param tableName
     * @return true is exist,false is not exist;
     */
    public static boolean isExistTable(SQLiteDatabase sqlDb, String tableName) {
        String sql = "SELECT COUNT(*) as RESULT FROM SQLITE_MASTER WHERE NAME = ?";
        Cursor cursor = sqlDb.rawQuery(sql, new String[]{tableName});
        int count = 0;
        if (cursor.moveToFirst())
            count = cursor.getInt(cursor.getColumnIndex("RESULT"));
        if (count >= 1) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     * create table sql
     *
     * @param <T>
     * @return
     */
    public static <T> String createTableSql(Class<T> clazz) {
        List<FieldDB> fieldDBs = getFieldDB(clazz);
        StringBuffer sqlsb = new StringBuffer();
        sqlsb.append("create table ");
        sqlsb.append(getTableName(clazz));
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
     * @param javaType
     * @return
     */
    public static String javaToDBType(String javaType) {
        if ("String".equals(javaType))
            return DBVarChar;
        if ("int".equals(javaType))
            return DBInt;
        if ("Intent".equals(javaType))
            return DBInt;
        if ("boolean".equals(javaType))
            return DBBoolean;
        if ("long".equals(javaType))
            return DBLong;
        if ("float".equals(javaType))
            return DBFloat;
        if ("double".equals(javaType))
            return DBDouble;
        try {
            throw new Throwable("没有这个类型-->" + javaType);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void isFieldSameByClass(SQLiteDatabase sqlDb, Class<T> t) {
        try {
            String tableName = getTableName(t);
            String sql = "SELECT"
                    + " * "
                    + "FROM "
                    + tableName
                    + " limit" +
                    " 0,1";
            Cursor cursor = sqlDb.rawQuery(sql, null);
            Field[] fields = t.getDeclaredFields();
            String[] columnNames = cursor.getColumnNames();
            cursor.close();
            List<FieldDB> diffField = getDiffField(fields, columnNames);
            for (int i = 0; i < diffField.size(); i++) {
                String sqlAlter = "ALTER "
                        + "TABLE "
                        + tableName
                        + " ADD "
                        + diffField.get(i).getFieldName()
                        + " "
                        + diffField.get(i).getFieldType();

                //即使执行了add 列，也有可能查询失败
                sqlDb.execSQL(sqlAlter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对比字段，主要是看用户是否在增加 或�?
     * <p>
     * 减少字段，现在无法解决，因为sqlite不支持drop COLUMN
     * </p>
     *
     * @param <T>
     * @return
     */
    public static <T> void isFieldSame(SQLiteDatabase sqlDb, T t) {
        isFieldSameByClass(sqlDb, t.getClass());
    }

    /**
     * 获取不一样的字段，然后add COLUMN
     *
     * @param a
     * @param b
     * @return
     */
    private static List<FieldDB> getDiffField(Field[] a, String[] b) {
        List<FieldDB> fields = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            if (!isFieldsExit(a[i].getName(), b) && !a[i].isSynthetic()) {
                //Instant Run特性导致
                String primarykeyStr = null;
                PrimaryKey primarykey = a[i].getAnnotation(PrimaryKey.class);
                AutoPrimaryKey autoPrimaryKey = a[i].getAnnotation(AutoPrimaryKey.class);
                if (primarykey != null) {
                    primarykeyStr = " PRIMARY KEY";
                }
                if (autoPrimaryKey != null) {
                    primarykeyStr = " PRIMARY KEY AUTOINCREMENT";
                }

                FieldDB fieldDB = new FieldDB();
                fieldDB.setFieldName(a[i].getName());
                fieldDB.setFieldType(javaToDBType(a[i].getType().getSimpleName()) + primarykeyStr);
                fields.add(fieldDB);
            }
        }
        return fields;
    }

    /**
     * @param param
     * @param b
     * @return
     */
    private static boolean isFieldsExit(String param, String[] b) {
        for (int i = 0; i < b.length; i++) {
            if (param.equals(b[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param t
     * @return
     */
    public static <T> ContentValues valuesGet(T t) {
        ContentValues values = new ContentValues();
        List<FieldDB> fieldDBs = getFieldDB(t.getClass());
        Method[] methods = t.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (!method.getName().equals("getClass") && method.getName().startsWith("get") || method.getName().startsWith("is")) {
                for (int j = 0; j < fieldDBs.size(); j++) {
                    if (method.getName().toLowerCase(Locale.getDefault())
                            .contains(fieldDBs.get(j).getFieldName().toLowerCase(Locale.getDefault())) &&
                            method.getName().toLowerCase(Locale.getDefault()).length()
                                    - fieldDBs.get(j).getFieldName().toLowerCase(Locale.getDefault()).length() <= 3) {
                        try {
                            Object keyValue = method.invoke(t);
                            if (keyValue == null)
                                keyValue = "";
                            if (keyValue.toString().equals("true"))
                                values.put(fieldDBs.get(j).getFieldName(), 1);
                            else if (keyValue.toString().equals("false"))
                                values.put(fieldDBs.get(j).getFieldName(), 0);
                            else
                                values.put(fieldDBs.get(j).getFieldName(), keyValue.toString());
                            break;
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
     * @param clazz
     * @return
     */
    private static <T> List<FieldDB> getFieldDB(Class<T> clazz) {
        List<FieldDB> fieldDBs = new ArrayList<>();
        FieldDB fieldDB = null;
        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String primarykeyStr = null;
            PrimaryKey primarykey = field.getAnnotation(PrimaryKey.class);
            AutoPrimaryKey autoPrimaryKey = field.getAnnotation(AutoPrimaryKey.class);
            if (primarykey != null) {
                primarykeyStr = " PRIMARY KEY";
            }
            if (autoPrimaryKey != null) {
                primarykeyStr = " PRIMARY KEY AUTOINCREMENT";
            }
            //Instant Run特性导致
            if (field.isSynthetic())
                continue;
            fieldDB = new FieldDB();
            fieldDB.setFieldName(field.getName());
            fieldDB.setFieldType(javaToDBType(field.getType().getSimpleName()) + primarykeyStr);
            fieldDBs.add(fieldDB);
        }
        return fieldDBs;
    }

    /**
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> String getTableName(Class<T> clazz) {
        String tableName = clazz.getCanonicalName().replace(".", "_");
        return tableName;
    }
}