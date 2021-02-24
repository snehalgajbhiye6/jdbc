package preparedStatementex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import statmentsExamples.Emp;

public class CurdExample {
	Scanner sc=new Scanner(System.in);
	private static Connection con=null;
	private static String url="jdbc:mysql://localhost:3306/user";
	private static String userName="root";
	private static String password="root";
	private boolean flag=Boolean.FALSE;
	static
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,userName,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertStud()
	{
		try(PreparedStatement ps= con.prepareStatement("insert into emp(name,mobile)values(?,?)");)
		{
			System.out.println("How many emplyoee do you want to add");
			int i=sc.nextInt();
			for(int j=0;j<i;j++)
			{
	    		Emp emp=new Emp();
                System.out.println("Enter name");
                emp.setName(sc.next());
				
				ps.setString(1,emp.getName());
				ps.setInt(2, emp.getMobile());
				ps.executeUpdate();
				flag=Boolean.TRUE;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(flag==Boolean.TRUE)
		{
			System.out.println("Inserted Successfully");
		}
		else
		{
			System.out.println("Interupted");
		}
	}
	
	public void selectEmp() {
		List<Emp> list=new ArrayList<Emp>();
		try(PreparedStatement ps=con.prepareStatement("select * from emp;"))
				{
					ResultSet rs=ps.executeQuery();
					while(rs.next())
					{
						Emp emp=new Emp();
						emp.setId(rs.getInt(1));
						emp.setName(rs.getString(2));
						emp.setMobile(3);
						list.add(emp);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		for(Emp emp:list)
		{
			System.out.println(emp.getId()+" "+emp.getName()+" "+emp.getMobile());
		}
	}
	
	public void updateEmp()
	{
		
		try(PreparedStatement ps=con.prepareStatement("update emp set name=? where id=?;");)
		{
			System.out.println("Which row do you want to update");
			int id=sc.nextInt();
			System.out.println("enter name");
			String name=sc.next();
			
			ps.setString(1, name);
			ps.setInt(2,id);
			
			int result=ps.executeUpdate();
			if(result>0)
			{
				System.out.println("Successfully");
			}
			else
			{
				System.out.println("Interupted");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete()
	{
		try(PreparedStatement ps=con.prepareStatement("delete from emp where id=?;");)
		{
			System.out.println("Which row do you want to delete");
			int id=sc.nextInt();
			
			ps.setInt(1, id);
			int result=ps.executeUpdate();
			if(result>0)
			{
				System.out.println("Successful");
			}
			else
			{
				System.out.println("interupted");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void max()
	{
		try(PreparedStatement ps=con.prepareStatement("select max(id) from emp;");)
		{
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void min()
	{
		try(PreparedStatement ps=con.prepareStatement("select min(id) from emp;");)
		{
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void avg()
	{
		try(PreparedStatement ps=con.prepareStatement("select avg(id) from emp;");)
		{
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void count()
	{
		try(PreparedStatement ps=con.prepareStatement("select count(id) from emp;");)
		{
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sum()
	{
		try(PreparedStatement ps=con.prepareStatement("select sum(id) from emp;");)
		{
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		CurdExample c=new CurdExample();
		//c.insertStud();
		//c.selectEmp();
		//c.updateEmp();
		//c.delete();
		//c.max();
		//c.min();
		//c.count();
		//c.sum();
		c.avg();
	}

}
