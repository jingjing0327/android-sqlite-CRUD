### android sqlite CRUD

``` java
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
```

``` java
// 保存单个---------------------------------
Person p = new Person();
p.setId(888);
p.setName("name888");
p.setPersonName("personName888");
crud.save(p);
//---------------------------------------------
```


``` java
// 查询所有------------------------------------
Log.e("MainActivity", crud.findAll(Person.class).toString());
//---------------------------------------------
```

``` java
// 删除所有-----------------------------------
crud.deleteAll(Person.class);
//---------------------------------------------
```

``` java
// 删除-----------------------
String[] whereArgs = { "0" };
crud.delete(Person.class, "id=?", whereArgs);
//-----------------------------
```

``` java
// 修改数据------------------
Person p1 = new Person();
p1.setName("name888");
p1.setId(88888);
p1.setPersonName("personName888");
String[] whereArgs1 = { "0" };
crud.update(p1, "id=?", whereArgs1);
//---------------------------------------
```

```java
// 使用-----------------------------
allprojects {
	repositories {
		...
		maven { url 'https://www.jitpack.io' }
	}
}

dependencies {
        compile 'com.github.jingjing0327:android-sqlite-CRUD:0.1.0'
}
//--------------------------------------------
```
