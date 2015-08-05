package com.example.db;
/**
 * 
 * @author LiQiong
 *
 */
public class Person {
	private int id;
	private String name;
	private String personName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", personName=" + personName + "]";
	}

}
