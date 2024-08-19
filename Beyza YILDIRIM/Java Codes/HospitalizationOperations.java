package package1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class HospitalizationOperations extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtId;
    private JTextField txtPatientId;
    private JTextField txtRoomNo;
    private JTextField txtDayToHospitalization;
    private JTable table;
    private DefaultTableModel tableModel;

    private HospitalizationDB hospitalizationDB = new HospitalizationDB();
    private RoomDB roomDB = new RoomDB();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                HospitalizationOperations frame = new HospitalizationOperations();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public HospitalizationOperations() {
        setTitle("Hospitalization Operations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblId = new JLabel("ID");
        lblId.setBounds(10, 10, 100, 20);
        contentPane.add(lblId);

        JLabel lblPatientId = new JLabel("Patient ID");
        lblPatientId.setBounds(10, 40, 100, 20);
        contentPane.add(lblPatientId);

        JLabel lblRoomNo = new JLabel("Room No");
        lblRoomNo.setBounds(10, 70, 100, 20);
        contentPane.add(lblRoomNo);

        JLabel lblDayToHospitalization = new JLabel("Day To Hospitalization");
        lblDayToHospitalization.setBounds(10, 100, 150, 20);
        contentPane.add(lblDayToHospitalization);

        txtId = new JTextField();
        txtId.setBounds(120, 10, 200, 20);
        contentPane.add(txtId);
        txtId.setColumns(10);

        txtPatientId = new JTextField();
        txtPatientId.setBounds(120, 40, 200, 20);
        contentPane.add(txtPatientId);
        txtPatientId.setColumns(10);

        txtRoomNo = new JTextField();
        txtRoomNo.setBounds(120, 70, 200, 20);
        contentPane.add(txtRoomNo);
        txtRoomNo.setColumns(10);

        txtDayToHospitalization = new JTextField();
        txtDayToHospitalization.setBounds(170, 100, 150, 20);
        contentPane.add(txtDayToHospitalization);
        txtDayToHospitalization.setColumns(10);

        JButton btnSave = new JButton("SAVE");
        btnSave.addActionListener(e -> {
            try {
                saveHospitalization();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        btnSave.setBounds(10, 150, 100, 20);
        contentPane.add(btnSave);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.addActionListener(e -> {
            try {
                deleteHospitalization();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        btnDelete.setBounds(153, 150, 100, 20);
        contentPane.add(btnDelete);

        JButton btnUpdate = new JButton("UPDATE");
        btnUpdate.addActionListener(e -> {
            try {
                updateHospitalization();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        btnUpdate.setBounds(290, 150, 100, 20);
        contentPane.add(btnUpdate);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 200, 764, 350);
        contentPane.add(scrollPane);

        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Patient ID", "Room No", "Day To Hospitalization" }
        );
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        loadHospitalizations();
    }

    private void saveHospitalization() throws SQLException {
        Hospitalization newHospitalization = new Hospitalization();
        newHospitalization.setId(Integer.parseInt(txtId.getText()));
        newHospitalization.setPatientId(Integer.parseInt(txtPatientId.getText()));
        newHospitalization.setRoomNo(Integer.parseInt(txtRoomNo.getText()));
        newHospitalization.setDayToHospitalization(Integer.parseInt(txtDayToHospitalization.getText()));
        hospitalizationDB.saveHospitalization(newHospitalization);
        roomDB.updateRoomSituation(newHospitalization.getRoomNo(), "full");
        clearFields();
        loadHospitalizations();
    }

    private void deleteHospitalization() throws SQLException {
        int id = Integer.parseInt(txtId.getText());
        int roomNo = hospitalizationDB.getRoomNoByHospitalizationId(id);
        hospitalizationDB.deleteHospitalization(id);
        roomDB.updateRoomSituation(roomNo, "free");
        clearFields();
        loadHospitalizations();
    }

    private void updateHospitalization() throws SQLException {
        Hospitalization updatedHospitalization = new Hospitalization();
        updatedHospitalization.setId(Integer.parseInt(txtId.getText()));
        updatedHospitalization.setPatientId(Integer.parseInt(txtPatientId.getText()));
        updatedHospitalization.setRoomNo(Integer.parseInt(txtRoomNo.getText()));
        updatedHospitalization.setDayToHospitalization(Integer.parseInt(txtDayToHospitalization.getText()));
        hospitalizationDB.updateHospitalization(updatedHospitalization);
        clearFields();
        loadHospitalizations();
    }

    private void clearFields() {
        txtId.setText("");
        txtPatientId.setText("");
        txtRoomNo.setText("");
        txtDayToHospitalization.setText("");
    }

    private void loadHospitalizations() {
        try {
            ArrayList<Hospitalization> hospitalizations = hospitalizationDB.getHospitalizations();
            tableModel.setRowCount(0);
            for (Hospitalization hospitalization : hospitalizations) {
                tableModel.addRow(new Object[] {
                        hospitalization.getId(),
                        hospitalization.getPatientId(),
                        hospitalization.getRoomNo(),
                        hospitalization.getDayToHospitalization()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
