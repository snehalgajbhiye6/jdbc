package rdbms;

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
	boolean flag = Boolean.FALSE;
	private static Connection con = null;
	private static String url = "jdbc:mysql://localhost:3306/rdbms";
	private static String userName = "root";
	private static String password = "root";
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void insert() {
		try (PreparedStatement ps = con.prepareStatement("insert into person(pname,mobile) values(?,?);");) {
			System.out.println("How many employee do you want to add?");
			int i = sc.nextInt();
			for (int j = 0; j < i; j++) {
				Person p = new Person();
				System.out.println("Enter name");
				p.setPname(sc.next());
				System.out.println("Enter mobile number");
				p.setMobile(sc.nextInt());

				ps.setString(1, p.getPname());
				ps.setInt(2, p.getMobile());

				int result = ps.executeUpdate();
				if (result > 0) {
					ResultSet rs = ps.executeQuery("select max(pid) from person;");
					if (rs.next())
						p.setPid(rs.getInt(1));

						try (PreparedStatement ps1 = con.prepareStatement("insert into pancard(panId,panNumber) values(?,?);");) {
							System.out.println("How many pancards do you want?");
							int k=sc.nextInt();
							for(int n=0;n<k;n++)
							{
								Pancard pan=new Pancard();
								System.out.println("Enter pancard number");
								pan.setPanNumber(sc.next());
								p.setPancard(pan);
								
								ps1.setInt(1, p.getPid());
								ps1.setString(2, p.getPancard().getPanNumber());
								ps1.executeUpdate();
								flag = Boolean.TRUE;
							}	
						} 
					   catch (Exception e) {
							System.out.println(e);
						}
					
				}
				if (flag == Boolean.TRUE) {
					System.out.println("Successfully inserted");
				} else {
					System.out.println("Interupted");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//-------------------------------------------------------------------------------------------------------------------
	public void select()
	{
		List<Person> list=new ArrayList<Person>();
		try(PreparedStatement ps=con.prepareStatement("select * from person p inner join pancard pan on p.pid=pan.panid");)
		{
			ResultSet rs=ps.executeQuery();
			while (rs.next())
			{
				Person p=new Person();
				p.setPid(rs.getInt(1));
				p.setPname(rs.getString(2));
				p.setMobile(rs.getInt(3));
				
				Pancard pan=new Pancard();
				pan.setPanId(rs.getInt(4));
				pan.setPanNumber(rs.getString(5));
				p.setPancard(pan);
				list.add(p);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(Person p:list)
		{
			System.out.println(p.getPid()+"\t"+p.getPname()+"\t"+p.getMobile()+"\t"+p.getPancard().getPanId()+"\t"+p.getPancard().getPanNumber());
		}
	}
	public static void main(String[] args) {

		Test t = new Test();
		//t.insert();
		t.select();
	}

}
