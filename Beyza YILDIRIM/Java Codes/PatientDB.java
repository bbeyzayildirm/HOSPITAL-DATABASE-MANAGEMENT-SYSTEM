package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDB {

    private Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hospitalDB";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }

    public void savePatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patient (id, patient_name, surname, phone_no, district_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patient.getId());
            pstmt.setString(2, patient.getName());
            pstmt.setString(3, patient.getSurname());
            pstmt.setString(4, patient.getPhoneNumber());
            pstmt.setInt(5, patient.getDistrictId());
            pstmt.executeUpdate();
        }
    }

    public void deletePatient(int id) throws SQLException {
        String sql = "DELETE FROM patient WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patient SET patient_name = ?, surname = ?, phone_no = ?, district_id = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getSurname());
            pstmt.setString(3, patient.getPhoneNumber());
            pstmt.setInt(4, patient.getDistrictId());
            pstmt.setInt(5, patient.getId());
            pstmt.executeUpdate();
        }
    }

    public Patient getPatientById(int id) throws SQLException {
        Patient patient = null;
        String sql = "SELECT p.id, p.patient_name, p.surname, p.phone_no, p.district_id, d.district_name FROM patient p JOIN district d ON p.district_id = d.district_id WHERE p.id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    patient = new Patient();
                    patient.setId(rs.getInt("id"));
                    patient.setName(rs.getString("patient_name"));
                    patient.setSurname(rs.getString("surname"));
                    patient.setPhoneNumber(rs.getString("phone_no"));
                    patient.setDistrictId(rs.getInt("district_id"));
                    patient.setDistrictName(rs.getString("district_name"));
                }
            }
        }
        return patient;
    }

    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT p.id, p.patient_name, p.surname, p.phone_no, p.district_id, d.district_name FROM patient p JOIN district d ON p.district_id = d.district_id";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setName(rs.getString("patient_name"));
                patient.setSurname(rs.getString("surname"));
                patient.setPhoneNumber(rs.getString("phone_no"));
                patient.setDistrictId(rs.getInt("district_id"));
                patient.setDistrictName(rs.getString("district_name"));
                patients.add(patient);
            }
        }
        return patients;
    }

    public List<String> getAllDistrictNames() throws SQLException {
        List<String> districtNames = new ArrayList<>();
        String sql = "SELECT district_name FROM district";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                districtNames.add(rs.getString("district_name"));
            }
        }
        return districtNames;
    }

    public int getDistrictIdByName(String districtName) throws SQLException {
        String sql = "SELECT district_id FROM district WHERE district_name = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, districtName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("district_id");
                }
            }
        }
        return -1; 
    }
}
