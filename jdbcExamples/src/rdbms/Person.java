package rdbms;

public class Person {

	private int pid;
	private String pname;
	private int mobile;
	private Pancard pancard;
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getMobile() {
		return mobile;
	}
	public void setMobile(int mobile) {
		this.mobile = mobile;
	}
	public Pancard getPancard() {
		return pancard;
	}
	public void setPancard(Pancard pancard) {
		this.pancard = pancard;
	}
	
	
}
