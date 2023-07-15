package hospital.frames;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import hospital.models.Appointment;
import hospital.models.Doctor;
import hospital.models.Patient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;
import java.util.Vector;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListedAppointmentsFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Appointment appointment;
	private DefaultTableModel dtm = null;
	private Connection c = null;
	private Statement s = null;
	private ResultSet rs = null;
	private Patient patient;
	private Vector v;
	private Doctor doctor;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListedAppointmentsFrame frame = new ListedAppointmentsFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ListedAppointmentsFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				getAppointments();
			}
		});
		setTitle("Available Appointments");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 707, 348);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 30, 671, 226);
		scrollPane.setBackground(Color.ORANGE);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setPatientAppointment();
			}
		});
		table.setBackground(SystemColor.info);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Doctor", "Province", "District", "Hospital", "Department", "Date", "State"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnRandevuAl = new JButton("Make an Appointment");
		btnRandevuAl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeAppointment();
			}
		});
		btnRandevuAl.setBounds(233, 267, 185, 23);
		contentPane.add(btnRandevuAl);
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
	
	public void getAppointments() {
		doctor = new Doctor();
		dtm = (DefaultTableModel)table.getModel();
		try {
			getConnection();
			String sql = "SELECT (Select doctor_name FROM doctors d \r\n"
					+ "        WHERE d.doctor_id = a.doctor_id),\r\n"
					+ "        province, district, hospital, department, appointment_date,\r\n"
					+ "        appointment_state\r\n"
					+ "FROM appointments a WHERE province = '"+appointment.getProvince()+"' "
							+ "AND department = '"+appointment.getDepartment()+"' AND appointment_state = '"+false+"';";
			rs = s.executeQuery(sql);
				if (rs.next()) {
				String doctor_name = rs.getString("doctor_name");
				doctor.setDoctor_name(doctor_name);
				String province = rs.getString("province");
				String district = rs.getString("district");
				String hospital = rs.getString("hospital");
				String department = rs.getString("department");
				String appointment_date = rs.getString("appointment_date");
				String appointment_state = rs.getString("appointment_state");
				dtm.addRow(new Object [] {doctor_name, province, district, hospital, department, appointment_date, appointment_state });
				}
				else {
					int info = JOptionPane.showConfirmDialog(null,"There are no appointments matching your search criteria.\r\n"
							+ "Would you like to call another appointment?" , 
							"Appointment Information", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (info == JOptionPane.YES_OPTION) {
						dispose();
						openMakeAnAppointmentFrame();
					}
					else {
						JOptionPane.showMessageDialog(null,
								"This page will be closed because no appointments were found according to your search criteria.\r\n"
								+ "You can call another appointment if you wish..");
						dispose();
						openMakeAnAppointmentFrame();
					}
				}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void openMakeAnAppointmentFrame() {
		MakeAnAppointmentFrame makeAnAppointmentFrame = new MakeAnAppointmentFrame();
		makeAnAppointmentFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		makeAnAppointmentFrame.setVisible(true);
	}

	
	public void openCameraFrame() {
		CameraFrame cameraFrame = new CameraFrame();
		cameraFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		cameraFrame.setVisible(true);
	}
	
	public void setPatientAppointment() {
		try {
			getConnection();
			dtm = (DefaultTableModel)table.getModel();
			v = dtm.getDataVector().elementAt(table.convertRowIndexToModel(table.getSelectedRow()));
				String message =  "Selected Appointment: "
						+ "\n Doctor: "+v.get(0)
						+"\n Province: " + v.get(1)
						+"\n District: "+v.get(2)
						+"\n Hospital: " + v.get(3)
						+"\n Department: " + v.get(4)
						+"\n Date: "+v.get(5)
						+"\n State: "+v.get(6);
				int choose = JOptionPane.showConfirmDialog(null, message,"Appointment Information", JOptionPane.YES_NO_OPTION);
				if (choose == JOptionPane.YES_OPTION) {
					if (patient.getPatientImportanceLevel() == 3) {
						JOptionPane.showMessageDialog(null, "You have selected level 3 as the severity level.\r\n"
								+ "You will video chat with your doctor.");
						openCameraFrame();
						//SQL bağlantısı ve metodu
					}
					else {
						JOptionPane.showMessageDialog(null, "Appointment Confirmed");
						//SQL bağlantısı ve metodu
					}
				}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void takeAppointment() {
		try {
			getConnection();
			dtm = (DefaultTableModel)table.getModel();
			v = dtm.getDataVector().elementAt(table.convertRowIndexToModel(table.getSelectedRow()));
			String sql = "INSERT INTO public.patient_appointment(appointment_id, doctor_name, "
					+ "patient_tc, province, district, hospital, department, date, state)\r\n"
					+ "	VALUES (nextval('pa_id'), '"+patient.getPatientTcNo()+"', "
							+ "'"+v.get(0)+"', '"+v.get(1)+"', '"+v.get(2)+"'"
									+ ",'"+v.get(3)+"', '"+v.get(4)+"', '"+v.get(5)+"',"+ "'"+v.get(6)+"');";
			int a = s.executeUpdate(sql);
			//updateAppointmentState(v);
			if (a>0) {
				JOptionPane.showMessageDialog(null, "Appointment Successful");
			}
			else JOptionPane.showMessageDialog(null, "Appointment Failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void updateAppointmentState(int id) {
		try {
			getConnection();
			String sql = "UPDATE public.appointments SET appointment_state='"+true+"' WHERE appointment_id = '"+id+"' ;";
			int rs = s.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	

}
