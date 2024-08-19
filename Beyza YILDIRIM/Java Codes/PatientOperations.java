package package1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class PatientOperations extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldId;
    private JTextField textFieldName;
    private JTextField textFieldSurname;
    private JTextField textFieldPhoneNo;
    private JComboBox<String> comboBoxDistrict;
    private JTable table;
    private DefaultTableModel tableModel;

    private PatientDB patientDB = new PatientDB();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PatientOperations frame = new PatientOperations();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PatientOperations() {
        setTitle("Patient Operations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblId = new JLabel("ID");
        lblId.setBounds(10, 10, 81, 20);
        contentPane.add(lblId);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(10, 34, 81, 20);
        contentPane.add(lblName);

        JLabel lblSurname = new JLabel("Surname");
        lblSurname.setBounds(10, 60, 81, 20);
        contentPane.add(lblSurname);

        JLabel lblPhoneNo = new JLabel("Phone No");
        lblPhoneNo.setBounds(10, 84, 81, 20);
        contentPane.add(lblPhoneNo);

        textFieldId = new JTextField();
        textFieldId.setBounds(101, 11, 162, 19);
        contentPane.add(textFieldId);
        textFieldId.setColumns(10);

        textFieldName = new JTextField();
        textFieldName.setColumns(10);
        textFieldName.setBounds(101, 35, 162, 19);
        contentPane.add(textFieldName);

        textFieldSurname = new JTextField();
        textFieldSurname.setColumns(10);
        textFieldSurname.setBounds(101, 61, 162, 19);
        contentPane.add(textFieldSurname);

        textFieldPhoneNo = new JTextField();
        textFieldPhoneNo.setColumns(10);
        textFieldPhoneNo.setBounds(101, 85, 162, 19);
        contentPane.add(textFieldPhoneNo);

        JLabel lblDistrictName = new JLabel("District Name");
        lblDistrictName.setBounds(10, 114, 81, 13);
        contentPane.add(lblDistrictName);

        comboBoxDistrict = new JComboBox<>();
        comboBoxDistrict.setBounds(101, 110, 162, 21);
        contentPane.add(comboBoxDistrict);
        addDistrictNamesToComboBox();

        JButton btnSave = new JButton("SAVE");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                savePatient();
            }
        });
        btnSave.setBounds(101, 150, 85, 21);
        contentPane.add(btnSave);

        JButton btnUpdate = new JButton("UPDATE");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatePatient();
            }
        });
        btnUpdate.setBounds(291, 150, 85, 21);
        contentPane.add(btnUpdate);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePatient();
            }
        });
        btnDelete.setBounds(196, 150, 85, 21);
        contentPane.add(btnDelete);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 200, 764, 350);
        contentPane.add(scrollPane);

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "Surname", "Phone No", "District"}
                );
                table = new JTable(tableModel);
                scrollPane.setViewportView(table);

                loadPatients();
            }

            private void addDistrictNamesToComboBox() {
                try {
                    List<String> districtNames = patientDB.getAllDistrictNames();
                    for (String districtName : districtNames) {
                        comboBoxDistrict.addItem(districtName);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            private void savePatient() {
                Patient newPatient = new Patient();
                newPatient.setId(Integer.parseInt(textFieldId.getText()));
                newPatient.setName(textFieldName.getText());
                newPatient.setSurname(textFieldSurname.getText());
                newPatient.setPhoneNumber(textFieldPhoneNo.getText());
                String districtName = (String) comboBoxDistrict.getSelectedItem();
                try {
                    int districtId = patientDB.getDistrictIdByName(districtName);
                    if (districtId != -1) {
                        newPatient.setDistrictId(districtId);
                        newPatient.setDistrictName(districtName);
                        patientDB.savePatient(newPatient);
                        clearFields();
                        loadPatients();
                    } else {
                        JOptionPane.showMessageDialog(null, "District not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            private void updatePatient() {
                Patient updatedPatient = new Patient();
                updatedPatient.setId(Integer.parseInt(textFieldId.getText()));
                updatedPatient.setName(textFieldName.getText());
                updatedPatient.setSurname(textFieldSurname.getText());
                updatedPatient.setPhoneNumber(textFieldPhoneNo.getText());
                String districtName = (String) comboBoxDistrict.getSelectedItem();
                try {
                    int districtId = patientDB.getDistrictIdByName(districtName);
                    if (districtId != -1) {
                        updatedPatient.setDistrictId(districtId);
                        updatedPatient.setDistrictName(districtName);
                        patientDB.updatePatient(updatedPatient);
                        clearFields();
                        loadPatients();
                    } else {
                        JOptionPane.showMessageDialog(null, "District not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            private void deletePatient() {
                String idText = textFieldId.getText().trim();
                if (!idText.isEmpty()) {
                    int patientId = Integer.parseInt(idText);
                    try {
                        patientDB.deletePatient(patientId);
                        clearFields();
                        loadPatients();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in the ID field to delete a patient.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void clearFields() {
                textFieldId.setText("");
                textFieldName.setText("");
                textFieldSurname.setText("");
                textFieldPhoneNo.setText("");
                comboBoxDistrict.setSelectedIndex(0);
            }

            private void loadPatients() {
                try {
                    List<Patient> patients = patientDB.getAllPatients();
                    tableModel.setRowCount(0); 
                    for (Patient patient : patients) {
                        tableModel.addRow(new Object[]{
                                patient.getId(),
                                patient.getName(),
                                patient.getSurname(),
                                patient.getPhoneNumber(),
                                patient.getDistrictName()
                        });
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }