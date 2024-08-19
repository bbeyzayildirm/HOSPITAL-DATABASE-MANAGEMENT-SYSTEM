package package1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;


public class PrescriptionOperations extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtPrescriptionId;
    private JTextField txtPatientId;
    private JTextField txtDoctorId;
    private DefaultTableModel tableModel;
    private JTable table;

    PrescriptionDB prescriptionDB = new PrescriptionDB();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PrescriptionOperations frame = new PrescriptionOperations();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PrescriptionOperations() {
        setTitle("Prescription Operations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 150, 550, 200);
        contentPane.add(scrollPane);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Prescription ID");
        tableModel.addColumn("Patient ID");
        tableModel.addColumn("Doctor ID");
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        JLabel lblPrescriptionId = new JLabel("Prescription ID:");
        lblPrescriptionId.setBounds(20, 20, 100, 20);
        contentPane.add(lblPrescriptionId);

        txtPrescriptionId = new JTextField();
        txtPrescriptionId.setBounds(130, 20, 200, 20);
        contentPane.add(txtPrescriptionId);
        txtPrescriptionId.setColumns(10);

        JLabel lblPatientId = new JLabel("Patient ID:");
        lblPatientId.setBounds(20, 50, 100, 20);
        contentPane.add(lblPatientId);

        txtPatientId = new JTextField();
        txtPatientId.setBounds(130, 50, 200, 20);
        contentPane.add(txtPatientId);
        txtPatientId.setColumns(10);

        JLabel lblDoctorId = new JLabel("Doctor ID:");
        lblDoctorId.setBounds(20, 80, 100, 20);
        contentPane.add(lblDoctorId);

        txtDoctorId = new JTextField();
        txtDoctorId.setBounds(130, 80, 200, 20);
        contentPane.add(txtDoctorId);
        txtDoctorId.setColumns(10);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionId(Integer.parseInt(txtPrescriptionId.getText()));
                prescription.setPatientId(Integer.parseInt(txtPatientId.getText()));
                prescription.setDoctorId(Integer.parseInt(txtDoctorId.getText()));
                try {
                    prescriptionDB.savePrescription(prescription);
                    refreshTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnSave.setBounds(20, 120, 100, 20);
        contentPane.add(btnSave);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int prescriptionId = Integer.parseInt(txtPrescriptionId.getText());
                    prescriptionDB.deletePrescription(prescriptionId);
                    refreshTable();
                } catch (NumberFormatException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Please fill in the Prescription ID field with a valid value.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnDelete.setBounds(130, 120, 100, 20);
        contentPane.add(btnDelete);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionId(Integer.parseInt(txtPrescriptionId.getText()));
                prescription.setPatientId(Integer.parseInt(txtPatientId.getText()));
                prescription.setDoctorId(Integer.parseInt(txtDoctorId.getText()));
                try {
                    prescriptionDB.updatePrescription(prescription);
                    refreshTable(); 
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnUpdate.setBounds(240, 120, 100, 20);
        contentPane.add(btnUpdate);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            ArrayList<Prescription> prescriptions = prescriptionDB.getPrescriptions();
            for (Prescription prescription : prescriptions) {
                Object[] row = {prescription.getPrescriptionId(), prescription.getPatientId(), prescription.getDoctorId()};
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
