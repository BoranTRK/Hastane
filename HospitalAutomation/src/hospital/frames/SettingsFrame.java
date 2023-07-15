package hospital.frames;

import java.awt.EventQueue;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import hospital.models.Patient;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JInternalFrame;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;

public class SettingsFrame extends JFrame {

	private JPanel contentPane;
	private Connection c = null;
	private Statement s = null;
	private ResultSet rs = null;
	private Patient patient;
	private JTextField txtNameSurname;
	private JTextField txtPhoneNumber;
	private JTextField txtTcNo;
	private JTextField txtPassword;
	private JTextField txtAddress;
	private JDateChooser dateChooser;
	private JTextField txtAge;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsFrame frame = new SettingsFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public SettingsFrame() {
		
		setTitle("Settings\r\n");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Boran\\Desktop\\settings.png"));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 707, 348);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.control);
		panel.setBounds(10, 11, 671, 287);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblPatientNameSurname = new JLabel("Name and Surname");
		lblPatientNameSurname.setBounds(10, 49, 130, 14);
		panel.add(lblPatientNameSurname);
		
		txtNameSurname = new JTextField();
		txtNameSurname.setBounds(130, 46, 114, 20);
		panel.add(txtNameSurname);
		txtNameSurname.setColumns(10);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(10, 93, 46, 14);
		panel.add(lblGender);
		
		JComboBox genderComboBox = new JComboBox();
		genderComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Erkek", "Kadın"}));
		genderComboBox.setBounds(130, 89, 114, 22);
		genderComboBox.setSelectedItem(patient.getPatientGender());
		panel.add(genderComboBox);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(10, 209, 58, 14);
		panel.add(lblAddress);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(10, 170, 86, 14);
		panel.add(lblPhoneNumber);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(130, 167, 114, 20);
		panel.add(txtPhoneNumber);
		txtPhoneNumber.setColumns(10);
		
		JLabel lblTc = new JLabel("T.C. Kimlik No");
		lblTc.setBounds(10, 11, 86, 14);
		panel.add(lblTc);
		
		txtTcNo = new JTextField();
		txtTcNo.setEnabled(false);
		txtTcNo.setBounds(130, 8, 114, 20);
		panel.add(txtTcNo);
		txtTcNo.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(272, 8, 67, 14);
		panel.add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(350, 8, 114, 20);
		panel.add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblUpdate = new JLabel("");
		lblUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updatePatientInformation();
			}
		});
		lblUpdate.setBounds(553, 185, 108, 91);
		Image img = new ImageIcon(this.getClass().getResource("/update.png")).getImage();
		lblUpdate.setIcon(new ImageIcon(img));
		panel.add(lblUpdate);
		
		JLabel lblDelete = new JLabel("");
		lblDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Deleted");
			}
		});
		lblDelete.setBounds(441, 185, 102, 91);
		Image imgDelete = new ImageIcon(this.getClass().getResource("/delete.png")).getImage();
		lblDelete.setIcon(new ImageIcon(imgDelete));
		panel.add(lblDelete);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(10, 234, 234, 42);
		panel.add(txtAddress);
		txtAddress.setColumns(10);
		
		JLabel lblDateOfBirth = new JLabel("Date Of Birth");
		lblDateOfBirth.setBounds(272, 49, 72, 14);
		panel.add(lblDateOfBirth);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(350, 49, 114, 20);
		panel.add(dateChooser);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setBounds(10, 129, 46, 14);
		panel.add(lblAge);
		
		txtAge = new JTextField();
		txtAge.setForeground(Color.BLACK);
		txtAge.setBackground(new Color(255, 255, 255));
		txtAge.setEnabled(false);
		txtAge.setBounds(130, 126, 114, 20);
		panel.add(txtAge);
		txtAge.setColumns(10);
		
		JLabel lblBack = new JLabel("");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				openMainFrame();
			}
		});
		Image imgBack = new ImageIcon(this.getClass().getResource("/back.png")).getImage();
		lblBack.setIcon(new ImageIcon(imgBack));
		lblBack.setBounds(553, 11, 108, 96);
		panel.add(lblBack);
		
		setPatientInformationToAreas();
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
	
	public void setPatientInformationToAreas() {
		try {
			getConnection();
			txtTcNo.setText(patient.getPatientTcNo());
			txtPhoneNumber.setText(patient.getPatientPhoneNumber());
			txtAddress.setText(patient.getPatientAddress());
			txtNameSurname.setText(patient.getPatientNameAndSurname());
			txtPassword.setText(patient.getPatientPassword());	
			dateChooser.setDate(patient.getPatientDateOfBirth());
			txtAge.setText(String.valueOf(patient.getPatientAge()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	//+"patient_date_of_birth = '"+formattedDate+"',"
	public void updatePatientInformation() {
		try {
			getConnection();
			LocalDate selectedDate = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = selectedDate.format(formatter);
			String sql = "UPDATE public.patients SET patient_password= '"+txtPassword.getText()+"',"
					+ "patient_name_surname='"+txtNameSurname.getText()+"', "+"patient_date_of_birth = '"+formattedDate+"',"
					+ "patient_age = '"+patient.getPatientAge()+"',"
					+ "patient_address='"+txtAddress.getText()+"', patient_phone_number= '"+txtPhoneNumber.getText()+"'"
					+ "	WHERE patient_tcno = '"+patient.getPatientTcNo()+"'";
			patient.setPatientPassword(txtPassword.getText());
			patient.setPatientNameAndSurname(txtNameSurname.getText());
			patient.setPatientAddress(txtAddress.getText());
			patient.setPatientPhoneNumber(txtPhoneNumber.getText());
			patient.setPatientDateOfBirth(dateChooser.getDate());
			//patient.setPatientDateOfBirth(String.valueOf(dateChooser.getDate()));
			int a = s.executeUpdate(sql);
			if (a == 1) {
				JOptionPane.showMessageDialog(null, "Patient's Data Updated");
			}
			else JOptionPane.showMessageDialog(null, "An error occurred while updating user information.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void openMainFrame() {
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
	}
}
