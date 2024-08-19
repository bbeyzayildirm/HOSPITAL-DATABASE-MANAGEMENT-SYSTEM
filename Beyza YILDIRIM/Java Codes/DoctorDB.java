package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDB {
    private Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hospitalDB";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }

    public void saveDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctor (id, firstname, surname, phone, branch_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctor.getId());
            pstmt.setString(2, doctor.getFirstName());
            pstmt.setString(3, doctor.getSurname());
            pstmt.setString(4, doctor.getPhone());
            pstmt.setString(5, doctor.getBranchName());
            pstmt.executeUpdate();
        }
    }

    public void deleteDoctor(int id) throws SQLException {
        String sql = "DELETE FROM doctor WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void updateDoctor(Doctor doctor) throws SQLException {
        String sql = "UPDATE doctor SET firstname = ?, surname = ?, phone = ?, branch_name = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctor.getFirstName());
            pstmt.setString(2, doctor.getSurname());
            pstmt.setString(3, doctor.getPhone());
            pstmt.setString(4, doctor.getBranchName());
            pstmt.setInt(5, doctor.getId());
            pstmt.executeUpdate();
        }
    }
    public Doctor getDoctorById(int id) throws SQLException {
        Doctor doctor = null;
        String sql = "SELECT * FROM doctor WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    doctor = new Doctor();
                    doctor.setId(rs.getInt("id"));
                    doctor.setFirstName(rs.getString("firstname"));
                    doctor.setSurname(rs.getString("surname"));
                    doctor.setPhone(rs.getString("phone"));
                    doctor.setBranchName(rs.getString("branch_name"));
                }
            }
        }
        return doctor;
    }

    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setFirstName(rs.getString("firstname"));
                doctor.setSurname(rs.getString("surname"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setBranchName(rs.getString("branch_name"));
                doctors.add(doctor);
            }
        }
        return doctors;
    }
}

