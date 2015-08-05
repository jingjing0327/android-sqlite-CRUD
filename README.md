# android-sqlite-CRUD
android sqlite CRUD 
保存全部save all  List data <br>
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
// 保存单个- save single Class data-------------------------------- 
Person p = new Person();
p.setId(888);
p.setName("name888");
p.setPersonName("personName888");
new DBUtils(this).save(p);
//---------------------------------------------
//```````````````````````````````````````````````````````````````
// 查询所有-find all data-----------------------------------
Log.e("MainActivity", new DBUtils(this).findAll(Person.class).toString());
//---------------------------------------------
//```````````````````````````````````````````````````````````````
// 删除所有-delete all data----------------------------------
new DBUtils(this).deleteAll(Person.class);
//---------------------------------------------
//```````````````````````````````````````````````````````````````
// 删除-delete where args----------------------
String[] whereArgs = { "0" };
new DBUtils(this).delete(Person.class, "id=?", whereArgs);
//-----------------------------
//```````````````````````````````````````````````````````````````
// 修改数据-update data where agrs-----------------
Person p1 = new Person();
p1.setName("name888");
p1.setId(88888);
p1.setPersonName("personName888");
String[] whereArgs1 = { "0" };
new DBUtils(this).update(p1, "id=?", whereArgs1);
//---------------------------------------
