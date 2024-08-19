package package1;

public class Hospitalization {
    private int id;
    private int patientId;
    private int roomNo;
    private int dayToHospitalization;
    
    public Hospitalization()
    {
    	
    }

    public Hospitalization(int id, int patientId, int roomNo, int dayToHospitalization) {
        this.id = id;
        this.patientId = patientId;
        this.roomNo = roomNo;
        this.dayToHospitalization = dayToHospitalization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getDayToHospitalization() {
        return dayToHospitalization;
    }

    public void setDayToHospitalization(int dayToHospitalization) {
        this.dayToHospitalization = dayToHospitalization;
    }
}

