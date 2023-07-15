package hospital.models;

public class Doctor {

	private static String doctor_name;

	public static String getDoctor_name() {
		return doctor_name;
	}

	public static void setDoctor_name(String doctor_name) {
		Doctor.doctor_name = doctor_name;
	}
}
