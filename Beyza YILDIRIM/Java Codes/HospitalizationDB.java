package package1;

import java.sql.*;
import java.util.ArrayList;

public class HospitalizationDB {

    public Connection getConnected() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalDB", "root", "root");
    }

    public ArrayList<Hospitalization> getHospitalizations() throws SQLException {
        ArrayList<Hospitalization> hospitalizations = new ArrayList<>();
        String query = "SELECT * FROM hospitalization";
        try (Connection connection = getConnected();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Hospitalization hospitalization = new Hospitalization(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getInt("room_no"),
                        rs.getInt("day_to_hospitalization")
                );
                hospitalizations.add(hospitalization);
            }
        }
        return hospitalizations;
    }

    public void saveHospitalization(Hospitalization hospitalization) throws SQLException {
        String query = "INSERT INTO hospitalization (id, patient_id, room_no, day_to_hospitalization) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnected();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, hospitalization.getId());
            ps.setInt(2, hospitalization.getPatientId());
            ps.setInt(3, hospitalization.getRoomNo());
            ps.setInt(4, hospitalization.getDayToHospitalization());
            ps.executeUpdate();
        }
    }

    public void deleteHospitalization(int hospitalizationId) throws SQLException {
        String query = "DELETE FROM hospitalization WHERE id = ?";
        try (Connection connection = getConnected();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, hospitalizationId);
            ps.executeUpdate();
        }
    }

    public void updateHospitalization(Hospitalization hospitalization) throws SQLException {
        String query = "UPDATE hospitalization SET patient_id = ?, room_no = ?, day_to_hospitalization = ? WHERE id = ?";
        try (Connection connection = getConnected();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, hospitalization.getPatientId());
            ps.setInt(2, hospitalization.getRoomNo());
            ps.setInt(3, hospitalization.getDayToHospitalization());
            ps.setInt(4, hospitalization.getId());
            ps.executeUpdate();
        }
    }

    public int getRoomNoByHospitalizationId(int id) throws SQLException {
        String query = "SELECT room_no FROM hospitalization WHERE id = ?";
        try (Connection connection = getConnected();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("room_no");
                } else {
                    throw new SQLException("No hospitalization found with the given ID");
                }
            }
        }
    }

    public ArrayList<Hospitalization> searchHospitalization(String keyword) throws SQLException {
        ArrayList<Hospitalization> hospitalizations = new ArrayList<>();
        String query = "SELECT * FROM hospitalization WHERE room_no LIKE ?";
        try (Connection connection = getConnected();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Hospitalization hospitalization = new Hospitalization(
                            rs.getInt("id"),
                            rs.getInt("patient_id"),
                            rs.getInt("room_no"),
                            rs.getInt("day_to_hospitalization")
                    );
                    hospitalizations.add(hospitalization);
                }
            }
        }
        return hospitalizations;
    }
}

       
