package package1;

public class Room {
    private int roomNo;
    private String roomSituation;

    public Room()
    {
    	
    }
    public Room(int roomNo, String roomSituation) {
        this.roomNo = roomNo;
        this.roomSituation = roomSituation;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomSituation() {
        return roomSituation;
    }

    public void setRoomSituation(String roomSituation) {
        this.roomSituation = roomSituation;
    }
}

