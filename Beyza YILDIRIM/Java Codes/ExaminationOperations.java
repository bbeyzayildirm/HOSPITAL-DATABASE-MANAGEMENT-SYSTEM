package package1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExaminationOperations extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtExaminationId;
    private JTextField txtDoctorId;
    private JTextField txtPatientId;
    private JTextField txtDiagnosis;
    private JTable table;
    private DefaultTableModel tableModel;

    ExaminationDB examinationDB = new ExaminationDB();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ExaminationOperations frame = new ExaminationOperations();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ExaminationOperations() {
        setTitle("Examination Operations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblExaminationId = new JLabel("Examination ID");
        lblExaminationId.setBounds(10, 10, 100, 20);
        contentPane.add(lblExaminationId);

        JLabel lblDoctorId = new JLabel("Doctor ID");
        lblDoctorId.setBounds(10, 40, 100, 20);
        contentPane.add(lblDoctorId);

        JLabel lblPatientId = new JLabel("Patient ID");
        lblPatientId.setBounds(10, 70, 100, 20);
        contentPane.add(lblPatientId);

        JLabel lblDiagnosis = new JLabel("Diagnosis");
        lblDiagnosis.setBounds(10, 100, 100, 20);
        contentPane.add(lblDiagnosis);

        txtExaminationId = new JTextField();
        txtExaminationId.setBounds(120, 10, 200, 20);
        contentPane.add(txtExaminationId);
        txtExaminationId.setColumns(10);

        txtDoctorId = new JTextField();
        txtDoctorId.setBounds(120, 40, 200, 20);
        contentPane.add(txtDoctorId);
        txtDoctorId.setColumns(10);

        txtPatientId = new JTextField();
        txtPatientId.setBounds(120, 70, 200, 20);
        contentPane.add(txtPatientId);
        txtPatientId.setColumns(10);

        txtDiagnosis = new JTextField();
        txtDiagnosis.setBounds(120, 100, 200, 20);
        contentPane.add(txtDiagnosis);
        txtDiagnosis.setColumns(10);

        JButton btnSave = new JButton("SAVE");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Examination newExamination=new Examination();
                newExamination.setExaminationId(Integer.parseInt(txtExaminationId.getText()));
                newExamination.setDoctorId(Integer.parseInt(txtDoctorId.getText()));
                newExamination.setPatientId(Integer.parseInt(txtPatientId.getText()));
                newExamination.setDiagnosis(txtDiagnosis.getText());
                try {
                    examinationDB.saveExamination(newExamination);
                    clearFields();
                    loadExaminations();
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
                
                if (!txtExaminationId.getText().isEmpty()) {
                    int examinationId = Integer.parseInt(txtExaminationId.getText());
                    try {
                        examinationDB.deleteExamination(examinationId);
                        clearFields();
                        loadExaminations();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Please enter the Examination ID to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnDelete.setBounds(153, 150, 100, 20);
        contentPane.add(btnDelete);

        JButton btnUpdate = new JButton("UPDATE");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Examination updatedExamination = new Examination();
                updatedExamination.setExaminationId(Integer.parseInt(txtExaminationId.getText()));
                updatedExamination.setDoctorId(Integer.parseInt(txtDoctorId.getText()));
                updatedExamination.setPatientId(Integer.parseInt(txtPatientId.getText()));
                updatedExamination.setDiagnosis(txtDiagnosis.getText());
                try {
                    examinationDB.updateExamination(updatedExamination);
                    clearFields();
                    loadExaminations();
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
                new String[] { "Examination ID", "Doctor ID", "Patient ID", "Diagnosis" }
        );
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        loadExaminations();
    }

    private void clearFields() {
        txtExaminationId.setText("");
        txtDoctorId.setText("");
        txtPatientId.setText("");
        txtDiagnosis.setText("");
    }

    private void loadExaminations() {
        try {
            ArrayList<Examination> examinations = examinationDB.getExaminations();
            tableModel.setRowCount(0); 
            for (Examination examination : examinations) {
                tableModel.addRow(new Object[] {
                    examination.getExaminationId(),
                    examination.getDoctorId(),
                    examination.getPatientId(),
                    examination.getDiagnosis()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

