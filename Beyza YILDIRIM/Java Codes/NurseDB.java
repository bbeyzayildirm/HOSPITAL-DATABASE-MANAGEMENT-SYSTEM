package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NurseDB {

    private Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hospitalDB";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }

    public void saveNurse(Nurse nurse) throws SQLException {
        String sql = "INSERT INTO nurse (id, branch_name, firstname, surname, phone_no) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nurse.getId());
            pstmt.setString(2, nurse.getBranchName());
            pstmt.setString(3, nurse.getFirstName());
            pstmt.setString(4, nurse.getSurname());
            pstmt.setString(5, nurse.getPhoneNumber());
            pstmt.executeUpdate();
        }
    }

    public void deleteNurse(int id) throws SQLException {
        String sql = "DELETE FROM nurse WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void updateNurse(Nurse nurse) throws SQLException {
        String sql = "UPDATE nurse SET branch_name = ?, firstname = ?, surname = ?, phone_no = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nurse.getBranchName());
            pstmt.setString(2, nurse.getFirstName());
            pstmt.setString(3, nurse.getSurname());
            pstmt.setString(4, nurse.getPhoneNumber());
            pstmt.setInt(5, nurse.getId());
            pstmt.executeUpdate();
        }
    }

    public Nurse getNurseById(int id) throws SQLException {
        Nurse nurse = null;
        String sql = "SELECT * FROM nurse WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nurse = new Nurse();
                    nurse.setId(rs.getInt("id"));
                    nurse.setBranchName(rs.getString("branch_name"));
                    nurse.setFirstName(rs.getString("firstname"));
                    nurse.setSurname(rs.getString("surname"));
                    nurse.setPhoneNumber(rs.getString("phone_no"));
                }
            }
        }
        return nurse;
    }

    public List<Nurse> getAllNurses() throws SQLException {
        List<Nurse> nurses = new ArrayList<>();
        String sql = "SELECT * FROM nurse";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Nurse nurse = new Nurse();
                nurse.setId(rs.getInt("id"));
                nurse.setBranchName(rs.getString("branch_name"));
                nurse.setFirstName(rs.getString("firstname"));
                nurse.setSurname(rs.getString("surname"));
                nurse.setPhoneNumber(rs.getString("phone_no"));
                nurses.add(nurse);
            }
        }
        return nurses;
    }
}
