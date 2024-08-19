package package1;

import java.sql.*;
import java.util.ArrayList;

public class RoomDB {

    public Connection getConnected() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalDB", "root", "root");
    }

    public ArrayList<Room> getRooms() throws SQLException {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room";
        try (Connection connection = getConnected();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Room room = new Room(
                        resultSet.getInt("room_no"),
                        resultSet.getString("room_situation")
                );
                rooms.add(room);
            }
        }
        return rooms;
    }

    public void saveRoom(Room room) throws SQLException {
        String query = "INSERT INTO room (room_no, room_situation) VALUES (?, ?)";
        try (Connection connection = getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, room.getRoomNo());
            preparedStatement.setString(2, room.getRoomSituation());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteRoom(int roomNo) throws SQLException {
        String query = "DELETE FROM room WHERE room_no = ?";
        try (Connection connection = getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomNo);
            preparedStatement.executeUpdate();
        }
    }

    public void updateRoom(Room room) throws SQLException {
        String query = "UPDATE room SET room_situation = ? WHERE room_no = ?";
        try (Connection connection = getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, room.getRoomSituation());
            preparedStatement.setInt(2, room.getRoomNo());
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Room> searchRoom(String keyword) throws SQLException {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room WHERE room_situation LIKE ?";
        try (Connection connection = getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Room room = new Room(
                            resultSet.getInt("room_no"),
                            resultSet.getString("room_situation")
                    );
                    rooms.add(room);
                }
            }
        }
        return rooms;
    }

    public void updateRoomSituation(int roomNo, String situation) throws SQLException {
        String query = "UPDATE room SET room_situation = ? WHERE room_no = ?";
        try (Connection connection = getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, situation);
            preparedStatement.setInt(2, roomNo);
            preparedStatement.executeUpdate();
        }
    }
}
