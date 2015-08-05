# android-sqlite-CRUD
android sqlite CRUD <br>
//保存全部save all  List data<br>
List<Person> listClazzs = new ArrayList<Person>();<br>
for (int i = 0; i < 20; i++) {<br>
Person xzoo = new Person();<br>
xzoo.setId(i);<br>
xzoo.setName("name名字" + i);<br>
xzoo.setPersonName("personName呵呵" + i);<br>
listClazzs.add(xzoo);<br>
}<br>
new DBUtils(this).saveAll(listClazzs);<br>

//a保存单个 save single Class data <br>
Person p = new Person();<br>
p.setId(888);<br>
p.setName("name888");<br>
p.setPersonName("personName888");<br>
new DBUtils(this).save(p);<br>

//查询所有find all data<br>
Log.e("MainActivity", new DBUtils(this).findAll(Person.class).toString());<br>

//删除所有delete all data<br>
new DBUtils(this).deleteAll(Person.class);<br>

//删除delete where args<br>
String[] whereArgs = { "0" };<br>
new DBUtils(this).delete(Person.class, "id=?", whereArgs);<br>

//修改数据update data where agrs<br>
Person p1 = new Person();<br>
p1.setName("name888");<br>
p1.setId(88888);<br>
p1.setPersonName("personName888");<br>
String[] whereArgs1 = { "0" };<br>
new DBUtils(this).update(p1, "id=?", whereArgs1);<br>
