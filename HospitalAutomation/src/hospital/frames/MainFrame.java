package hospital.frames;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import hospital.models.Patient;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private Connection c = null;
	private Statement s = null;
	private ResultSet rs = null;
	private Patient patient;
	private JPanel panel;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MainFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				setPatientInfo();
			}
		});
		patient = new Patient();
		setPatientInfo();	
		setTitle("Hospital Automation Main Page");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 707, 348);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.control);
		panel.setBorder(new LineBorder(new Color(128, 0, 0)));
		panel.setBounds(20, 38, 298, 260);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblTcNo = new JLabel("T.C. Kimlik No");
		lblTcNo.setBounds(10, 11, 91, 14);
		panel.add(lblTcNo);

		JLabel lblUserNameAndSurname = new JLabel("Name and Surname");
		lblUserNameAndSurname.setBounds(10, 36, 127, 14);
		panel.add(lblUserNameAndSurname);

		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(10, 61, 46, 14);
		panel.add(lblGender);

		JLabel lblDateOfBirth = new JLabel("Date Of Birth");
		lblDateOfBirth.setBounds(10, 86, 91, 14);
		panel.add(lblDateOfBirth);

		JLabel lblPatientAddress = new JLabel("Address");
		lblPatientAddress.setBounds(10, 136, 74, 14);
		panel.add(lblPatientAddress);

		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(10, 161, 91, 14);
		panel.add(lblPhoneNumber);

		JLabel lblAge = new JLabel("Age");
		lblAge.setBounds(10, 111, 46, 14);
		panel.add(lblAge);
		
		JLabel lblTcNoText = new JLabel("");
		lblTcNoText.setBounds(132, 11, 156, 14);
		panel.add(lblTcNoText);
		
		JLabel lblNameText = new JLabel("");
		lblNameText.setBounds(132, 36, 156, 14);
		panel.add(lblNameText);
		
		JLabel lblGenderText = new JLabel("");
		lblGenderText.setBounds(132, 61, 156, 14);
		panel.add(lblGenderText);
		
		JLabel lblDateText = new JLabel("");
		lblDateText.setBounds(132, 86, 156, 14);
		panel.add(lblDateText);
		
		JLabel lblAgeText = new JLabel("");
		lblAgeText.setBounds(132, 111, 156, 14);
		panel.add(lblAgeText);
		
		JLabel lblAddressText = new JLabel("");
		lblAddressText.setBounds(132, 136, 156, 14);
		panel.add(lblAddressText);
		
		JLabel lblPhoneText = new JLabel("");
		lblPhoneText.setBounds(132, 161, 156, 14);
		panel.add(lblPhoneText);

		JLabel lblPatientInformation = new JLabel("Patient Information");
		lblPatientInformation.setBounds(109, 13, 126, 14);
		contentPane.add(lblPatientInformation);
		Image imgMakeAnAppointment = new ImageIcon(this.getClass().getResource("/makeAppointment.png")).getImage();

		JButton btnShowBookedAppointments = new JButton("Show Booked Appointments");
		btnShowBookedAppointments.setBackground(SystemColor.control);
		btnShowBookedAppointments.setHorizontalAlignment(SwingConstants.LEFT);
		Image imgShowBookedAppointments = new ImageIcon(this.getClass().getResource("/booked.png")).getImage();
		btnShowBookedAppointments.setIcon(new ImageIcon(imgShowBookedAppointments));
		btnShowBookedAppointments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowBookedAppointmentFrame a = new ShowBookedAppointmentFrame();
				a.setVisible(true);
			}
		});
		btnShowBookedAppointments.setBounds(328, 129, 353, 80);
		contentPane.add(btnShowBookedAppointments);
		
		lblTcNoText.setText(patient.getPatientTcNo());
		lblNameText.setText(patient.getPatientNameAndSurname());
		lblGenderText.setText(patient.getPatientGender());
		lblDateText.setText(String.valueOf(patient.getPatientDateOfBirth()));
		lblAgeText.setText(String.valueOf(patient.getPatientAge()).toString());
		lblAddressText.setText(patient.getPatientAddress());
		lblPhoneText.setText(patient.getPatientPhoneNumber());
		
		JLabel lblSettings = new JLabel("");
		lblSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SettingsFrame settingsFrame = new SettingsFrame();
				settingsFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				settingsFrame.setVisible(true);
				dispose();
			}
		});
		lblSettings.setBounds(10, 186, 114, 62);
		Image img = new ImageIcon(this.getClass().getResource("/settings.png")).getImage();
		lblSettings.setIcon(new ImageIcon(img));
		panel.add(lblSettings);
		
		JLabel lblSignOut = new JLabel("");
		lblSignOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int approve = JOptionPane.showConfirmDialog(null,"After you approve, you will log out of the system, do you confirm?" 
						,"Do you want to sign out?", JOptionPane.YES_NO_CANCEL_OPTION);
				if (approve == JOptionPane.YES_OPTION) {
					dispose();
					openLogInFrame();
				}
				
			
			}
		});
		lblSignOut.setBounds(226, 182, 104, 66);
		Image imgSignOut = new ImageIcon(this.getClass().getResource("/signout.png")).getImage();
		lblSignOut.setIcon(new ImageIcon(imgSignOut));
		panel.add(lblSignOut);
		
		JButton btnMakeAppointment = new JButton("Make An Appointment");
		btnMakeAppointment.setHorizontalAlignment(SwingConstants.LEFT);
		btnMakeAppointment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MakeAnAppointmentFrame mf = new MakeAnAppointmentFrame();
				mf.setVisible(true);
			}
		});
		btnMakeAppointment.setBackground(SystemColor.control);
		Image img2 = new ImageIcon(this.getClass().getResource("/makeAppointment.png")).getImage();
		btnMakeAppointment.setIcon(new ImageIcon(img2));
		btnMakeAppointment.setBounds(328, 38, 353, 78);
		contentPane.add(btnMakeAppointment);
		
	}
	
	public void getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HospitalAutomation", "postgres", "12345");
			s = c.createStatement();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void setPatientInfo() {		
		try {
			getConnection();
			String sql = "SELECT patient_id, patient_tcno, patient_password, patient_name_surname, patient_gender, "
					+ "patient_date_of_birth,(SELECT \r\n"
					+ "EXTRACT(YEAR FROM CURRENT_DATE) - \r\n"
					+ "EXTRACT(YEAR FROM patient_date_of_birth) AS patient_age), \r\n"
					+ "patient_address, patient_phone_number\r\n"
					+ "	FROM public.patients WHERE patient_tcno = '"+patient.getPatientTcNo()+"'";
			rs = s.executeQuery(sql);
			while (rs.next()) {
				String patient_name_surname = rs.getString("patient_name_surname");
				String patient_password = rs.getString("patient_password");
				String patient_gender = rs.getString("patient_gender");
				Date patient_date_of_birth = rs.getDate("patient_date_of_birth");
				int patient_age = rs.getInt("patient_age");
				String patient_address = rs.getString("patient_address");
				String patient_phone_number = rs.getString("patient_phone_number");
				
				patient.setPatientNameAndSurname(patient_name_surname);
				patient.setPatientPassword(patient_password);
				patient.setPatientGender(patient_gender);
				patient.setPatientDateOfBirth(patient_date_of_birth);
				patient.setPatientAge(patient_age);
				patient.setPatientAddress(patient_address);
				patient.setPatientPhoneNumber(patient_phone_number);
				
				}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void openLogInFrame() {
		LogInFrame logInFrame = new LogInFrame();
		logInFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		logInFrame.setVisible(true);
	}
}
