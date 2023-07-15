package hospital.frames;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import hospital.models.Doctor;
import hospital.models.Patient;
import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;


public class CameraFrame extends JFrame {

	private JPanel contentPane;
	private Patient patient;
	private CanvasFrame canvasFrame;
	private JTextField txtMessage;
	private JPanel patientPanel;
	private Webcam webcam;
	private WebcamPanel webcamPanel;
	private JButton btnCamera;
	private Doctor doctor;
	private static VideoCapture camera;
    private static boolean isCameraOpen = false;
    private JTextArea textArea;
    

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CameraFrame frame = new CameraFrame();
					frame.setVisible(true);
				}
					 catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public CameraFrame() {

		setTitle("Live interview of '"+patient.getPatientNameAndSurname()+"' and '"+doctor.getDoctor_name()+"'");	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 490);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHastaAdi = new JLabel("Hasta Adı");
		lblHastaAdi.setText(patient.getPatientNameAndSurname());
		lblHastaAdi.setBounds(65, 26, 104, 14);
		contentPane.add(lblHastaAdi);
		
		JLabel lblDoktorAdi = new JLabel();
		lblDoktorAdi.setBounds(416, 26, 118, 14);
		lblDoktorAdi.setText(doctor.getDoctor_name());
		contentPane.add(lblDoktorAdi);
		
		JButton btnBitir = new JButton("End the Call");
		btnBitir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int info = JOptionPane.showConfirmDialog(null,"Are You Sure You Want to End the Call?","User Consent",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (info == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
		btnBitir.setBounds(205, 417, 142, 23);
		contentPane.add(btnBitir);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		desktopPane.setBounds(20, 224, 280, -165);
		contentPane.add(desktopPane);
		
		patientPanel = new JPanel();
		patientPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		patientPanel.setBounds(10, 51, 188, 156);
		contentPane.add(patientPanel);
				
		JPanel doctorPanel = new JPanel();
		doctorPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		doctorPanel.setBounds(370, 51, 188, 156);
		contentPane.add(doctorPanel);
		
		JLabel lblDoctor = new JLabel("");
		lblDoctor.setBounds(61, 11, 157, 134);
		Image img = new ImageIcon(this.getClass().getResource("/doctor.png")).getImage();
		doctorPanel.setLayout(null);
		lblDoctor.setIcon(new ImageIcon(img));
		doctorPanel.add(lblDoctor);
		
		JPanel textPanel = new JPanel();
		textPanel.setBounds(10, 264, 548, 142);
		contentPane.add(textPanel);
		textPanel.setLayout(null);
		
		
		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					getMessageFromTextBox();
                }
			}
		});
		txtMessage.setBounds(0, 111, 449, 31);
		textPanel.add(txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnSendMessage = new JButton("");
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getMessageFromTextBox();
			}
		});
		btnSendMessage.setBackground(SystemColor.control);
		Image sendImg = new ImageIcon(this.getClass().getResource("/sent.png")).getImage();
		btnSendMessage.setIcon(new ImageIcon(sendImg));
		btnSendMessage.setBounds(445, 111, 103, 31);
		textPanel.add(btnSendMessage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 548, 112);
		textPanel.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setBackground(SystemColor.control);
		scrollPane.setViewportView(textArea);
		
		btnCamera = new JButton("");
		btnCamera.setBackground(SystemColor.control);
		Image cameraImg = new ImageIcon(this.getClass().getResource("/camClose.png")).getImage();
		btnCamera.setIcon(new ImageIcon(cameraImg));
		btnCamera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (webcam != null && webcam.isOpen()) {
                    webcam.close();
                    Image imgPatientCamClose = new ImageIcon(this.getClass().getResource("/camClose.png")).getImage();
            		btnCamera.setIcon(new ImageIcon(imgPatientCamClose));
            		
                } else {
                	webcam = Webcam.getDefault();
                	webcam.open();
                	Image imgPatientCamOpen = new ImageIcon(this.getClass().getResource("/camOpen.png")).getImage();
            		btnCamera.setIcon(new ImageIcon(imgPatientCamOpen));
                    startCamera();
                }
				
			}
		});
		btnCamera.setBounds(78, 207, 55, 46);
		contentPane.add(btnCamera);
		
		JButton btnDoctorCam = new JButton("");
		btnDoctorCam.setBackground(SystemColor.control);
		Image imgDoctorCam = new ImageIcon(this.getClass().getResource("/camOpen.png")).getImage();
		btnDoctorCam.setIcon(new ImageIcon(imgDoctorCam));
		btnDoctorCam.setBounds(440, 207, 55, 46);
		contentPane.add(btnDoctorCam);
		
		
	}
	public void getMessageFromTextBox() {
		 String inputText = txtMessage.getText();
		 textArea.append(patient.getPatientNameAndSurname()+": " +inputText + "\n");
         txtMessage.setText("");
	}
	
	private void startCamera() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (webcam.isOpen()) {
                	Image image = webcam.getImage();
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(patientPanel.getWidth(), patientPanel.getHeight(), Image.SCALE_DEFAULT));
                    patientPanel.removeAll();
                    patientPanel.add(new JLabel(icon));
                    patientPanel.revalidate();
                    patientPanel.repaint();
                }
            }
        }).start();
}
}

