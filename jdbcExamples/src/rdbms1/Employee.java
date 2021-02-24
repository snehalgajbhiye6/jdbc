package rdbms1;

import java.util.List;

public class Employee {

	private int id;
	private String name;
	private List<Address>adresses;
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
	public List<Address> getAdresses() {
		return adresses;
	}
	public void setAdresses(List<Address> adresses) {
		this.adresses = adresses;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
	}
	
	
}
