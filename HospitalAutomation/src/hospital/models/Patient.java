package hospital.models;

import java.util.Date;

public class Patient {
	
	private static int patientId;
	private static String patientTcNo;
	private static String patientPassword;
	private static String patientNameAndSurname;
	private static String patientGender;
	private static Date patientDateOfBirth;
	private static int patientAge;
	private static String patientAddress;
	private static String patientPhoneNumber;
	private static int patientImportanceLevel;

	private static boolean permission;

	public static boolean isPermission() {
		return permission;
	}

	public static void setPermission(boolean permission) {
		Patient.permission = permission;
	}

	public static int getPatientId() {
		return patientId;
	}

	public static void setPatientId(int patientId) {
		Patient.patientId = patientId;
	}

	public static String getPatientTcNo() {
		return patientTcNo;
	}

	public static void setPatientTcNo(String patientTcNo) {
		Patient.patientTcNo = patientTcNo;
	}

	public static String getPatientPassword() {
		return patientPassword;
	}

	public static void setPatientPassword(String patientPassword) {
		Patient.patientPassword = patientPassword;
	}

	public static String getPatientNameAndSurname() {
		return patientNameAndSurname;
	}

	public static void setPatientNameAndSurname(String patientNameAndSurname) {
		Patient.patientNameAndSurname = patientNameAndSurname;
	}

	public static String getPatientGender() {
		return patientGender;
	}

	public static void setPatientGender(String patientGender) {
		Patient.patientGender = patientGender;
	}

	public static Date getPatientDateOfBirth() {
		return patientDateOfBirth;
	}

	public static void setPatientDateOfBirth(Date patientDateOfBirth) {
		Patient.patientDateOfBirth = patientDateOfBirth;
	}

	public static int getPatientAge() {
		return patientAge;
	}

	public static void setPatientAge(int patientAge) {
		Patient.patientAge = patientAge;
	}

	public static String getPatientAddress() {
		return patientAddress;
	}

	public static void setPatientAddress(String patientAddress) {
		Patient.patientAddress = patientAddress;
	}

	public static String getPatientPhoneNumber() {
		return patientPhoneNumber;
	}

	public static void setPatientPhoneNumber(String patientPhoneNumber) {
		Patient.patientPhoneNumber = patientPhoneNumber;
	}

	public static int getPatientImportanceLevel() {
		return patientImportanceLevel;
	}

	public static void setPatientImportanceLevel(int patientImportanceLevel) {
		Patient.patientImportanceLevel = patientImportanceLevel;
	}

}
