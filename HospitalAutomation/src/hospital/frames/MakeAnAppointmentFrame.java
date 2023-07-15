package hospital.frames;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import hospital.models.Appointment;
import hospital.models.Patient;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MakeAnAppointmentFrame extends JFrame {

	private JPanel contentPane;
	private Connection c = null;
	private Statement s = null;
	private ResultSet rs = null;
	private Appointment appointment;
	private Patient patient;
	private JComboBox cmbxIlce;
	private JComboBox cmbxIl;
	private JComboBox cmbxHastane;
	private JComboBox cmbxBolum;
	private int provinceCode;
	private int districtId;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MakeAnAppointmentFrame frame = new MakeAnAppointmentFrame();
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
	public MakeAnAppointmentFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				getProvinces();
				getDepartments();
			}
		});

		setTitle("Make An Appointment!\r\n");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 422, 347);
		setLocation(420,100);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIl = new JLabel("Province");
		lblIl.setBounds(10, 11, 63, 14);
		contentPane.add(lblIl);
		
		cmbxIl = new JComboBox();
		cmbxIl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getProvinces();
				provinceCode = cmbxIl.getSelectedIndex();
				//System.out.println(provinceCode);
				cmbxIlce.removeAllItems();
				//cmbxHastane.removeAllItems();
				cmbxIlce.setSelectedIndex(0);
				//cmbxHastane.setSelectedIndex(0);
			}
		});
		cmbxIl.setForeground(new Color(255, 255, 255));
		cmbxIl.setBackground(Color.LIGHT_GRAY);
		cmbxIl.setModel(new DefaultComboBoxModel(new String[] {"-"}));
		cmbxIl.setBounds(10, 26, 386, 22);
		contentPane.add(cmbxIl);
		
		JLabel lblIlce = new JLabel("District");
		lblIlce.setBounds(10, 59, 46, 14);
		contentPane.add(lblIlce);
		
		cmbxIlce = new JComboBox<Object>();
		cmbxIlce.setModel(new DefaultComboBoxModel(new String[] {"-"}));
		cmbxIlce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getDistrict(provinceCode);
				districtId = cmbxIlce.getSelectedIndex() + 1;
				System.out.println(districtId);
				cmbxHastane.setSelectedItem(null);
				cmbxHastane.removeAllItems();
			}
		}); 
		cmbxIlce.setForeground(new Color(255, 255, 255));
		cmbxIlce.setBackground(Color.LIGHT_GRAY);
		cmbxIlce.setBounds(10, 76, 386, 22);
		contentPane.add(cmbxIlce);
		
		JLabel lblHastane = new JLabel("Hospital");
		lblHastane.setBounds(10, 109, 72, 14);
		contentPane.add(lblHastane);
		
		cmbxHastane = new JComboBox();
	
		cmbxHastane.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getHospitals(districtId);				
			}
		});
	
		cmbxHastane.setForeground(new Color(255, 255, 255));
		cmbxHastane.setBackground(Color.LIGHT_GRAY);
		cmbxHastane.setModel(new DefaultComboBoxModel(new String[] {"-"}));
		cmbxHastane.setBounds(10, 123, 386, 22);
		contentPane.add(cmbxHastane);
		
		JLabel lblBolum = new JLabel("Department");
		lblBolum.setBounds(10, 156, 72, 14);
		contentPane.add(lblBolum);
		
		cmbxBolum = new JComboBox();
		cmbxBolum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getDepartments();		
			}
		});
		cmbxBolum.setForeground(new Color(255, 255, 255));
		cmbxBolum.setBackground(Color.LIGHT_GRAY);
		cmbxBolum.setModel(new DefaultComboBoxModel(new String[] {"-"}));
		cmbxBolum.setBounds(10, 172, 386, 22);
		contentPane.add(cmbxBolum);
		
		JButton btnRandevuAra = new JButton("Search For Appointment");
		btnRandevuAra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String province = (String) cmbxIl.getSelectedItem();
				String district = (String) cmbxIlce.getSelectedItem();
				String hospital = (String) cmbxHastane.getSelectedItem();
				String department = (String) cmbxBolum.getSelectedItem();				
					setDataForAppointment(province, district, hospital, department);
					openListedAppointmentsFrame();
					dispose();
			}
			
		});
		btnRandevuAra.setBounds(70, 274, 175, 23);
		contentPane.add(btnRandevuAra);
		
		JButton btnTemizle = new JButton("Clean");
		btnTemizle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbxIlce.setSelectedIndex(0);
				cmbxIl.setSelectedIndex(0);
				cmbxHastane.setSelectedIndex(0);
				cmbxBolum.setSelectedIndex(0);
			}
		});
		btnTemizle.setBounds(250, 274, 89, 23);
		contentPane.add(btnTemizle);
		
		JLabel lblOnemDerecesi = new JLabel("Importance Level");
		lblOnemDerecesi.setBounds(10, 206, 110, 14);
		contentPane.add(lblOnemDerecesi);
		
		JRadioButton rdbtn1 = new JRadioButton("1");
		rdbtn1.setBackground(SystemColor.inactiveCaption);
		rdbtn1.setBounds(126, 201, 63, 23);
		contentPane.add(rdbtn1);
		
		JRadioButton rdnbtn2 = new JRadioButton("2");
		rdnbtn2.setBackground(SystemColor.inactiveCaption);
		rdnbtn2.setBounds(191, 202, 63, 23);
		contentPane.add(rdnbtn2);
		
		JRadioButton rdbtn3 = new JRadioButton("3");
		rdbtn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				patient.setPatientImportanceLevel(3);
			}
		});
		rdbtn3.setBackground(SystemColor.inactiveCaption);
		rdbtn3.setBounds(256, 202, 72, 23);
		contentPane.add(rdbtn3);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtn1);
		group.add(rdnbtn2);
		group.add(rdbtn3);
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
	
	public void getProvinces() {
		try {
			getConnection();
			String sql = "SELECT isim FROM public.iller;";
			rs = s.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("isim");
				cmbxIl.addItem(name);				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void getDistrict(int provinceCode) {
		try {
			getConnection();
			String sql = "SELECT id,isim FROM public.ilceler where il_id = '"+provinceCode+"';";
			rs = s.executeQuery(sql);
			while (rs.next()) {
				String district = rs.getString("isim");
				if (district != null) {
					cmbxIlce.addItem(district);
				}
				else JOptionPane.showMessageDialog(null, "null döndü");
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void getHospitals(int districtId) {
		try {
			getConnection();
			String sql = "SELECT isim FROM public.hastaneler WHERE ilce_id = '"+districtId+"';";
			rs = s.executeQuery(sql);
			while (rs.next()) {
				String hospitals = rs.getString("isim");
				if (hospitals != null) {
					
					cmbxHastane.addItem(hospitals);	
				}
				else JOptionPane.showMessageDialog(null, "null döndü");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void getDepartments() {
		try {
			getConnection();
			String sql = "SELECT department_name FROM public.departments;";
			rs = s.executeQuery(sql);
			while (rs.next()) {
				String department_name = rs.getString("department_name");
		        cmbxBolum.addItem(department_name);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	} 

	public void setDataForAppointment(String province, String district, String hospital, String department) {
		appointment.setProvince(province);
		appointment.setDistrict(district);
		appointment.setHospital(hospital);
		appointment.setDepartment(department);
	}
	
	public void openListedAppointmentsFrame() {
		ListedAppointmentsFrame findAppointmentFrame = new ListedAppointmentsFrame();
		findAppointmentFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		findAppointmentFrame.setVisible(true);
		findAppointmentFrame.setBounds(100, 100, 707, 348);
	}
	
	public void openCameraFrame() {
		CameraFrame cameraFrame = new CameraFrame();
		cameraFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		cameraFrame.setVisible(true);
	}
	
}
