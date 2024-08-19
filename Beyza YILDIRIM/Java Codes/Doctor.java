package package1;
public class Doctor {
    private int id;
    private String firstName;
    private String surname;
    private String phone;
    private String branchName;

    public Doctor()
    {
    	
    }
    public Doctor(int id, String firstName, String surname, String phone, String branchName) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.phone = phone;
        this.branchName = branchName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}

