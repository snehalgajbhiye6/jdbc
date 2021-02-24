package statmentsExamples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class CurdExample {
	private static Connection con=null;
	private static String url="jdbc:mysql://localhost:3306/user";
	private static String username="root";
	private static String password="root";
	private boolean flag=Boolean.FALSE;
	
	Scanner sc=new Scanner(System.in);
	static {
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,username,password);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	
    public void insertEmp() throws SQLException
    {
    	Statement stmt=con.createStatement();
    	System.out.println("How many employees do you want to add");
    	int i=sc.nextInt();
    	for(int j=0;j<i;j++)
    	{
    		Emp emp=new Emp();
    		System.out.println("Enter name");
    		emp.setName(sc.next());
    		
    		System.out.println("Enter mobile number");
    		emp.setMobile(sc.nextInt());
    	    stmt.executeUpdate("insert into emp(name,mobile)values('"+ emp.getName()+
    	    		"','"+emp.getMobile()+"')");
    	    flag=Boolean.TRUE;
    	}
    	if(flag==Boolean.TRUE)
    	{
    		System.out.println("insert succesfully");
    	}
    	else
    	{
    		System.out.println("Interupted");
    	}
    	
    }
    
   public void selectEmp()
    {
    	List<Emp> list=new ArrayList<Emp>();
        try(Statement stmt=con.createStatement();)
        {
        	ResultSet rs=stmt.executeQuery("select * from emp;");
        	while(rs.next())
        	{
        		Emp emp=new Emp();
        		emp.setId(rs.getInt(1));
        		emp.setName(rs.getString(2));
        		emp.setMobile(rs.getInt(3));
        		list.add(emp);
        	}
        } catch (SQLException e) {
			e.printStackTrace();
		}
        for(Emp emp:list)
        {
        	System.out.println(emp.getId()+"\t"+emp.getName()+"\t"+emp.getMobile());
        }
    }
   
   public void maxEmp()
   {
	   try(Statement stmt=con.createStatement();)
	   {
		   ResultSet rs=stmt.executeQuery("select max(id) from emp;");
		   if(rs.next())
		   {
			   System.out.println(rs.getInt(1));
		   }
	   } catch (SQLException e) {
		e.printStackTrace();
	}
   }
    
   public void minEmp() throws SQLException
   {
	   try(Statement stmt=con.createStatement();)
	   {
		   ResultSet rs=stmt.executeQuery("select min(id) from emp;");
		   if(rs.next())
		   {
			   System.out.println(rs.getInt(1));
		   }
	   }
   }
    public void updateEmp()
    {
    	try(Statement stmt=con.createStatement())
    	{
    		System.out.println("which row do you want to update");
    		int id=sc.nextInt();
    		
    		System.out.println("Enter name");
    		String name=sc.next();
    				
    		int result=stmt.executeUpdate("update emp set name='"+name+"' "
    				+ "where id="+id+"");
    		
    		if(result>0)
    		{
    			System.out.println("Update successfully");
    		}
    		
    		else
    		{
    			System.out.println("Interupted");
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    public void deleteEmp()
    {
    	try(Statement stmt=con.createStatement())
    	{
    		System.out.println("Which row do you want to delete");
    		int id=sc.nextInt();
    		
    		int result=stmt.executeUpdate("delete from emp where id="+id+"");
    		if(result>0)
    		{
    			System.out.println("Updated succesfully");
    		}
    		
    		else
    		{
    			System.out.println("Interuppted");
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void avgEmp()
    {
    	try(Statement stmt=con.createStatement();)
    	{
    		ResultSet rs=stmt.executeQuery("select avg(id) from emp;");
    		if(rs.next())
    		{
    			System.out.println(rs.getInt(1));
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void countEmp() {
       try(Statement stmt=con.createStatement();)
       {
    	   ResultSet rs=stmt.executeQuery("select count(id) from emp");
    	   if(rs.next())
    	   {
    		   System.out.println(rs.getInt(1));
    	   }
       } catch (SQLException e) {
		e.printStackTrace();
	}
    }
    
    public void sumEmp()
    {
    	try(Statement stmt=con.createStatement();)
    	{
    		ResultSet rs=stmt.executeQuery("select sum(id) from emp;");
    		if(rs.next())
    		{
    			System.out.println(rs.getInt(1));
    		}
    		
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	public static void main(String[] args) throws SQLException {
		CurdExample ce=new CurdExample();
		//ce.insertEmp();
		//ce.selectEmp();
		//ce.updateEmp();
		//ce.deleteEmp();
		//ce.maxEmp();
		//ce.minEmp();
		//ce.avgEmp();
		//ce.countEmp();
		ce.sumEmp();
	}

}
