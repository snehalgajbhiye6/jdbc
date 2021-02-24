package callableExamples;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CurdExample {

	Scanner sc = new Scanner(System.in);
	private boolean flag = Boolean.FALSE;

	public void insertEmp() {
		try (CallableStatement cs = JdbcUtility.getConnection().prepareCall("{Call insertEmp(?,?)}");) {
			System.out.println("How many employee do you want to add");
			int i = sc.nextInt();
			for (int j = 0; j < i; j++) {
				Emp emp = new Emp();

				System.out.println("Enter name");
				emp.setName(sc.next());

				System.out.println("Enter mobile");
				emp.setMobile(sc.nextInt());

				cs.setString(1, emp.getName());
				cs.setInt(2, emp.getMobile());
				cs.executeUpdate();
				flag = Boolean.TRUE;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag == Boolean.TRUE) {
			System.out.println("Successfully");
		} else {
			System.out.println("Inerupted");
		}
	}

	public void selectEmp() {
		List<Emp> list = new ArrayList<Emp>();
		try (ResultSet cs = JdbcUtility.executeQuery("{call selectEmp()}");) {
			while (cs.next()) {
				Emp emp = new Emp();
				emp.setId(cs.getInt(1));
				emp.setName(cs.getString(2));
				emp.setMobile(cs.getInt(3));

				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Emp em : list) {
			System.out.println(em.getId() + " " + em.getName() + " " + em.getMobile());
		}
	}

	public void updateEmp() {
		try (CallableStatement cs = JdbcUtility.getConnection().prepareCall("{call updateEmp(?,?)}");) {
			System.out.println("which row do you want to update?");
			int id = sc.nextInt();

			System.out.println("Enter name");
			String name = sc.next();

			cs.setInt(1, id);
			cs.setString(2, name);

			int result = cs.executeUpdate();
			if (result > 0) {
				System.out.println("Successful");
			} else {
				System.out.println("Interupted");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteEmp() {
		try (CallableStatement cs = JdbcUtility.getConnection().prepareCall("{call deleteEmp(?)}");) {
			System.out.println("Which row do you want to delete?");
			int id = sc.nextInt();

			cs.setInt(1, id);

			int result = cs.executeUpdate();
			if (result > 0) {
				System.out.println("Successsful");
			} else {
				System.out.println("interupted");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void sumEmp() {
		try (ResultSet rs = JdbcUtility.executeQuery("{call sumEmp()}");) {
	while(rs.next()){
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void maxEmp() {
		try (ResultSet rs = JdbcUtility.executeQuery("{call maxEmp}");)
		{
	while(rs.next()){
				System.out.println(rs.getInt(1));
			}
		}
		 catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void countEmp() {
		try (ResultSet rs = JdbcUtility.executeQuery("{call countEmp()}");) {
	while(rs.next()){
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void avg()
	{
		try(ResultSet rs=JdbcUtility.executeQuery("{call avg()}");)
		{
			while(rs.next())
			{
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void minEmp() 
	{
		try(ResultSet rs=JdbcUtility.executeQuery("{call minEmp()}");)
		{
			while(rs.next())
			{
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {

		CurdExample ce = new CurdExample();
		// .insertEmp();
		// .selectEmp();
		// e.updateEmp();
		// e.deleteEmp();
	//e.sumEmp();
		//ce.maxEmp();
		//ce.countEmp();
		//ce.avg();
		ce.minEmp();
	}

}
