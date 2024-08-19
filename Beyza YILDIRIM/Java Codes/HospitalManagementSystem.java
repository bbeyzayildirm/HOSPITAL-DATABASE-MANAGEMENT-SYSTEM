package package1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class HospitalManagementSystem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HospitalManagementSystem frame = new HospitalManagementSystem();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public HospitalManagementSystem() {
		setTitle("Hospital Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 919, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Doctor Operations");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
		        DoctorOperations doctorOperations = new DoctorOperations();
		        doctorOperations.setVisible(true);
		    }
		});
		btnNewButton.setBounds(253, 30, 184, 158);
		contentPane.add(btnNewButton);
		
		JButton btnNurseOperations = new JButton("Nurse Operations");
		btnNurseOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
		        NurseOperations nurseOperations = new NurseOperations();
		        nurseOperations.setVisible(true);
		    }
		});
		btnNurseOperations.setBounds(30, 231, 184, 158);
		contentPane.add(btnNurseOperations);
		
		JButton btnPatientOperations = new JButton("Patient Operations");
		btnPatientOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
		        PatientOperations patientOperations = new PatientOperations();
		        patientOperations.setVisible(true);
		    }
		});
		btnPatientOperations.setBounds(253, 231, 184, 158);
		contentPane.add(btnPatientOperations);
		
		JButton btnPrescriptionOperations = new JButton("Prescription Operations");
		btnPrescriptionOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
		        PrescriptionOperations prescriotionOperations = new PrescriptionOperations();
		        prescriotionOperations.setVisible(true);
		    }
		});
		btnPrescriptionOperations.setBounds(468, 231, 184, 158);
		contentPane.add(btnPrescriptionOperations);
		
		JButton btnRoomOperations = new JButton("Room Operations");
		btnRoomOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
		        RoomO roomOperations = new RoomO();
		        roomOperations.setVisible(true);
		    }
		});
		btnRoomOperations.setBounds(689, 231, 184, 158);
		contentPane.add(btnRoomOperations);
		
		JButton btnAppointmentOperations = new JButton("Appointment Operations");
		btnAppointmentOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
		        AppointmentOperations appointmentOperations = new AppointmentOperations();
		        appointmentOperations.setVisible(true);
		    }
		});
		btnAppointmentOperations.setBounds(30, 30, 185, 158);
		contentPane.add(btnAppointmentOperations);
		
		JButton btnExaminationOperations = new JButton("Examination Operations");
		btnExaminationOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
		        ExaminationOperations examinationOperations = new  ExaminationOperations();
		        examinationOperations.setVisible(true);
		    }
		});
		btnExaminationOperations.setBounds(468, 30, 185, 158);
		contentPane.add(btnExaminationOperations);
		
		JButton btnHospitalizationOperations = new JButton("Hospitalization Operations");
		btnHospitalizationOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
		        HospitalizationOperations hospitalizationOperations = new   HospitalizationOperations();
		        hospitalizationOperations.setVisible(true);
		    }
		});
		btnHospitalizationOperations.setBounds(689, 30, 185, 158);
		contentPane.add(btnHospitalizationOperations);
		
		JButton btnNewButton_1 = new JButton("EXIT");
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        System.exit(0);
		    }
		});
		btnNewButton_1.setBounds(400, 408, 100, 37);
		contentPane.add(btnNewButton_1);
	}

}
