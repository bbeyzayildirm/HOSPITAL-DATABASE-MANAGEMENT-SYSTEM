package package1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentOperations extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTable table;
    private DefaultTableModel tableModel;

    AppointmentDB appointmentDB = new AppointmentDB();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AppointmentOperations frame = new AppointmentOperations();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AppointmentOperations() {
        setTitle("Appointment Operations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblAppointmentId = new JLabel("Appointment ID");
        lblAppointmentId.setBounds(10, 10, 100, 20);
        contentPane.add(lblAppointmentId);

        JLabel lblPatientId = new JLabel("Patient ID");
        lblPatientId.setBounds(10, 40, 100, 20);
        contentPane.add(lblPatientId);

        JLabel lblDoctorId = new JLabel("Doctor ID");
        lblDoctorId.setBounds(10, 70, 100, 20);
        contentPane.add(lblDoctorId);

        JLabel lblDate = new JLabel("Date");
        lblDate.setBounds(10, 100, 100, 20);
        contentPane.add(lblDate);

        textField = new JTextField();
        textField.setBounds(120, 10, 200, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(120, 40, 200, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(120, 70, 200, 20);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(120, 100, 200, 20);
        contentPane.add(textField_3);
        textField_3.setColumns(10);

        JButton btnSave = new JButton("SAVE");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Appointment newAppointment = new Appointment();
                newAppointment.setAppointmentId(Integer.parseInt(textField.getText()));
                newAppointment.setPatientId(Integer.parseInt(textField_1.getText()));
                newAppointment.setDoctorId(Integer.parseInt(textField_2.getText()));
                newAppointment.setAppointmentDate(textField_3.getText());
                try {
                    appointmentDB.saveAppointment(newAppointment);
                    clearFields();
                    loadAppointments();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnSave.setBounds(10, 150, 100, 20);
        contentPane.add(btnSave);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int appointmentId = Integer.parseInt(textField.getText());
                try {
                    appointmentDB.deleteAppointment(appointmentId);
                    clearFields();
                    loadAppointments();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnDelete.setBounds(153, 150, 100, 20);
        contentPane.add(btnDelete);

        JButton btnUpdate = new JButton("UPDATE");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Appointment updatedAppointment = new Appointment();
                updatedAppointment.setAppointmentId(Integer.parseInt(textField.getText()));
                updatedAppointment.setPatientId(Integer.parseInt(textField_1.getText()));
                updatedAppointment.setDoctorId(Integer.parseInt(textField_2.getText()));
                updatedAppointment.setAppointmentDate(textField_3.getText());
                try {
                    appointmentDB.updateAppointment(updatedAppointment);
                    clearFields();
                    loadAppointments();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnUpdate.setBounds(290, 150, 100, 20);
        contentPane.add(btnUpdate);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 200, 764, 350);
        contentPane.add(scrollPane);

        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] { "Appointment ID", "Patient ID", "Doctor ID", "Date" }
        );
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        loadAppointments();
    }

    private void clearFields() {
        textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
        textField_3.setText("");
    }

    private void loadAppointments() {
        try {
            ArrayList<Appointment> appointments = appointmentDB.getAppointments();
            tableModel.setRowCount(0); 
            for (Appointment appointment : appointments) {
                tableModel.addRow(new Object[] {
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    appointment.getDoctorId(),
                    appointment.getAppointmentDate()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
