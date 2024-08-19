package package1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AppointmentDB {

    public Connection getConnected() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalDB", "root", "root");
    }

    public ArrayList<Appointment> getAppointments() throws SQLException {
        Statement st = getConnected().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM appointment");
        ArrayList<Appointment> appointments = new ArrayList<>();

        while (rs.next()) {
            Appointment appointment = new Appointment(
                    rs.getInt("appointment_id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getString("appointment_date")
            );
            appointments.add(appointment);
        }
        return appointments;
    }

    public void saveAppointment(Appointment appointment) throws SQLException {
        String query = "INSERT INTO appointment (appointment_id, patient_id, doctor_id, appointment_date) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = getConnected().prepareStatement(query);
        ps.setInt(1, appointment.getAppointmentId());
        ps.setInt(2, appointment.getPatientId());
        ps.setInt(3, appointment.getDoctorId());
        ps.setString(4, appointment.getAppointmentDate());
        ps.executeUpdate();
    }

    public void deleteAppointment(int appointmentId) throws SQLException {
        String query = "DELETE FROM appointment WHERE appointment_id = ?";
        PreparedStatement ps = getConnected().prepareStatement(query);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }

    public void updateAppointment(Appointment appointment) throws SQLException {
        String query = "UPDATE appointment SET patient_id = ?, doctor_id = ?, appointment_date = ? WHERE appointment_id = ?";
        PreparedStatement ps = getConnected().prepareStatement(query);
        ps.setInt(1, appointment.getPatientId());
        ps.setInt(2, appointment.getDoctorId());
        ps.setString(3, appointment.getAppointmentDate());
        ps.setInt(4, appointment.getAppointmentId());
        ps.executeUpdate();
    }

    public ArrayList<Appointment> searchAppointment(String keyword) throws SQLException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointment WHERE appointment_date LIKE ?";
        PreparedStatement ps = getConnected().prepareStatement(query);
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Appointment appointment = new Appointment(
                    rs.getInt("appointment_id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getString("appointment_date")
            );
            appointments.add(appointment);
        }
        return appointments;
    }
}

