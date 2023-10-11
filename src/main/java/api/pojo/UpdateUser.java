package api.pojo;

public class UpdateUser {
	private String accountno;
	private String departmentno;
	private String salary;
	private String pincode;
	private String id;
	private String userid;
	public UpdateUser(String accountno, String departmentno,
			String salary, String pincode,String id,String userid) {

		this.accountno = accountno;
		this.departmentno = departmentno;
		this.salary = salary;
		this.pincode = pincode;
		this.id=id;
		this.userid=userid;
	}
	public String getAccountno() {
		return accountno;
	}
	
	public String getDepartmentno() {
		return departmentno;
	}
	
	public String getSalary() {
		return salary;
	}
	
	public String getPincode() {
		return pincode;
	}
	
	public String getId() {
		return id;
	}
	
	public String getUserid() {
		return userid;
	}
	
	
	
}
