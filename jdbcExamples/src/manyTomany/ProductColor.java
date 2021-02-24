package manyTomany;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductColor {
	Scanner sc = new Scanner(System.in);
	private static Connection con = null;
	private static String url = "jdbc:mysql://localhost:3306/manytomany";
	private static String username = "root";
	private static String password = "root";
	boolean flag = Boolean.FALSE;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	List<Color> color = new ArrayList<Color>();

	public void addColor() throws SQLException {
		PreparedStatement ps = con.prepareCall("insert into color(cname) values(?);");
		System.out.println("How many colors do you want?");
		int noOfcolor = sc.nextInt();
		for (int i = 0; i < noOfcolor; i++) {
			Color cr = new Color();
			System.out.println("Enter color name");
			cr.setCname(sc.next());

			ps.setString(1, cr.getCname());
			
			ps.executeUpdate();
		}
	}

	public void displayColor() throws SQLException {
		color = new ArrayList<Color>();
		ResultSet rs = con.prepareCall("select * from color;").executeQuery();
		while (rs.next()) {
			Color cr = new Color();
			cr.setCid(rs.getInt(1));
			cr.setCname(rs.getString(2));
			color.add(cr);
		}
		for (Color cr1 : color) {
			System.out.println(cr1.getCid() + "\t" + cr1.getCname());
		}
	}

	public void addProduct() throws SQLException {
		List<Product> products = new ArrayList<Product>();

		System.out.println("How many products do you want?");

		int noOfpro = sc.nextInt();
		for (int i = 0; i < noOfpro; i++) {
			Product pr = new Product();

			System.out.println("Enter product name");
			pr.setPname(sc.next());
			System.out.println("Enter price");
			pr.setPrice(sc.nextDouble());
			if (!color.isEmpty()) {
				displayColor();
				System.out.println("How many color's product do you want to buy");
				List<Color> colors = new ArrayList<Color>();
				int noOfc = sc.nextInt();
				for (int k = 0; k < noOfc; k++) {
					Color cr1 = new Color();
					System.out.println("Enter color id for allocation");
					cr1.setCid(sc.nextInt());
					colors.add(cr1);
				}
				pr.setColors(colors);
				products.add(pr);
			} else {
				System.out.println("Colors not available please add colors : ");
				addColor();
			}

		}
		System.out.println(products);

		for (Product prod : products) {
			PreparedStatement ps = con.prepareStatement("insert into product(pname,price) values(?,?);");

			ps.setString(1, prod.getPname());
			ps.setDouble(2, prod.getPrice());
			int result = ps.executeUpdate();
			if (result > 0) {
				PreparedStatement ps1 = con.prepareStatement("select max(pid) from product");
				ResultSet rs = ps1.executeQuery();
				if (rs.next()) {
					prod.setPid(rs.getInt(1));
					PreparedStatement ps2 = con
							.prepareStatement("insert into product_color(prod_id,color_id)values(?,?)");
					for (Color color : prod.getColors()) {
						ps2.setInt(1, prod.getPid());
						ps2.setInt(2, color.getCid());
						ps2.executeUpdate();
						flag = Boolean.TRUE;
					}
					if (flag)
						System.out.println("successfully inserted");
					else
						System.out.println("not inserted ");
				} else {
					System.out.println("product not avaliable");
				}
			}
		}

	}

	public void select() throws SQLException {
		List<Product> product = new ArrayList<>();
		PreparedStatement pst = con.prepareStatement("select * from product");
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			Product pr = new Product();
			pr.setPid(rs.getInt(1));
			pr.setPname(rs.getString(2));
			pr.setPrice(rs.getDouble(3));
			product.add(pr);
		}

		for (Product pp : product) {
		color = new ArrayList<>();
			PreparedStatement ps1 = con.prepareStatement("select c.cid,c.cname from product p inner join color c "
					+ "inner join product_color pc on p.pid=pc.prod_id and c.cid=pc.color_id ");
			ResultSet rs1 = ps1.executeQuery();
			while (rs1.next()) {
				Color cl = new Color();
				cl.setCid(rs1.getInt(1));
				cl.setCname(rs1.getString(2));
				color.add(cl);
			}
			pp.setColors(color);// has a
		}
		for (Product p1 : product) {
			System.out.println(p1.getPid() + "\t" + p1.getPname() + "\t" + p1.getPrice());
			for (Color c1 : p1.getColors()) {
				System.out.println(c1.getCid() + "\t" + c1.getCname());
			}
		}
	}

	public static void main(String[] args) {
		ProductColor pc = new ProductColor();

		try {
//			pc.addColor();
//			pc.displayColor();
//			pc.addProduct();
			pc.select();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}
