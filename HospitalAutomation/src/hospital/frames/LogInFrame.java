package hospital.frames;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import hospital.models.Patient;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JPasswordField;

public class LogInFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtTC;
	private Connection c = null;
	private Statement s = null;
	private ResultSet rs = null;
	private Patient patient;
	private JPasswordField txtPassword;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInFrame frame = new LogInFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public LogInFrame() {
		setTitle("Welcome to the B-Hospital Systems!\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 707, 348);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.control);
		panel.setBorder(new LineBorder(new Color(128, 0, 0), 1, true));
		panel.setBounds(10, 28, 355, 249);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblUserTcNo = new JLabel("TC Identification Number");
		lblUserTcNo.setForeground(new Color(0, 0, 0));
		lblUserTcNo.setBounds(10, 32, 140, 14);
		panel.add(lblUserTcNo);
		
		txtTC = new JTextField();
		txtTC.setBackground(SystemColor.window);
		txtTC.setBounds(10, 57, 140, 20);
		panel.add(txtTC);
		txtTC.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(0, 0, 0));
		lblPassword.setBounds(10, 94, 86, 14);
		panel.add(lblPassword);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setBackground(new Color(192, 192, 192));
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logIn();
			}
		});
		btnLogIn.setBounds(35, 146, 89, 23);
		panel.add(btnLogIn);
		
		JLabel lblSignUpNow = new JLabel("Don't you have an account? \r\nSign Up Now!");
		lblSignUpNow.setBounds(10, 187, 269, 25);
		panel.add(lblSignUpNow);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(10, 110, 140, 20);
		panel.add(txtPassword);
		
		JLabel lblUserInformation = new JLabel("User Information");
		lblUserInformation.setBounds(10, 11, 102, 14);
		contentPane.add(lblUserInformation);
		
		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/hospital.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(407, 0, 274, 299);
		contentPane.add(lblNewLabel);
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
	
	public void logIn() {
		//user = new User();
		String tcno = txtTC.getText();
		patient.setPatientTcNo(tcno);
		//user.setUser_email(email);
		String password = txtPassword.getText();
		try {
			getConnection();
			String sql = "SELECT patient_tcno, patient_password\r\n"
					+ "	FROM public.patients WHERE patient_tcno = '"+tcno+"' AND patient_password = '"+password+"'";
			rs = s.executeQuery(sql);
			if (rs.next()) {
				MainFrame mainContactFrame = new MainFrame();
				mainContactFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				mainContactFrame.setVisible(true);
				dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "T.C. No or password did not match! \nPlease try again!"
						,"Patient Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
