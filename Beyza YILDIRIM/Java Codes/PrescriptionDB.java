package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PrescriptionDB {

    public Connection getConnected() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalDB", "root", "root");
    }

    public ArrayList<Prescription> getPrescriptions() throws SQLException {
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        String query = "SELECT * FROM prescription";
        try (Connection connection = getConnected();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Prescription prescription = new Prescription(
                        resultSet.getInt("prescription_id"),
                        resultSet.getInt("patient_id"),
                        resultSet.getInt("doctor_id")
                );
                prescriptions.add(prescription);
            }
        }
        return prescriptions;
    }

    public void savePrescription(Prescription prescription) throws SQLException {
        String query = "INSERT INTO prescription (patient_id, doctor_id) VALUES (?, ?)";
        try (Connection connection = getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, prescription.getPatientId());
            preparedStatement.setInt(2, prescription.getDoctorId());
            preparedStatement.executeUpdate();
        }
    }

    public void deletePrescription(int prescriptionId) throws SQLException {
        String query = "DELETE FROM prescription WHERE prescription_id = ?";
        try (Connection connection = getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, prescriptionId);
            preparedStatement.executeUpdate();
        }
    }

    public void updatePrescription(Prescription prescription) throws SQLException {
        String query = "UPDATE prescription SET patient_id = ?, doctor_id = ? WHERE prescription_id = ?";
        try (Connection connection = getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, prescription.getPatientId());
            preparedStatement.setInt(2, prescription.getDoctorId());
            preparedStatement.setInt(3, prescription.getPrescriptionId());
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Prescription> searchPrescription(String keyword) throws SQLException {
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        String query = "SELECT * FROM prescription WHERE prescription_id LIKE ?";
        try (Connection connection = getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Prescription prescription = new Prescription(
                            resultSet.getInt("prescription_id"),
                            resultSet.getInt("patient_id"),
                            resultSet.getInt("doctor_id")
                    );
                    prescriptions.add(prescription);
                }
            }
        }
        return prescriptions;
    }
}
