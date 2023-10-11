package api.pojo;


	public class AddUser {
//		@JsonNaming(PropertyNamingStrategies.LOWER_CASE)
		private String accountno;
		private String departmentno;
		private String salary;
		private String pincode;
		
		public AddUser(String accountno, String departmentno, String salary, String pincode) {

			this.accountno = accountno;
			this.departmentno = departmentno;
			this.salary = salary;
			this.pincode = pincode;
		}
		
		public String getAccountNo() {
			return accountno;
		}
		
		public String getSalary() {
			return salary;
		}
		
		public String getPinCode() {
			return pincode;
		}
		
		public String getDepartmentNo() {
			return departmentno;
		}
		
		

	}


