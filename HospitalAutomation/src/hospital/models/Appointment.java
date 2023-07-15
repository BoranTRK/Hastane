package hospital.models;

public class Appointment {
	
	private static String province;
	private static String district;
	private static String hospital;
	private static String department;
	
	public static String getProvince() {
		return province;
	}
	public static void setProvince(String province) {
		Appointment.province = province;
	}
	public static String getDistrict() {
		return district;
	}
	public static void setDistrict(String district) {
		Appointment.district = district;
	}
	public static String getHospital() {
		return hospital;
	}
	public static void setHospital(String hospital) {
		Appointment.hospital = hospital;
	}
	public static String getDepartment() {
		return department;
	}
	public static void setDepartment(String department) {
		Appointment.department = department;
	}

}
