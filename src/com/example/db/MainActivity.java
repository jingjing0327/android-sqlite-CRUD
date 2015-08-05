package com.example.db;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.db.frame.DBUtils;
/**
 * 
 * @author LiQiong
 * 
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 保存全部-----------------------------------
		List<Person> listClazzs = new ArrayList<Person>();
		for (int i = 0; i < 20; i++) {
			Person xzoo = new Person();
			xzoo.setId(i);
			xzoo.setName("name名字" + i);
			xzoo.setPersonName("personName呵呵" + i);
			listClazzs.add(xzoo);
		}
		new DBUtils(this).saveAll(listClazzs);
		//---------------------------------------------
		//```````````````````````````````````````````````````````````````
		// 保存单个---------------------------------
		Person p = new Person();
		p.setId(888);
		p.setName("name888");
		p.setPersonName("personName888");
		new DBUtils(this).save(p);
		//---------------------------------------------
		//```````````````````````````````````````````````````````````````
		// 查询所有------------------------------------
		Log.e("MainActivity", new DBUtils(this).findAll(Person.class).toString());
		//---------------------------------------------
		//```````````````````````````````````````````````````````````````
		// 删除所有-----------------------------------
		new DBUtils(this).deleteAll(Person.class);
		//---------------------------------------------
		//```````````````````````````````````````````````````````````````
		// 删除-----------------------
		String[] whereArgs = { "0" };
		new DBUtils(this).delete(Person.class, "id=?", whereArgs);
		//-----------------------------
		//```````````````````````````````````````````````````````````````
		// 修改数据------------------
		Person p1 = new Person();
		p1.setName("name888");
		p1.setId(88888);
		p1.setPersonName("personName888");
		String[] whereArgs1 = { "0" };
		new DBUtils(this).update(p1, "id=?", whereArgs1);
		//---------------------------------------
	}
}