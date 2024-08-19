package package1;

public class Examination {
    private int examinationId;
    private int doctorId;
    private int patientId;
    private String diagnosis;
    
    public Examination()
    {
    	
    }

    public Examination(int examinationId, int doctorId, int patientId, String diagnosis) {
        this.examinationId = examinationId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.diagnosis = diagnosis;
    }

    public int getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(int examinationId) {
        this.examinationId = examinationId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "Examination [examinationId=" + examinationId + ", doctorId=" + doctorId + ", patientId=" + patientId
                + ", diagnosis=" + diagnosis + "]";
    }
}

