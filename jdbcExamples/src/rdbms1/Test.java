package rdbms1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
	Scanner sc = new Scanner(System.in);
	private static Connection con = null;
	private PreparedStatement ps = null;
	private static String url = "jdbc:mysql://localhost:3306/rdbms1";
	private static String username = "root";
	private static String password = "root";
	boolean flag = Boolean.FALSE;

	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert() throws SQLException {
		System.out.println("How many employee do you want to add");
		int i = sc.nextInt();
		List<Employee> employee = new ArrayList<Employee>();
		for (int j = 0; j < i; j++) {
			Employee emp = new Employee();
			System.out.println("Enter employee name");
			emp.setName(sc.next());

			List<Address> address = new ArrayList<>();
			System.out.println("How many address do you want");
			int m = sc.nextInt();
			for (int k = 0; k < m; k++) {
				Address adder = new Address();
				System.out.println("Enter city");
				adder.setCity(sc.next());
				address.add(adder);
			}
			emp.setAdresses(address);
			employee.add(emp);
		}

		for (Employee em : employee) {
			ps = con.prepareStatement("insert into employee(name) values(?)");
			ps.setString(1, em.getName());
			int result = ps.executeUpdate();
			if (result > 0) {
				ps = con.prepareStatement("select max(id) from employee");
				ResultSet rs = ps.executeQuery();
				if (rs.next())
					em.setId(rs.getInt(1));
				    System.out.println(em.getId());
				for (Address add : em.getAdresses()) {
					ps = con.prepareStatement("insert into address(city,eid)values(?,?)");
					ps.setString(1, add.getCity());
					ps.setInt(2, em.getId());
					ps.executeUpdate();
					flag = Boolean.TRUE;
				}
			}
			if (flag) {
				System.out.println("success");
			} else
				System.out.println("failed");
		}

	}

	public void select() throws SQLException {
		List<Employee> employees = new ArrayList<Employee>();
		ResultSet rs = con.prepareStatement("select * from employee").executeQuery();
		while (rs.next()) {
			Employee employee = new Employee();
			employee.setId(rs.getInt(1));
			employee.setName(rs.getString(2));
			employees.add(employee);
		}

		for (Employee employee : employees) {
			List<Address> addresses = new ArrayList<>();
			ResultSet rs1 = con
					.prepareStatement("select a.aid,a.city from employee e inner " + "join address a on e.id=a.eid ")
					.executeQuery();
			while (rs1.next()) {
				Address address = new Address();
				address.setAid(rs1.getInt(1));
				address.setCity(rs1.getString(2));
				address.setEmployee(employee); // has-a
				addresses.add(address);
			}

			employee.setAdresses(addresses);// has-a
		}

		for (Employee employee : employees) {
			System.out.println(employee.getId() + "\t" + employee.getName());
			for (Address address : employee.getAdresses()) {
				System.out.println("\t" + address.getAid() + "\t" + address.getCity());
			}
		}
	}

	public static void main(String[] args) throws SQLException {
		Test t = new Test();

		t.insert();
		t.select();

	}
}
