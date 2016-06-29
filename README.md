# android-sqlite-CRUD
android sqlite CRUD <br>
// 保存全部-----------------------------------
List<Person> listClazzs = new ArrayList<Person>();
for (int i = 0; i < 20; i++) {
	Person xzoo = new Person();
	xzoo.setId(i);
	xzoo.setName("name名字" + i);
	xzoo.setPersonName("personName呵呵" + i);
	listClazzs.add(xzoo);
}
crud.saveAll(listClazzs);
//---------------------------------------------
//```````````````````````````````````````````````````````````````
// 保存单个---------------------------------
Person p = new Person();
p.setId(888);
p.setName("name888");
p.setPersonName("personName888");
crud.save(p);
//---------------------------------------------
//```````````````````````````````````````````````````````````````
// 查询所有------------------------------------
Log.e("MainActivity", crud.findAll(Person.class).toString());
//---------------------------------------------
//```````````````````````````````````````````````````````````````
// 删除所有-----------------------------------
crud.deleteAll(Person.class);
//---------------------------------------------
//```````````````````````````````````````````````````````````````
// 删除-----------------------
String[] whereArgs = { "0" };
crud.delete(Person.class, "id=?", whereArgs);
//-----------------------------
//```````````````````````````````````````````````````````````````
// 修改数据------------------
Person p1 = new Person();
p1.setName("name888");
p1.setId(88888);
p1.setPersonName("personName888");
String[] whereArgs1 = { "0" };
crud.update(p1, "id=?", whereArgs1);
//---------------------------------------