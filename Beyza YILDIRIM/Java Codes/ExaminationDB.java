package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExaminationDB {

    public Connection getConnected() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalDB", "root", "root");
    }

    public ArrayList<Examination> getExaminations() throws SQLException {
        Statement st = getConnected().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM examination");
        ArrayList<Examination> examinations = new ArrayList<>();

        while (rs.next()) {
            Examination examination = new Examination(
                    rs.getInt("examination_id"),
                    rs.getInt("doctor_id"),
                    rs.getInt("patient_id"),
                    rs.getString("diagnosis")
            );
            examinations.add(examination);
        }
        return examinations;
    }

    public void saveExamination(Examination examination) throws SQLException {
        String query = "INSERT INTO examination (examination_id, doctor_id, patient_id, diagnosis) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = getConnected().prepareStatement(query);
        ps.setInt(1, examination.getExaminationId());
        ps.setInt(2, examination.getDoctorId());
        ps.setInt(3, examination.getPatientId());
        ps.setString(4, examination.getDiagnosis());
        ps.executeUpdate();
    }

    public void deleteExamination(int examinationId) throws SQLException {
        String query = "DELETE FROM examination WHERE examination_id = ?";
        PreparedStatement ps = getConnected().prepareStatement(query);
        ps.setInt(1, examinationId);
        ps.executeUpdate();
    }

    public void updateExamination(Examination examination) throws SQLException {
        String query = "UPDATE examination SET doctor_id = ?, patient_id = ?, diagnosis = ? WHERE examination_id = ?";
        PreparedStatement ps = getConnected().prepareStatement(query);
        ps.setInt(1, examination.getDoctorId());
        ps.setInt(2, examination.getPatientId());
        ps.setString(3, examination.getDiagnosis());
        ps.setInt(4, examination.getExaminationId());
        ps.executeUpdate();
    }

    public ArrayList<Examination> searchExamination(String keyword) throws SQLException {
        ArrayList<Examination> examinations = new ArrayList<>();
        String query = "SELECT * FROM examination WHERE diagnosis LIKE ?";
        PreparedStatement ps = getConnected().prepareStatement(query);
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Examination examination = new Examination(
                    rs.getInt("examination_id"),
                    rs.getInt("doctor_id"),
                    rs.getInt("patient_id"),
                    rs.getString("diagnosis")
            );
            examinations.add(examination);
        }
        return examinations;
    }
}

