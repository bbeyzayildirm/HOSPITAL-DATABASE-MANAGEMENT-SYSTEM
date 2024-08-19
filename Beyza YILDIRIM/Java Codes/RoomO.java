package package1;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class RoomO extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtRoomNo;
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<String> comboBox;

    RoomDB roomDB = new RoomDB();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RoomO frame = new RoomO();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public RoomO() {
        setTitle("Room Operations");
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
        tableModel.addColumn("Room No");
        tableModel.addColumn("Free");
        tableModel.addColumn("Full"); 
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        JLabel lblRoomNo = new JLabel("Room No:");
        lblRoomNo.setBounds(20, 20, 100, 20);
        contentPane.add(lblRoomNo);

        txtRoomNo = new JTextField();
        txtRoomNo.setBounds(130, 20, 200, 20);
        contentPane.add(txtRoomNo);
        txtRoomNo.setColumns(10);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Room room = new Room();
                room.setRoomNo(Integer.parseInt(txtRoomNo.getText()));
                room.setRoomSituation((String) comboBox.getSelectedItem());
                try {
                    roomDB.saveRoom(room);
                    refreshTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnSave.setBounds(54, 120, 100, 20);
        contentPane.add(btnSave);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String roomNoText = txtRoomNo.getText().trim(); 
                if (!roomNoText.isEmpty()) { 
                    int roomNo = Integer.parseInt(roomNoText);
                    try {
                        roomDB.deleteRoom(roomNo);
                        refreshTable(); 
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Please enter a room number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnDelete.setBounds(186, 120, 100, 20);
        contentPane.add(btnDelete);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Room room = new Room();
                room.setRoomNo(Integer.parseInt(txtRoomNo.getText()));
                room.setRoomSituation((String) comboBox.getSelectedItem());
                try {
                    roomDB.updateRoom(room);
                    refreshTable(); 
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnUpdate.setBounds(324, 120, 100, 20);
        contentPane.add(btnUpdate);

        JLabel lblRoomSituation = new JLabel("Room Situation:");
        lblRoomSituation.setBounds(20, 50, 100, 20);
        contentPane.add(lblRoomSituation);

        comboBox = new JComboBox<>();
        comboBox.addItem("free");
        comboBox.addItem("full");
        comboBox.setBounds(130, 50, 200, 21);
        contentPane.add(comboBox);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0); 
        try {
            ArrayList<Room> rooms = roomDB.getRooms();
            for (Room room : rooms) {
                Object[] row = { room.getRoomNo(), room.getRoomSituation().equals("free") ? "Yes" : "", room.getRoomSituation().equals("full") ? "Yes" : "" };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

