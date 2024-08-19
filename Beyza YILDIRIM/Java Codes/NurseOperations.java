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


public class NurseOperations extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JComboBox<String> comboBox;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private NurseDB nurseDB = new NurseDB();
    private JTextField searchTextField;
    private DefaultTableModel searchTableModel;
    private JTable searchTable;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NurseOperations frame = new NurseOperations();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public NurseOperations() {
        setTitle("Nurse Operations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("ID");
        lblNewLabel.setBounds(10, 10, 81, 20);
        contentPane.add(lblNewLabel);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(10, 34, 81, 20);
        contentPane.add(lblName);

        JLabel lblSurname = new JLabel("Surname");
        lblSurname.setBounds(10, 60, 81, 20);
        contentPane.add(lblSurname);

        JLabel lblPhoneNo = new JLabel("Phone No");
        lblPhoneNo.setBounds(10, 84, 81, 20);
        contentPane.add(lblPhoneNo);

        JLabel lblBranch = new JLabel("Branch");
        lblBranch.setBounds(10, 107, 81, 20);
        contentPane.add(lblBranch);

        textField = new JTextField();
        textField.setBounds(101, 11, 162, 19);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(101, 35, 162, 19);
        contentPane.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(101, 61, 162, 19);
        contentPane.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(101, 85, 162, 19);
        contentPane.add(textField_3);

        comboBox = new JComboBox<>();
        comboBox.setBounds(101, 107, 162, 21);
        contentPane.add(comboBox);

        String[] sampleBranches = {
            "Cardiology", "Orthopedics", "Neurology", "Dermatology",
            "Pediatrics", "Oncology", "Gastroenterology",
            "Endocrinology", "Urology", "Ophthalmology"
        };
        for (String branch : sampleBranches) {
            comboBox.addItem(branch);
        }

        JButton btnSave = new JButton("SAVE");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Nurse newNurse = new Nurse();
                newNurse.setId(Integer.parseInt(textField.getText()));
                newNurse.setFirstName(textField_1.getText());
                newNurse.setSurname(textField_2.getText());
                newNurse.setPhoneNumber(textField_3.getText());
                newNurse.setBranchName(comboBox.getSelectedItem().toString());
                try {
                    nurseDB.saveNurse(newNurse);
                    clearFields();
                    loadNurses();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnSave.setBounds(101, 150, 85, 21);
        contentPane.add(btnSave);

        JButton btnUpdate = new JButton("UPDATE");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Nurse updatedNurse = new Nurse();
                updatedNurse.setId(Integer.parseInt(textField.getText()));
                updatedNurse.setFirstName(textField_1.getText());
                updatedNurse.setSurname(textField_2.getText());
                updatedNurse.setPhoneNumber(textField_3.getText());
                updatedNurse.setBranchName(comboBox.getSelectedItem().toString());
                try {
                    nurseDB.updateNurse(updatedNurse);
                    clearFields();
                    loadNurses();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnUpdate.setBounds(291, 150, 85, 21);
        contentPane.add(btnUpdate);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idText = textField.getText().trim();
                if (!idText.isEmpty()) {
                    int nurseId = Integer.parseInt(idText);
                    try {
                        nurseDB.deleteNurse(nurseId);
                        clearFields();
                        loadNurses();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in the ID field to delete a nurse.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnDelete.setBounds(196, 150, 85, 21);
        contentPane.add(btnDelete);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 200, 764, 350);
        contentPane.add(scrollPane);

        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] { "ID", "First Name", "Surname", "Phone No", "Branch" }
        );
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        JScrollPane searchScrollPane = new JScrollPane();
        searchScrollPane.setBounds(417, 50, 357, 100);
        contentPane.add(searchScrollPane);

        searchTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] { "ID", "First Name", "Surname", "Phone No", "Branch" }
        );
        searchTable = new JTable(searchTableModel);
        searchScrollPane.setViewportView(searchTable);

        JLabel lblSearch = new JLabel("Search Nurse with ID:");
        lblSearch.setBounds(417, 14, 154, 13);
        contentPane.add(lblSearch);

        searchTextField = new JTextField();
        searchTextField.setBounds(567, 10, 40, 21);
        contentPane.add(searchTextField);

        JButton btnSearch = new JButton("Search Nurse");
        btnSearch.setBounds(636, 10, 119, 21);
        contentPane.add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int searchId = Integer.parseInt(searchTextField.getText());
                try {
                    Nurse nurse = nurseDB.getNurseById(searchId);
                    if (nurse != null) {
                        searchTableModel.setRowCount(0);
                        searchTableModel.addRow(new Object[]{
                            nurse.getId(),
                            nurse.getFirstName(),
                            nurse.getSurname(),
                            nurse.getPhoneNumber(),
                            nurse.getBranchName()
                        });
                    } else {
                        searchTableModel.setRowCount(0);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        loadNurses();
    }

    private void clearFields() {
        textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
        textField_3.setText("");
        comboBox.setSelectedIndex(0);
    }

    private void loadNurses() {
        try {
            List<Nurse> nurses = nurseDB.getAllNurses();
            tableModel.setRowCount(0); 
            for (Nurse nurse : nurses) {
                tableModel.addRow(new Object[] {
                    nurse.getId(),
                    nurse.getFirstName(),
                    nurse.getSurname(),
                    nurse.getPhoneNumber(),
                    nurse.getBranchName()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
