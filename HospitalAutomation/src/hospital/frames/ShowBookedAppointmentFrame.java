package hospital.frames;

import javax.swing.*;
import javax.swing.border.Border;

import hospital.models.Patient;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class ShowBookedAppointmentFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private Patient patient;
    private Connection c = null;
	private Statement s = null;
	private ResultSet rs = null;
	private JPanel panel;

    public ShowBookedAppointmentFrame() {
        setTitle(patient.getPatientNameAndSurname() + "'s Appointments");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 707, 350);

        tabbedPane = new JTabbedPane();

        try {
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HospitalAutomation?useSSL=false", "postgres", "12345");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT appointment_id,patient_tc as Doktor, province as İl, district as İlçe, "
                    + "hospital as Hastane, department as Bölüm, date as Tarih, state as Durum"
                    + " FROM public.patient_appointment;");

            JPanel randevularimPanel = new JPanel(new GridLayout(0, 1));
            randevularimPanel.setSize(500, 550);
            
            JPanel gecmisRandevularimPanel = new JPanel();
            gecmisRandevularimPanel.setSize(500, 550);

            while (resultSet.next()) {
                panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                panel.setSize(700, 700);

                StringBuilder content = new StringBuilder();

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i).toUpperCase();
                    String value = resultSet.getString(i).toUpperCase();
                    content.append(columnName).append(": ").append(value);

                    // Son sütuna gelinmediyse ayırıcı ve alt satıra geçme işareti ekle
                    if (i < columnCount) {
                        content.append("<br>");
                    }
                }

                JLabel label = new JLabel("<html>" + content.toString() + "</html>");
                panel.add(label);

                // Line border oluşturma ve panelin etrafına ekleme
                Border border = BorderFactory.createLineBorder(Color.BLACK);
                panel.setBorder(border);
                
                Date databaseDate = resultSet.getDate("Tarih"); // Burada veritabanından tarih almanız gerekmektedir

                // Şu anki zamanın alınması
                LocalDate currentDate = LocalDate.now();

                // Veritabanındaki tarihi LocalDate türüne dönüştürme
                LocalDate convertedDatabaseDate = databaseDate.toLocalDate();

                // Karşılaştırma
                if (convertedDatabaseDate.isBefore(currentDate)) {
                	panel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                           JOptionPane.showMessageDialog(null,"This is an outdated appointment.");
                        }
                    });
                    gecmisRandevularimPanel.add(panel);
                } else {
                	panel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                        	String id = String.valueOf(content.charAt(16));
                        	int info = JOptionPane.showConfirmDialog(null,"Are you sure you want to cancel your appointment?","User Consent",
        							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        				if (info == JOptionPane.YES_OPTION) {
        					cancelAppointment(id);
        					panel.remove(label);
        					panel.revalidate();
        					panel.repaint();
        				}
                        }
                    });
                    randevularimPanel.add(panel);
                    
                }               
            }

            // Panelin güncellenmesi
            randevularimPanel.revalidate();
            randevularimPanel.repaint();
            gecmisRandevularimPanel.revalidate();
            gecmisRandevularimPanel.repaint();

           resultSet.close();
            statement.close();
            connection.close();

            // "Geçmiş Randevularım" paneli oluşturma

            // Panelleri JTabbedPane'e ekleme
            tabbedPane.addTab("Appointments", randevularimPanel);
            tabbedPane.addTab("Past Appointments", gecmisRandevularimPanel);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShowBookedAppointmentFrame example = new ShowBookedAppointmentFrame();
            example.setVisible(true);
        });
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
    
    public void cancelAppointment(String id) {
    	try {
			getConnection();
			String sql = "DELETE FROM public.patient_appointment WHERE appointment_id = '"+id+"'";
			boolean rs = s.execute(sql);
			if (rs) {
				JOptionPane.showMessageDialog(null, "The appointment could not be deleted!");
			}
			else JOptionPane.showMessageDialog(null, "Appointment successfully deleted!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
    }
}
